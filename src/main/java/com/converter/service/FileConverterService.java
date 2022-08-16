package com.converter.service;

import com.converter.dto.ConvertResultDTO;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FileConverterService {

    private static final Logger logger = Logger.getLogger(FileConverterService.class);
    private int quantityProcessedFiles;
    @ConfigProperty(name = "path.initialData")
    String pathOriginal;
    @ConfigProperty(name = "path.results")
    String pathModified;
    public ConvertResultDTO startOfConversion(String parameter) {
        try {
            CamelContext camel = new DefaultCamelContext();
            camel.getPropertiesComponent().setLocation("classpath:application.properties");
            camel.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:{{path.initialData}}?noop=true&antInclude=*.txt")
                            .process(new Processor() {
                                @Override
                                public void process(Exchange exchange) throws Exception {
                                    ++quantityProcessedFiles;
                                }
                            })
                            .setHeader("parameter", constant(parameter))
                            .bean(FileConverterService.class, "convertBody(${exchange},${header.parameter})")
                            .to("file:{{path.results}}");
                }
            });
            camel.start();
            Thread.sleep(3000);
            camel.close();

        }catch (InterruptedException e) {
            logger.fatal("Exception: " +e.getClass(), e);;
        } catch (Exception e) {
            logger.fatal("Exception: " +e.getClass(), e);
        }
        return new ConvertResultDTO(pathOriginal, pathModified, quantityProcessedFiles, parameter);
    }

    public void convertBody(Exchange exchange, String parameter){
        logger.info("Processing file: " + exchange.getIn().getHeader("CamelFileName") + " with parameter: " + parameter );
        String body = exchange.getMessage().getBody(String.class);
        int characterСounter = 0;
        int characterCounterWithoutSpaces = 0;
        characterСounter += body.length();
        characterCounterWithoutSpaces += body.replace(" ","").length();
        StringBuilder sb = new StringBuilder();
        sb.append(body)
                .append("\n")
                .append("Количетсво символов:")
                .append(characterСounter)
                .append("\n")
                .append("Количетсво символов без пробелов:")
                .append(characterCounterWithoutSpaces)
                .append("\n")
                .append("Параметр на вход RESTа:")
                .append(parameter);
        exchange.getIn().setBody(sb);
    }
}

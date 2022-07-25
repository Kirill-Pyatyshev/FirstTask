package com.example;


import org.apache.log4j.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/hello")
public class ExampleResource {

    private static final Logger logger = Logger.getLogger(ExampleResource.class);
    @ConfigProperty(name = "path.dirOriginal")
    String pathOriginal;
    @ConfigProperty(name = "path.dirModified")
    String pathModified;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        try {
            Сonverter.copyFile(pathOriginal,pathModified);
        } catch (IOException e) {
            logger.fatal("Catch exception " + e.getClass());
        }
        return "Path original: " + pathOriginal + "\n" +
        "Path modified: " + pathModified + "\n";
    }

}
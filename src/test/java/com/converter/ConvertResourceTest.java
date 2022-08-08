package com.converter;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;
import service.FileConverterService;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@QuarkusTest
public class ConvertResourceTest {

    @ConfigProperty(name = "path.dirModified")
    String pathModified;
    @Inject
    FileConverterService service;

    @Test
    public void testСonverterEndpointReturnJson() {
        given()
                .queryParam("Parameter","User auth")
                .auth().basic("admin", "admin")
                .when().get("/converter")
                .then()
                .statusCode(200)
                .body("parameter", is("User auth"))
                .body("pathOriginal", is("src\\test\\resources\\initialData\\"))
                .body("pathModified", is("src\\test\\resources\\results\\"))
                .body("quantityProcessedFiles", is(2));
    }

    @Test
    public void testСonverterEndpointReturnJsonV2() {
        service = given()
                .queryParam("Parameter","User auth")
                .auth().basic("admin", "admin")
                .when().get("/converter")
                .then()
                .statusCode(200)
                .extract().as(FileConverterService.class);

        assertEquals("User auth", service.getResultDTO().getParameter());
        assertEquals("src\\test\\resources\\initialData\\", service.getResultDTO().getPathInitialData());
        assertEquals("src\\test\\resources\\results\\", service.getResultDTO().getPathResult());
        assertEquals(2, service.getResultDTO().getQuantityProcessedFiles());
    }

    @Test
    public void testConverterController() {
        try {
            BufferedReader reader = new BufferedReader((new FileReader(Path.of(pathModified,"Война и мир.txt").toString())));
            String lines;
            while ((lines = reader.readLine()) != null){
                if (lines.contains("Параметр на вход RESTа:")){
                    String[] str = lines.split(":");
                    String parameter = (str[1]);
                    given()
                            .queryParam("Parameter","User auth")
                            .auth().basic("admin", "admin")
                            .when().get("/converter")
                            .then()
                            .statusCode(200)
                            .body("parameter", is(parameter));
                }
            }
        } catch (IOException e) {
            fail("testConverterController : " + e.getMessage());
        }
    }
}

package com.converter;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.fail;

@QuarkusTest
public class ConvertResourceTest {

    @ConfigProperty(name = "path.dirOriginal")
    String pathOriginal;
    @ConfigProperty(name = "path.dirModified")
    String pathModified;

    @Test
    public void testСonverterEndpointReturnJson() {
        given()
                .queryParam("Parameter","User auth")
                .auth().basic("user", "12")
                .when().get("/converter")
                .then()
                .statusCode(200)
                .body("parameter", is("User auth"))
                .body("path_Initial_Data", is("src\\test\\resources\\initialData\\"))
                .body("path_Result", is("src\\test\\resources\\results\\"))
                .body("quantity_Processed_Files", is(3));
    }

    @Test
    public void testConverterController() {
        try {
            BufferedReader reader = new BufferedReader((new FileReader(pathModified + "Война и мир.txt")));
            String lines;
            while ((lines = reader.readLine()) != null){
                if (lines.contains("Параметр на вход RESTа:")){
                    String[] str = lines.split(":");
                    String parameter = (str[1]);
                    given()
                            .queryParam("Parameter","User auth")
                            .auth().basic("user", "12")
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
package com.converter;

import Service.FileConverterService;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ConverterTest {
    @ConfigProperty(name = "path.dirOriginal")
    String pathOriginal;
    @ConfigProperty(name = "path.dirModified")
    String pathModified;

    @org.junit.jupiter.api.Test
    void testCopyFile() {
        final File directory = new File(pathModified);
        final File[] files = directory.listFiles(File::isFile);

        String[] expectedValues = {"Война и мир.txt", "Пустой.txt", "У лукоморья дуб зелёный....txt"};
        try {
            FileConverterService.copyFile(pathOriginal, pathModified,"");
            for (int i = 0; i < files.length; i++) {
                assertEquals(files[i].getName(),expectedValues[i]); // Checking the file name
            }

            int expectedСountСharacters = 729735;

            BufferedReader reader = new BufferedReader((new FileReader(pathModified + "Война и мир.txt")));

            String lines;
            while ((lines = reader.readLine()) != null){
                if (lines.contains("Количество символов:")){
                    String[] str = lines.split(":");
                    int countingCharactersFromFile = Integer.parseInt (str[1]);
                    assertEquals(expectedСountСharacters,countingCharactersFromFile); // Checking the character count
                }
            }
        } catch (IOException e) {
            fail("ConverterTest failed : " + e.getMessage());
        }
    }
}

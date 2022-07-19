package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.company.Main.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    String directoryPathOrig = "D:\\original\\";
    String directoryPathMod = "D:\\modified\\";
    File directory = new File(directoryPathOrig);
    File[] filesOrig = directory.listFiles(File::isFile);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFileSearch() {

        try{
            assertEquals(5,fileSearch(directoryPathOrig).length );

            if(directory.listFiles(File::isDirectory).length > 0){
                assertNotEquals(directory.listFiles().length, fileSearch(directoryPathOrig).length);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFileConversion() {
        try {
            for (File f : filesOrig) {
                List<String> sListOrig = Files.readAllLines(f.toPath());
                List<String> sListMod = fileConversion(fileSearch(directoryPathOrig)).get(f);
                assertNotEquals(sListOrig,sListMod);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSaveFile() {
        try {
            saveFile(fileConversion(fileSearch(directoryPathOrig)));
            File directory = new File(directoryPathMod);
            File[] fileMod = directory.listFiles();
            assertNotNull(fileMod);
            assertEquals(fileSearch(directoryPathOrig).length, fileMod.length);
            for (int i = 0; i < filesOrig.length; i++) {
                String expectedVal = "New_"+ filesOrig[i].getName();
                assertEquals(expectedVal,fileMod[i].getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
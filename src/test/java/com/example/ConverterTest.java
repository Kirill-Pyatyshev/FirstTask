package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterTest {
    final String pathOriginal = "src\\test\\resources\\initialData\\";
    final String pathModified = "src\\test\\resources\\results\\";
    final File directory = new File(pathModified);
    final File[] files = directory.listFiles(File::isFile);

    @org.junit.jupiter.api.Test
    void testCopyFile() {
        String[] expectedValues = {"Война и мир.txt", "Пустой.txt", "У лукоморья дуб зеленый....txt"};
        try {
            Сonverter.copyFile(pathOriginal, pathModified);
            for (int i = 0; i < files.length; i++) {
                assertEquals(files[i].getName(),expectedValues[i]); // Checking the file name
            }

            int expectedСountСharacters = 729735;
            List<String> fileLines = Files.readAllLines(Path.of(pathModified + "Война и мир.txt")); //Война и мир 729735 601232
            for (String lines : fileLines) {
                if (lines.contains("Количетсво символов:")){
                    String[] str = lines.split(":");
                    int countingCharactersFromFile = Integer.parseInt (str[1]);
                    assertEquals(expectedСountСharacters,countingCharactersFromFile); // Checking the character count
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

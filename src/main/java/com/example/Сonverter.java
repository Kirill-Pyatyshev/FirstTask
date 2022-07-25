package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;

public  class Сonverter {
    public static void copyFile(String directoryPathOriginal, String directoryPathModified) throws IOException {
        final File directory = new File(directoryPathOriginal);
        File[] files = directory.listFiles(File::isFile);
        for (File file: files) {
            if(Objects.equals(FilenameUtils.getExtension(String.valueOf(file)), "txt")){
                Files.copy(Path.of(String.valueOf(file)), Path.of(directoryPathModified + file.getName()));
                convertAndSaveFile(file, directoryPathModified);
            }
        }
    }
    public static void convertAndSaveFile(File file, String directoryPathModified) throws IOException {
        int characterСounter = 0;
        int characterCounterWithoutSpaces = 0;
        List<String> fileLines = Files.readAllLines(file.toPath());
        for (String lines : fileLines) {
            characterСounter += lines.length();
            characterCounterWithoutSpaces += lines.replace(" ","").length();
        }
        FileWriter fileWriter = new FileWriter(directoryPathModified + file.getName(), true);
        fileWriter.write("\n" + "\n" + "Количетсво символов:" + characterСounter + "\n" +
                "Количетсво символов без пробелов:" + characterCounterWithoutSpaces);
        fileWriter.close();
    }
}

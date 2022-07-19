package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Main {

    private static FileWriter fileWriter;

    public static void main(String[] args){

        String directoryPath = "D:\\original\\";

        try{
            saveFile(fileConversion(fileSearch(directoryPath)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File[] fileSearch (String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles(File::isFile);

        if (files == null) {
            System.out.println("Нет доступных файлов для обработки.");
        } else {
            System.out.println("Количество файлов для обработки: " + files.length);
            for (File f : files) {
                System.out.println(f.getName());
            }
            System.out.println("\n");
        }
        return files;
    };

    public static Map<File, List<String>> fileConversion(File[] files) throws IOException {

        Map<File, List<String>> allF = new LinkedHashMap<>();

        for (File f : files)
        {
            int symbol = 0;
            int symbolN = 0;

            List<String> sList = Files.readAllLines(f.toPath());

            for (String str : sList) {
                symbol += str.length();
                symbolN += str.replace(" ","").length();
            }
            sList.add(" ");
            sList.add("Количетсво символов: " + symbol);
            sList.add("Количетсво символов без пробелов: " + symbolN);

            allF.put(f,sList);

            System.out.println("Файл " + f.getName() + " преобразован!");
        }
        System.out.println("\n");
        return allF;
    }

    public static void saveFile (Map<File, List<String>> allF) throws IOException {

        for (File f: allF.keySet()) {

            FileWriter fileWriter = new FileWriter("D:\\modified\\" + "New_"+ f.getName(), false);
            for (String str : allF.get(f)) {
                fileWriter.write(str);
                fileWriter.append('\n');
            }
            fileWriter.close();
            System.out.println("Файл сохранен, как New_" + f.getName());
        }
    }
}

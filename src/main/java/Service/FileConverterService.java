package Service;

import org.apache.log4j.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@ApplicationScoped
public  class FileConverterService {
    private static final Logger logger = Logger.getLogger(FileConverterService.class);
    private static int Quantity_Processed_Files;
    public static int getQuantity_Processed_Files() {
        return Quantity_Processed_Files;
    }
    public static void copyFile(String directoryPathOriginal, String directoryPathModified,String Parameter){

        final File directory = new File(directoryPathOriginal);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        Quantity_Processed_Files = files.length;
        for (File file: files) {
            try {
                Files.copy(file.toPath(), Path.of(directoryPathModified + file.getName()), StandardCopyOption.REPLACE_EXISTING);
                convertAndSaveFile(file, directoryPathModified,Parameter);
            } catch (IOException e) {
                logger.fatal("Exception: " +e.getClass() + "Error when copying file");
            }
        }
    }
    public static void convertAndSaveFile(File file, String directoryPathModified,String Parameter){
        int characterСounter = 0;
        int characterCounterWithoutSpaces = 0;
        try {
            BufferedReader reader = new BufferedReader((new FileReader(file)));

            String lines;
            while ((lines = reader.readLine()) != null){
                characterСounter += lines.length();
                characterCounterWithoutSpaces += lines.replace(" ","").length();
            }

            FileWriter fileWriter = new FileWriter(directoryPathModified + file.getName(), true);
            StringBuffer sb = new StringBuffer();

            sb.append("\n")
                    .append("Количетсво символов:")
                    .append(characterСounter)
                    .append("\n")
                    .append("Количетсво символов без пробелов:")
                    .append(characterCounterWithoutSpaces)
                    .append("\n")
                    .append("Параметр на вход RESTа:")
                    .append(Parameter);
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            logger.fatal("Exception: " +e.getClass() + "Error when saving the file.");
        }
    }
}

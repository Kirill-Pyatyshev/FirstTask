package service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class FileConverterService {

    private static final Logger logger = Logger.getLogger(FileConverterService.class);

    @ConfigProperty(name = "path.dirOriginal")
    String pathOriginal;
    @ConfigProperty(name = "path.dirModified")
    String pathModified;
    private int quantityProcessedFiles;
    private String parameter;

    public void startOfConversion(String parameter){
        this.parameter = parameter;
        final File directory = new File(pathOriginal);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        quantityProcessedFiles = files.length;
        Arrays.stream(files).forEach(file -> {
            try {
                Files.copy(file.toPath(), Path.of(pathModified,file.getName()), StandardCopyOption.REPLACE_EXISTING);
                convertAndSaveFile(file, parameter);
            } catch (AccessDeniedException e){
                logger.fatal("Exception: " + e.getClass() + ". The file " + file.getName() + " is not copied, there is no access,");
                quantityProcessedFiles--;
            } catch (IOException e) {
                logger.fatal("Exception: " + e.getClass() + ". Error when copying file");
            }
        });
    }

    public void convertAndSaveFile(File file, String Parameter){
        int characterСounter = 0;
        int characterCounterWithoutSpaces = 0;
        try {
            BufferedReader reader = new BufferedReader((new FileReader(file)));
            String lines;
            while ((lines = reader.readLine()) != null){
                characterСounter += lines.length();
                characterCounterWithoutSpaces += lines.replace(" ","").length();
            }
            FileWriter fileWriter = new FileWriter(Path.of(pathModified,file.getName()).toString(), true);
            StringBuilder sb = new StringBuilder();

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

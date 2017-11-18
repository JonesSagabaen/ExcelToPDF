package configuration;

import java.io.*;
import java.util.ArrayList;

public class ConfigurationFile {

    /**
     * The set of configuration details obtained from the configuration file.
     */
    private ExcelPDFConfiguration[] configurationFields;

    /**
     * The format of the configuration filename
     */
    private static String filename = "/user.conf";

    /**
     * Read an existing configuration file.
     * Create an instance of this class so that configuration information is readily availble for the app to use.
     * @param sourceFilepath            Path of the existing configuration file to read.
     */
    public ConfigurationFile(String sourceFilepath) {
        // Read user's configuration file and create list of columns to read
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilepath))) {
            ArrayList<ExcelPDFConfiguration> columnsList = new ArrayList<>();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                columnsList.add(ExcelPDFConfiguration.parseString(currentLine));
            }

            configurationFields = columnsList.toArray(new ExcelPDFConfiguration[columnsList.size()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write a configuration file.
     * If no configuration file is found, prompt the user to enter the configuration information.  That information
     * will then be used to create an instance of this class and to also write a configuration file for future use.
     * @param destinationFilepath       Path to write the user configuration file to.
     * @param excelPDFConfigurations    The set of configuration fields that will be saved into the configuration file.
     */
    public ConfigurationFile(String destinationFilepath, ExcelPDFConfiguration[] excelPDFConfigurations) {
        // Assign provided list directly into this class' columnsToRead
        configurationFields = excelPDFConfigurations;

        // Create a configuration file for future use
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilepath))) {
            String fileContent = "";
            for (ExcelPDFConfiguration currentConfField : excelPDFConfigurations) {
                fileContent = fileContent + currentConfField.toString() + "\n";
            }
            writer.write(fileContent);

            System.out.println("User configuration file created:");
            System.out.println(destinationFilepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the entire collection of ExcelPDFConfigurations obtained from the configuration file.
     * @return                          All ExcelPDFConfigurations configuration objects.
     */
    public ExcelPDFConfiguration[] getConfigurationFields() {
        return configurationFields;
    }

    /**
     * Getter for the configuration filename.
     * Important for maintaining a standard format across the application.
     * @return                      The configuration filename.
     */
    public static String getFilename() {
        return filename;
    }
}

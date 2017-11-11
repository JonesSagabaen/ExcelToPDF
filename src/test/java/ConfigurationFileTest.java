import configuration.ConfigurationFile;
import configuration.ExcelPDFConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigurationFileTest {

    private final static String configurationFilepath = "src/test/resources/";

    @Test
    public void readConfigurationFile() throws Exception {
        ConfigurationFile configurationFile = new ConfigurationFile(configurationFilepath + ConfigurationFile.getFilename());
        ExcelPDFConfiguration[] actualConfigurationsSet = configurationFile.getConfigurationFields();
        Assert.assertEquals("name", actualConfigurationsSet[0].getTargetExcelColumnName());
        Assert.assertEquals("id number", actualConfigurationsSet[1].getTargetExcelColumnName());
        Assert.assertEquals("birthday", actualConfigurationsSet[2].getTargetExcelColumnName());
        Assert.assertEquals("anniversary", actualConfigurationsSet[3].getTargetExcelColumnName());
    }

    @Test
    public void writeConfigurationFile() throws Exception {
        final String testDirectory = "out/test/resources";
        // Perform cleanup before running test
        File fileToDelete = new File(testDirectory + ConfigurationFile.getFilename());
        if (!fileToDelete.delete()) {
            System.out.println("No file to delete.");
        }

        // Create configuration file
        ExcelPDFConfiguration config1 = new ExcelPDFConfiguration("name", 100, 100, 12);
        ExcelPDFConfiguration config2 = new ExcelPDFConfiguration("id number", 100, 120, 12);
        ExcelPDFConfiguration config3 = new ExcelPDFConfiguration("birthday", 100, 140, 12);
        ExcelPDFConfiguration[] configurationsSet = {config1, config2, config3};
        ConfigurationFile configurationFile = new ConfigurationFile(testDirectory + ConfigurationFile.getFilename(), configurationsSet);

        // Read created configuration file
        String[] actualConfigurationSetAsStrings = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(testDirectory + ConfigurationFile.getFilename()))) {
            ArrayList<String> configurationsAsString = new ArrayList<>();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                configurationsAsString.add(currentLine);
            }
            actualConfigurationSetAsStrings = configurationsAsString.toArray(new String[configurationsAsString.size()]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] expectedConfigurationSetAsStrings = {"|||name|||100|||100|||12|||",
                "|||id number|||100|||120|||12|||",
                "|||birthday|||100|||140|||12|||"};
        Assert.assertArrayEquals(expectedConfigurationSetAsStrings, actualConfigurationSetAsStrings);
    }
}

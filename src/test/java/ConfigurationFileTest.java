import configuration.*;
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
    public void readPrint() throws Exception {
        ConfigurationFile configurationFile = new ConfigurationFile(configurationFilepath + ConfigurationFile.getFilenameStandardFormat());
        ExcelPDFConfiguration[] actualConfigurationsSet = configurationFile.getConfigurationFields();
        Assert.assertEquals("111217", ((ConfigurationPrint) actualConfigurationsSet[0]).getPrintString());
    }

    @Test
    public void readLookup() throws Exception {
        ConfigurationFile configurationFile = new ConfigurationFile(configurationFilepath + ConfigurationFile.getFilenameStandardFormat());
        ExcelPDFConfiguration[] actualConfigurationsSet = configurationFile.getConfigurationFields();
        Assert.assertEquals("name", ((ConfigurationLookup) actualConfigurationsSet[1]).getExcelTargetColumn());
        Assert.assertEquals("id number", ((ConfigurationLookup) actualConfigurationsSet[2]).getExcelTargetColumn());
        Assert.assertEquals("birthday", ((ConfigurationLookup) actualConfigurationsSet[5]).getExcelTargetColumn());
        Assert.assertEquals("anniversary", ((ConfigurationLookup) actualConfigurationsSet[6]).getExcelTargetColumn());
    }

    @Test
    public void readCheckbox() throws Exception {
        ConfigurationFile configurationFile = new ConfigurationFile(configurationFilepath + ConfigurationFile.getFilenameStandardFormat());
        ExcelPDFConfiguration[] actualConfigurationsSet = configurationFile.getConfigurationFields();
        ConfigurationCheckbox confCheckbox = (ConfigurationCheckbox) actualConfigurationsSet[4];
        confCheckbox.setCheckboxSelection("yes");
        Assert.assertEquals("newsletter signup", confCheckbox.getExcelTargetColumn());
        Assert.assertEquals(Integer.parseInt("431"), confCheckbox.getX());
        Assert.assertEquals(Integer.parseInt("455"), confCheckbox.getY());
    }

    @Test
    public void writeReadFile() throws Exception {
        final String testDirectory = "out/test/resources";
        // Perform cleanup before running test
        File fileToDelete = new File(testDirectory + ConfigurationFile.getFilenameStandardFormat());
        if (!fileToDelete.delete()) {
            System.out.println("No file to delete.");
        }

        // Create configuration file
        String[] configConfPrint = {"20", "Saturday", "500", "580"};
        ExcelPDFConfiguration config1 = new ConfigurationPrint(configConfPrint);
        String[] configConfLookup = {"12", "id number", "100", "120"};
        ExcelPDFConfiguration config2 = new ConfigurationLookup(configConfLookup);
        String[] configConfCheckbox = {"20", "still employed", "yes", "210", "400", "no", "250", "400"};
        ExcelPDFConfiguration config3 = new ConfigurationCheckbox(configConfCheckbox);
        ExcelPDFConfiguration[] configurationsSet = {config1, config2, config3};
        new ConfigurationFile(testDirectory + ConfigurationFile.getFilenameStandardFormat(), configurationsSet);

        // Read created configuration file
        String[] actualConfigurationSetAsStrings = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(testDirectory + ConfigurationFile.getFilenameStandardFormat()))) {
            ArrayList<String> configurationsAsString = new ArrayList<>();
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                configurationsAsString.add(currentLine);
            }
            actualConfigurationSetAsStrings = configurationsAsString.toArray(new String[configurationsAsString.size()]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] expectedConfigurationSetAsStrings = {"|||print|||20|||Saturday|||500|||580|||",
                "|||excelLookup|||12|||id number|||100|||120|||",
                "|||checkbox|||20|||still employed|||yes|||210|||400|||no|||250|||400|||"};
        Assert.assertArrayEquals(expectedConfigurationSetAsStrings, actualConfigurationSetAsStrings);
    }
}

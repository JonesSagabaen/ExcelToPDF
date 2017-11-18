import excel.ExcelReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExcelReaderTest {

    private static ExcelReader testDocReader = null;

    @Before
    public void setUp() throws Exception {
        final String excelFilepath = "src/test/resources/Personnel.xlsx";
        testDocReader = new ExcelReader(excelFilepath, 2);
    }

    @Test
    public void readEntireExcelXlsx() throws Exception {
        String actualOutput = testDocReader.readEntireDocument();
        String expectedOutput = "||||ID Digits|||||||Department|Security|Additional Notes||\n" +
                "|Row #|Name|ID Number|1|2|3|4|5|Birthday|Anniversary|||||\n" +
                "|1|Takahashi, Ryo|55501|5|5|5|0|1|05/18/80|09/XX/92|PR|Tier 3|Blue blazer|\n" +
                "|2|Urbosa, Menat-Darud|55502|5|5|5|0|2|10/22/74|04/30/01|Facilities|Denied|N/A|\n" +
                "|3|Collins, Phil|55503|5|5|5|0|3|02/30/02|12/01/11|Researcher|Tier 2|5’3”|\n" +
                "|4|Collins, Cecille|3940|3|9|4|0||01/XX/99||Intern|Temp|3.2.1.0|\n" +
                "||Collins, Phil|55503|5|5|5|0|3|02/30/02|12/01/11|Researcher|Tier 2|5’3”|\n" +
                "|5|Boudaux, Franc|00397|0|0|3|9|7|XX/XX/89|XX/XX/01|Kitchen||6:31:12:00:00|\n" +
                "|Line Separator||||||||||||||\n" +
                "|6|Dellany, Tammy|TBD|TBD|||||02/16/92|03/16/17|Facilities|Denied|Under review|";
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void findAHeaderColumn() throws Exception {
        int actualColumnIndex = testDocReader.getHeaderColumnIndex("Birthday");
        int expectedColumnIndex = 10;
        Assert.assertEquals(expectedColumnIndex, actualColumnIndex);
    }

    @Test
    public void invalidHeaderColumnToFind() throws Exception {
        int actualColumnIndex = testDocReader.getHeaderColumnIndex("invalid_column_name");
        int expectedColumnIndex = -1;
        Assert.assertEquals(expectedColumnIndex, actualColumnIndex);
    }

    @Test
    public void matchingSingleName() throws Exception {
        String[] actualNamesList = testDocReader.listMatchingNames("ryo");
        String[] expectedNamesList = {"Takahashi, Ryo"};
        Assert.assertArrayEquals(expectedNamesList, actualNamesList);
    }

    @Test
    public void matchingMultipleNames() throws Exception {
        String[] actualNamesList = testDocReader.listMatchingNames("Collins");
        String[] expectedNamesList = {"Collins, Phil", "Collins, Cecille"};
        Assert.assertArrayEquals(expectedNamesList, actualNamesList);
    }

    @Test
    public void matchingNoNames() throws Exception {
        String[] actualNamesList = testDocReader.listMatchingNames("Bubsy");
        String[] expectedNamesList = {};
        Assert.assertArrayEquals(expectedNamesList, actualNamesList);
    }

    @Test
    public void rowFromNameMatch() throws Exception {
        int actualRowNameMatch = testDocReader.getRowFromName("tam");
        int expectedRowNameMatch = 9;
        Assert.assertEquals(expectedRowNameMatch, actualRowNameMatch);
    }

    @Test
    public void rowFromNameNoMatch() throws Exception {
        int actualRowNameMatch = testDocReader.getRowFromName("Danley");
        int expectedRowNameMatch = -1;
        Assert.assertEquals(expectedRowNameMatch, actualRowNameMatch);
    }

    @Test
    public void lookupSingleRowInformation() throws Exception {
        String actualInfoObtained = testDocReader.getFieldFromRow(2, "name");
        String expectedInfoObtained = "Takahashi, Ryo";
        Assert.assertEquals(expectedInfoObtained, actualInfoObtained);
    }

    @Test
    public void getInfoFromFormulaResult() throws Exception {
        String[] desireInfo = {"Name", "ID Number", "Birthday", "Department"};
        String[] actualInfoObtained = testDocReader.getFieldsFromRow(2, desireInfo);
        String[] expectedInfoObtained = {"Takahashi, Ryo", "55501", "05/18/80", "PR"};
        Assert.assertArrayEquals(expectedInfoObtained, actualInfoObtained);
    }
}
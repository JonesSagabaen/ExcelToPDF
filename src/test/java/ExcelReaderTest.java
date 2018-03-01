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
        String actualOutput = testDocReader.readExcelDocument();
        String expectedOutput = "||||||ID Digits|||||||Department|Security|Additional Notes||\n" +
                "|Row #|Name|Email Address|Newsletter Signup|ID Number|1|2|3|4|5|Birthday|Anniversary|||||\n" +
                "|1|Takahashi, Ryo|ryotakahashi@testgmail.com|Yes|55501|5|5|5|0|1|05/18/80|09/XX/92|PR|Tier 3|Blue blazer|\n" +
                "|2|Urbosa, Mena-Darud|durbosa@testgmail.com|Yes|55502|5|5|5|0|2|10/22/74|04/30/01|Facilities|Denied|N/A|\n" +
                "|3|Collins, Phil|therealphilcollins@testaol.com||55503|5|5|5|0|3|02/30/02|12/01/11|Researcher|Tier 2|5’3”|\n" +
                "|4|Collins, Cecille|cecille-collins@testtumblr.com|No|3940|3|9|4|0||01/XX/99||Intern|Temp|3.2.1.0|\n" +
                "||Collins, Phil|therealphilcollins@testaol.com||55503|5|5|5|0|3|02/30/02|12/01/11|Researcher|Tier 2|5’3”|\n" +
                "|5|Boudaux, Franc|||00397|0|0|3|9|7|XX/XX/89|XX/XX/01|Kitchen||6:31:12:00:00|\n" +
                "|Line Separator||||||||||||||||\n" +
                "|6|Dellany, Tammy|tamtam1988@testhotmail.com|No|TBD|TBD|||||02/16/92|03/16/17|Facilities|Denied|Under review|\n" +
                "|7|Sapperstein, Donny|donsap@testgmail.com|Yes|00629|0|0|6|2|9||05/01/14|Facilities|Tier 3|-|\n" +
                "|8|Blake, Kerry|kerryblake@testyahoo.com|Yes|||||||10/19/91|06/11/12|Facilities|Tier 3|N/A|\n" +
                "|9|Two, Timbuk|timbuk2@testgmail.com|Yes|7|7|||||11/22/90|02/29/15|Facilities|Tier 3|N/A|";
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void findAHeaderColumn() throws Exception {
        int actualColumnIndex = testDocReader.getColumnIndex("Birthday");
        int expectedColumnIndex = 10;
        Assert.assertEquals(expectedColumnIndex, actualColumnIndex);
    }

    @Test
    public void invalidHeaderColumnToFind() throws Exception {
        int actualColumnIndex = testDocReader.getColumnIndex("invalid_column_name");
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
        int actualRowNameMatch = testDocReader.getRowIndex("tam");
        int expectedRowNameMatch = 9;
        Assert.assertEquals(expectedRowNameMatch, actualRowNameMatch);
    }

    @Test
    public void rowFromNameNoMatch() throws Exception {
        int actualRowNameMatch = testDocReader.getRowIndex("Danley");
        int expectedRowNameMatch = -1;
        Assert.assertEquals(expectedRowNameMatch, actualRowNameMatch);
    }

    @Test
    public void lookupSingleRowInformation() throws Exception {
        String actualInfoObtained = testDocReader.lookupDetail(2, "name");
        String expectedInfoObtained = "Takahashi, Ryo";
        Assert.assertEquals(expectedInfoObtained, actualInfoObtained);
    }

    @Test
    public void getInfoFromFormulaResult() throws Exception {
        String[] desireInfo = {"Name", "ID Number", "Birthday", "Department"};
        String[] actualInfoObtained = testDocReader.lookupDetails(2, desireInfo);
        String[] expectedInfoObtained = {"Takahashi, Ryo", "55501", "05/18/80", "PR"};
        Assert.assertArrayEquals(expectedInfoObtained, actualInfoObtained);
    }

    @Test
    public void readExcelFormulaCellValid() throws Exception {
        int rowNumber = testDocReader.getRowIndex("Sapperstein, Donny");
        String actualResult = testDocReader.lookupDetail(rowNumber, "ID Number");
        String expectedResult = "00629";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void readExcelFormulaCellBlank() throws Exception {
        int rowNumber = testDocReader.getRowIndex("Blake, Kerry");
        String actualResult = testDocReader.lookupDetail(rowNumber, "ID Number");
        String expectedResult = "";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void readExcelFormulaCellShort() throws Exception {
        int rowNumber = testDocReader.getRowIndex("Two, Timbuk");
        String actualResult = testDocReader.lookupDetail(rowNumber, "ID Number");
        String expectedResult = "7";
        Assert.assertEquals(expectedResult, actualResult);
    }
}
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import org.junit.Assert;
import org.junit.Test;
import pdf.WriteToPDF;

import java.io.File;
import java.io.InvalidObjectException;

public class WriteToPDFTest {

    private final String inputPDF = "src/test/resources/SignUpForm.pdf";

    @Test
    public void setPrintColor_blackColor() throws Exception {
        final String outputPDF = "out/test/resources/notSupposedToExist.pdf";

        WriteToPDF writeToPDF = new WriteToPDF(inputPDF, outputPDF);
        writeToPDF.setPrintTextColor("black");
        Color resultColor = writeToPDF.getPrintTextColor();
        Assert.assertEquals(Color.BLACK, resultColor);
    }

    @Test
    public void setPrintColor_redColor() throws Exception {
        final String outputPDF = "out/test/resources/notSupposedToExist.pdf";

        WriteToPDF writeToPDF = new WriteToPDF(inputPDF, outputPDF);
        writeToPDF.setPrintTextColor("red");
        Color resultColor = writeToPDF.getPrintTextColor();
        Assert.assertEquals(Color.RED, resultColor);
    }

    @Test
    public void setPrintColor_blueColor() throws Exception {
        final String outputPDF = "out/test/resources/notSupposedToExist.pdf";

        WriteToPDF writeToPDF = new WriteToPDF(inputPDF, outputPDF);
        writeToPDF.setPrintTextColor("blue");
        Color resultColor = writeToPDF.getPrintTextColor();
        Assert.assertEquals(Color.BLUE, resultColor);
    }

    @Test(expected = InvalidObjectException.class)
    public void setPrintColor_invalid() throws Exception {
        final String outputPDF = "out/test/resources/notSupposedToExist.pdf";

        WriteToPDF writeToPDF = new WriteToPDF(inputPDF, outputPDF);
        writeToPDF.setPrintTextColor("rainbow");
    }

    @Test
    public void writeInPDF() throws Exception {
        final String outputPDF = "out/test/resources/writeInPDF_test.pdf";

        // Perform cleanup before running test
        File fileToDelete = new File(outputPDF);
        if (!fileToDelete.delete()) {
            System.out.println("No file to delete.");

        }

        // Write in document fields
        WriteToPDF writeToPDF = new WriteToPDF(inputPDF, outputPDF);
        writeToPDF.writeTextField("11", 419, 602, 18, 50);
        writeToPDF.writeTextField("01", 475 , 602, 18, 50);
        writeToPDF.writeTextField("2017", 517, 602, 18, 50);
        writeToPDF.writeTextField("Sagabaen, Jones", 210, 543, 20, 500);
        writeToPDF.writeTextField("00001", 210, 510, 20, 500);
        writeToPDF.writeTextField("01/01/1900", 140, 468, 20, 500);
        writeToPDF.writeTextField("11/01/2017", 382, 468, 20, 500);
        PdfDocument pdfDocument = writeToPDF.generatePdf();
        Assert.assertNotNull(pdfDocument);
    }

    @Test
    public void noFileGenerated() throws Exception {
        final String outputPDF = "out/test/resources/notSupposedToExist.pdf";

        // Write file but delete it
        WriteToPDF writeToPDF = new WriteToPDF(inputPDF, outputPDF);
        writeToPDF.writeTextField("Sagabaen, Jones", 210, 543, 20, 500);
        writeToPDF.closePdf();

        File fileCheck = new File(outputPDF);
        Assert.assertFalse(fileCheck.exists());
    }
}

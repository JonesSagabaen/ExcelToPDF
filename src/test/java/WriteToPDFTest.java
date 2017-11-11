import com.itextpdf.kernel.pdf.PdfDocument;
import org.junit.Assert;
import org.junit.Test;
import pdf.WriteToPDF;

import java.io.File;

public class WriteToPDFTest {

    private final String inputPDF = "src/test/resources/SignUpForm.pdf";

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
        writeToPDF.fillInTextField("11", 419, 602, 18, 50);
        writeToPDF.fillInTextField("01", 475 , 602, 18, 50);
        writeToPDF.fillInTextField("2017", 517, 602, 18, 50);
        writeToPDF.fillInTextField("Sagabaen, Jones", 210, 543, 20, 500);
        writeToPDF.fillInTextField("00001", 210, 510, 20, 500);
        writeToPDF.fillInTextField("01/01/1900", 140, 468, 20, 500);
        writeToPDF.fillInTextField("11/01/2017", 382, 468, 20, 500);
        PdfDocument pdfDocument = writeToPDF.generatePdf();
        Assert.assertNotNull(pdfDocument);
    }

    @Test
    public void noFileGenerated() throws Exception {
        final String outputPDF = "out/test/resources/notSupposedToExist.pdf";

        // Write file but delete it
        WriteToPDF writeToPDF = new WriteToPDF(inputPDF, outputPDF);
        writeToPDF.fillInTextField("Sagabaen, Jones", 210, 543, 20, 500);
        writeToPDF.closePdf();

        File fileCheck = new File(outputPDF);
        Assert.assertFalse(fileCheck.exists());
    }
}

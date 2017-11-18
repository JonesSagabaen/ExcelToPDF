package pdf;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WriteToPDF {

    /**
     * The PDF document that will be used for being written on top of.
     */
    private String basePDF;

    /**
     * The output PDF after making all the document changes.
     */
    private String outputPDF;

    /**
     * The PDF document passed by this class.
     */
    private PdfDocument pdfDocument;

    /**
     * The document in the format that is ready to be manipulated.
     */
    private Document manipulatingDocument;

    private Color printTextColor;

    /**
     * Initilize the PDF document that will be manipulated.
     * @param basePDF           The filepath to the PDF document that will be used as a basis of manipulation.
     * @param outputPDF         The filepath for outputting the PDF document after manipulation.
     */
    public WriteToPDF(String basePDF, String outputPDF) throws IOException {
        // Check if basePDF is a valid document
        File file = new File(basePDF);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }

        // Assign variables
        this.basePDF = basePDF;
        this.outputPDF = outputPDF;
        printTextColor = Color.RED;

        //Initialize PDF document
        PdfDocument pdfDoc = null;
        pdfDocument = new PdfDocument(new PdfReader(this.basePDF), new PdfWriter(this.outputPDF));
        manipulatingDocument = new Document(pdfDocument);

        System.out.println("PDF document set:");
        System.out.println("Filepath: " + this.basePDF);
    }

    public Color getPrintTextColor() {
        return printTextColor;
    }

    public void setPrintTextColor(Color printTextColor) {
        this.printTextColor = printTextColor;
    }

    /**
     * Write text onto the PDF document given the text, font size and coordinates.
     * @param textInput         The text to enter.
     * @param fontSize          The font size.
     * @param x                 The x coordinate originating from the bottom-left of the PDF document.
     * @param y                 The y coordinate originating from the bottom-left of the PDF document.
     * @param occupyingWidth    The total width size allowed for the given text to enter.
     */
    public void fillInTextField(String textInput, int fontSize, int x, int y, int occupyingWidth) {
        try {
            Text text = new Text(textInput)
                    .setFontColor(printTextColor)
                    .setFontSize(fontSize)
                    .setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN));

            Paragraph paragraph = new Paragraph(text).setMargin(0);
            paragraph.setFixedPosition(x, y, occupyingWidth);
            manipulatingDocument.add(paragraph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generate manipulated PDF document.
     * @return                  The generated PDF document.
     */
    public PdfDocument generatePdf() {
        // Close documents
        manipulatingDocument.close();
        pdfDocument.close();
        return pdfDocument;
    }

    /**
     * Close document writers and cleanup output file since no modifications were made to base PDF.
     */
    public void closePdf() {
        // Close documents
        manipulatingDocument.close();
        pdfDocument.close();

        // Remove generated file since no modifications were done to base PDF
        File temporaryFile = new File(outputPDF);
        temporaryFile.delete();
    }

    /**
     * Get the name of the output PDF.
     * @return                  The name of the PDF document that will be output.
     */
    public String getOutputPDF() {
        return outputPDF;
    }
}

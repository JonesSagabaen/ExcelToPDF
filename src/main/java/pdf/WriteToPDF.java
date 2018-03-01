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
import java.io.InvalidObjectException;

public class WriteToPDF {

    /**
     * The PDF document that will be used as a basis.
     */
    private String basePDF;

    /**
     * The output PDF after making writing annotations against the base PDF.
     */
    private String outputPDF;

    /**
     * The PDF document object used during writing manipulations.
     */
    private PdfDocument pdfDocument;

    /**
     * The generic document object used during writing manipulations.
     */
    private Document manipulatingDocument;

    /**
     * The color for writing annotations to be made.
     */
    private Color printTextColor;

    /**
     * Initialize the PDF document that will be manipulated.
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

        //Initialize PDF document
        PdfDocument pdfDoc = null;
        pdfDocument = new PdfDocument(new PdfReader(this.basePDF), new PdfWriter(this.outputPDF));
        manipulatingDocument = new Document(pdfDocument);

        System.out.println("PDF document set:");
        System.out.println("Filepath: " + this.basePDF);
    }

    /**
     * Get the color to be used by writing annotations that are to be done.
     * @return                  The color set for writing annotations.
     */
    public Color getPrintTextColor() {
        return printTextColor;
    }

    /**
     * Set the color for writing annotations that are to be done.
     * @param printTextColor    All possible text color options that is available for the user to select.
     * @throws InvalidObjectException
     */
    public void setPrintTextColor(String printTextColor) throws InvalidObjectException {
        if (printTextColor.equalsIgnoreCase("black"))
            this.printTextColor = Color.BLACK;
        else if (printTextColor.equalsIgnoreCase("red"))
            this.printTextColor = Color.RED;
        else if (printTextColor.equalsIgnoreCase("blue"))
            this.printTextColor = Color.BLUE;
        else
            throw new InvalidObjectException("Could not handle color parameter given.");
    }

    /**
     * Write text onto the PDF document given the text, font size and coordinates.
     * @param textInput         The text to write.
     * @param fontSize          The font size.
     * @param x                 The x coordinate originating from the bottom-left of the PDF document.
     * @param y                 The y coordinate originating from the bottom-left of the PDF document.
     * @param occupyingWidth    The total width size allowed for the given text to enter.
     */
    public void writeTextField(String textInput, int fontSize, int x, int y, int occupyingWidth) {
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

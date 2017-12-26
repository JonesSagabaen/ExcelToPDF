package gui.configuration.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PrintPanel {
    private JPanel MainView;
    private JPanel excelPanel;
    private JPanel pdfPanel;
    private JTextField fontSizeField;
    private JTextField printTextField;
    private JTextField xCoordinateField;
    private JTextField yCoordinateField;

    public PrintPanel() {
        setFurtherUICustomizations();
    }

    /**
     * Front-end container for entire panel.
     * @return  The main panel of this entire form.
     */
    public JPanel getMainView() {
        return MainView;
    }

    public String getFontSizeFieldContent() {
        return fontSizeField.getText();
    }

    public String getPrintTextFieldContent() {
        return printTextField.getText();
    }

    public String getXCoordinateFieldContent() {
        return xCoordinateField.getText();
    }

    public String getYCoordinateFieldContent() {
        return yCoordinateField.getText();
    }

    /**
     * All further UI customizations are contained here where commenting out this method will still run the app
     * but without these customizations.
     */
    private void setFurtherUICustomizations() {
        // Titled borders
        TitledBorder titledExcelArea = new TitledBorder("Excel");
        excelPanel.setBorder(titledExcelArea);

        TitledBorder titledPdfArea = new TitledBorder("PDF");
        pdfPanel.setBorder(titledPdfArea);

    }

}

package gui.configuration.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LookupPanel {
    private JPanel MainView;
    private JPanel excelPanel;
    private JPanel pdfPanel;
    private JTextField fontSizeField;
    private JTextField columnNameField;
    private JTextField xCoordinateField;
    private JTextField yCoordinateField;

    public LookupPanel() {
        setFurtherUICustomizations();
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

    /**
     * Front-end container for entire panel.
     * @return  The main panel of this entire form.
     */
    public JPanel getMainView() {
        return MainView;
    }

    /**
     * Construct the string representation of this configuration type.
     * @return  String array of this LookupPanel configuration type.
     */
    public String[] stringConfig() {
        return new String[]{fontSizeField.getText(), columnNameField.getText(), xCoordinateField.getText(), yCoordinateField.getText()};
    }
}

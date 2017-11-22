package gui.configuration;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LookupPanel {
    private JPanel MainView;
    private JPanel excelPanel;
    private JPanel pdfPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton removeButton;

    public LookupPanel() {
        setFurtherUICustomizations();
    }

    /**
     * Main method for passing this to other GUI classes.
     * @return  The main panel of this entire form.
     */
    public JPanel getMainView() {
        return MainView;
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

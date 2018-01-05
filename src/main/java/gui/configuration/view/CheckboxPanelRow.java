package gui.configuration.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;

public class CheckboxPanelRow {
    private JPanel MainView;
    private JPanel excelPanel;
    private JPanel pdfPanel;
    private JTextField conditionTextField;
    private JTextField xCoordinateField;
    private JTextField yCoordinateField;
    private JButton removeConditionButton;
    private JButton addConditionButton;
    private JPanel rowPanel;

    /**
     * Reference to the parent CheckboxPanel that this single condition row belongs to.
     */
    private CheckboxPanel parentPanel;

    public CheckboxPanelRow(CheckboxPanel parentPanel) {
        this.parentPanel = parentPanel;
        setFurtherUICustomizations();

        // Action listener for adding a condition to the view
        removeConditionButton.addActionListener((ActionEvent e) -> {
            parentPanel.removeConditionRow(this);
        });
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
    public JPanel getMainView() { return MainView; }

    public String getConditionTextFieldContent() {
        return conditionTextField.getText();
    }

    public String getXCoordinateFieldContent() {
        return xCoordinateField.getText();
    }

    public String getYCoordinateFieldContent() {
        return yCoordinateField.getText();
    }
}

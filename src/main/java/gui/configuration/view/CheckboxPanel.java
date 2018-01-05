package gui.configuration.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CheckboxPanel {
    private JPanel MainView;
    private JPanel body;
    private JPanel addButtonPanel;
    private JButton addConditionButton;
    private JPanel commonPanel;
    private JTextField fontSizeField;
    private JPanel borderPanel;
    private JTextField excelLookupField;

    /**
     * References to all condition rows displayed in the UI.
     */
    private ArrayList<CheckboxPanelRow> conditionRows;

    public CheckboxPanel() {
        conditionRows = new ArrayList<>();
        initializeMainBodyPanel();
        setFurtherUICustomizations();

        // Action listener for adding a condition to the view
        addConditionButton.addActionListener((ActionEvent e) -> {
            addConditionRow();
        });
    }

    /**
     * Initialize main body panel that will contain configuration rows and the overall add button
     */
    private void initializeMainBodyPanel() {
        // BoxLayout layout manager is used for adding new ConfigurationRow components to this view
        // Note: body JPanel in CheckboxPanel.form needs "Layout Manager" variable to not be GridLayoutManager (IntelliJ)
        //       in order to prevent a NullPointerException at runtime.
        body.setLayout(new BoxLayout(body, BoxLayout.PAGE_AXIS));
        addConditionRow();
    }

    /**
     * All further UI customizations are contained here where commenting out this method will still run the app
     * but without these customizations.
     */
    private void setFurtherUICustomizations() {
        // Lined border
        borderPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    /**
     * Add a new condition row to the UI.
     */
    public void addConditionRow() {
        CheckboxPanelRow conditionToAdd = new CheckboxPanelRow(this);
        conditionRows.add(conditionToAdd);
        body.add(conditionToAdd.getMainView());
        body.revalidate();
        body.repaint();
    }

    /**
     * Remove a condition row from the UI.
     * @param rowToDelete
     */
    public void removeConditionRow(CheckboxPanelRow rowToDelete) {
        body.remove(conditionRows.indexOf(rowToDelete));
        conditionRows.remove(rowToDelete);
        body.revalidate();
        body.repaint();
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
     * @return  String array of this CheckboxPanel configuration type.
     */
    public String[] stringConfig() {
        ArrayList<String> returnString = new ArrayList<>();
        returnString.add(fontSizeField.getText());
        returnString.add(excelLookupField.getText());
        for (CheckboxPanelRow conditionRow : conditionRows) {
            returnString.add(conditionRow.getConditionTextFieldContent());
            returnString.add(conditionRow.getXCoordinateFieldContent());
            returnString.add(conditionRow.getYCoordinateFieldContent());
        }
        return returnString.toArray(new String[conditionRows.size()]);
    }
}

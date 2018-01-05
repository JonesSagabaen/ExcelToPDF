package gui.configuration.view;

import gui.configuration.controller.Controller;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ConfigurationRow {
    private JPanel MainView;
    private JComboBox configTypeComboBox;
    private JButton removeButton;
    private JPanel configPanel;

    private Controller controller;

    private Object configObject;

    public ConfigurationRow() {
        // Initilize contents
        PrintPanel newPrintPanel = new PrintPanel();
        configObject = newPrintPanel;
        configPanel.add(newPrintPanel.getMainView());

        // Action listener for the selection type drop-down
        configTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //System.out.println("[DEBUG] ComboBox item: " + e.getItem());
                //System.out.println("[DEBUG] ComboBox state: " + e.getStateChange());
                if (e.getStateChange() == 1) {
                    String selectedItem = e.getItem().toString();
                    switch (selectedItem) {
                        case "Print":
                            configPanel.removeAll();
                            PrintPanel newPrintPanel = new PrintPanel();
                            configObject = newPrintPanel;
                            configPanel.add(newPrintPanel.getMainView());
                            configPanel.revalidate();
                            configPanel.repaint();
                            break;
                        case "Lookup":
                            configPanel.removeAll();
                            LookupPanel newLookupPanel = new LookupPanel();
                            configObject = newLookupPanel;
                            configPanel.add(newLookupPanel.getMainView());
                            configPanel.revalidate();
                            configPanel.repaint();
                            break;
                        case "Checkbox":
                            configPanel.removeAll();
                            CheckboxPanel newCheckboxPanel = new CheckboxPanel();
                            configObject = newCheckboxPanel;
                            configPanel.add(newCheckboxPanel.getMainView());
                            configPanel.revalidate();
                            configPanel.repaint();
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown ComboxBox selection chosen");
                    }
                }
            }
        });
    }

    /**
     * Front-end container for entire panel.
     * @return  The main panel of this entire form.
     */
    public JPanel getMainView() {
        return MainView;
    }

    /**
     * Get the currently selected configuration type.
     * @return  Selected item from configuration ComboBox.
     */
    public String getComboBoxSelectedItem() {
        return configTypeComboBox.getSelectedItem().toString();
    }

    /**
     * Package whatever configuration input by the user and pass it on for generating the configuration file.
     * @return  An array ready to be used for generating configuration file.
     */
    public String[] generateConfigArray() {
        String[] outputArray = {};
        String selectComboOption = getComboBoxSelectedItem();
        switch (selectComboOption) {
            case "Print":
                PrintPanel printPanelView = (PrintPanel) configObject;
                // TODO: Move this logic over to PrintPanel
                outputArray = new String[]{printPanelView.getFontSizeFieldContent(), printPanelView.getPrintTextFieldContent(), printPanelView.getXCoordinateFieldContent(), printPanelView.getYCoordinateFieldContent()};
                break;
            case "Lookup":
                LookupPanel lookupPanelView = (LookupPanel) configObject;
                // TODO: Move this logic over to LookupPanel
                outputArray = new String[]{lookupPanelView.getFontSizeFieldContent(), lookupPanelView.getColumnNameFieldContent(), lookupPanelView.getXCoordinateFieldContent(), lookupPanelView.getYCoordinateFieldContent()};
                break;
            case "Checkbox":
                CheckboxPanel checkboxPanelView = (CheckboxPanel) configObject;
                outputArray = checkboxPanelView.stringConfig();
                break;
            default:
                throw new IllegalArgumentException("Unknown ComboxBox selection chosen");
        }
        return outputArray;
    }

    public void addController(ActionListener controller){
        System.out.println("[View ConfigurationRow] Adding controller");
        removeButton.addActionListener(controller);
    }
}

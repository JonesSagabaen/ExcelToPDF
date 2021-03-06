package gui.configuration.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import gui.configuration.controller.Controller;

import javax.swing.*;
import java.awt.*;
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
     *
     * @return The main panel of this entire form.
     */
    public JPanel getMainView() {
        return MainView;
    }

    /**
     * Get the currently selected configuration type.
     *
     * @return Selected item from configuration ComboBox.
     */
    public String getComboBoxSelectedItem() {
        return configTypeComboBox.getSelectedItem().toString();
    }

    /**
     * Package whatever configuration input by the user and pass it on for generating the configuration file.
     *
     * @return An array ready to be used for generating configuration file.
     */
    public String[] generateConfigArray() {
        String[] outputArray = {};
        String selectComboOption = getComboBoxSelectedItem();
        switch (selectComboOption) {
            case "Print":
                PrintPanel printPanelView = (PrintPanel) configObject;
                outputArray = printPanelView.stringConfig();
                break;
            case "Lookup":
                LookupPanel lookupPanelView = (LookupPanel) configObject;
                outputArray = lookupPanelView.stringConfig();
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

    public void addController(ActionListener controller) {
        System.out.println("[View ConfigurationRow] Adding controller");
        removeButton.addActionListener(controller);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainView = new JPanel();
        MainView.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        MainView.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        configTypeComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Print");
        defaultComboBoxModel1.addElement("Lookup");
        defaultComboBoxModel1.addElement("Checkbox");
        configTypeComboBox.setModel(defaultComboBoxModel1);
        panel1.add(configTypeComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        configPanel = new JPanel();
        configPanel.setLayout(new BorderLayout(0, 0));
        panel2.add(configPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        removeButton = new JButton();
        removeButton.setText("-");
        panel3.add(removeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainView;
    }
}

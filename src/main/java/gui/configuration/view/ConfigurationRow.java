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

    public ConfigurationRow() {
        configTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
//                System.out.println("[DEBUG] ComboBox item: " + e.getItem());
//                System.out.println("[DEBUG] ComboBox state: " + e.getStateChange());
                if (e.getStateChange() == 1) {
                    if (e.getItem().equals("Print")) {
                        configPanel.removeAll();
                        // TODO
                        configPanel.revalidate();
                        configPanel.repaint();
                    }
                    else if (e.getItem().equals("Lookup")) {
                        configPanel.removeAll();
                        LookupPanel newLookupPanel = new LookupPanel();
                        configPanel.add(newLookupPanel.getMainView());
                        configPanel.revalidate();
                        configPanel.repaint();
                    }
                    else if (e.getItem().equals("Checkbox")) {
                        configPanel.removeAll();
                        // TODO
                        configPanel.revalidate();
                        configPanel.repaint();
                    }
                    else
                        throw new IllegalArgumentException("Unknown ComboxBox selection chosen");
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

    private JPanel getConfigPanel() {
        return configPanel;
    }

    private void setConfigPanel(JPanel configPanel) {
        this.configPanel = configPanel;
    }

    public void addController(ActionListener controller){
        System.out.println("[View ConfigurationRow] Adding controller");
        removeButton.addActionListener(controller);
    }
}

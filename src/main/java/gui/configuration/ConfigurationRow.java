package gui.configuration;

import javax.swing.*;

public class ConfigurationRow {
    private JPanel MainView;
    private JPanel excel;
    private JPanel pdf;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel rowButtons;
    private JButton removeButton;
    private JButton addButton;

    /**
     * Main method for passing this to other GUI classes.
     * @return  The main panel of this entire form.
     */
    public JPanel getMainView() {
        return MainView;
    }
}

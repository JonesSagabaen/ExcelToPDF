package gui.configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Configure {
    private JPanel MainView;
    private JPanel marginsPanel;
    private JTextArea headerDescription;
    private JPanel bodyContainer;
    private JPanel body;

    /**
     * List of configuration rows to be displayed.
     */
    private ArrayList<LookupPanel> configurationRows;

    /**
     * Panel containing the add button.
     */
    private JPanel addButtonPanel;

    // Used for debugging since this UI component should be launched from ExcelToPDF.java
    public static void main(String[] args) {
        JFrame frame = new JFrame("Create configuration file");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new Configure().MainView);
        frame.setSize(600, 500);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Configure() {
        // BoxLayout layout manager is used for adding new ConfigurationRow components to this view
        // Note: body JPanel in Configure.form needs "Layout Manager" variable to not be GridLayoutManager (IntelliJ)
        //       in order to prevent a NullPointerException at runtime.
        body.setLayout(new BoxLayout(body, BoxLayout.PAGE_AXIS));
        configurationRows = new ArrayList<>();

        // Add an add button to the bottom of the list of configurations
        addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton();
        addButton.setText("+");
        addButtonPanel.add(addButton);
        body.add(addButtonPanel);

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        // Action Listeners
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        // Action for the add button
        addButton.addActionListener((ActionEvent e) -> {
            configurationRows.add(new LookupPanel());
            refreshBodyPanel();
        });
    }

    /**
     * Refresh the contents of the body panel.
     */
    private void refreshBodyPanel() {
        for (LookupPanel configurationRow : configurationRows) {
            body.add(configurationRow.getMainView());
        }
        body.add(addButtonPanel);
        body.revalidate();
        body.repaint();
    }
}

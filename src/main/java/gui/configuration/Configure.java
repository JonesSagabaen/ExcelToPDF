package gui.configuration;

import javax.swing.*;

public class Configure {
    private JPanel MainView;
    private JPanel marginsPanel;
    private JTextArea headerDescription;
    private JPanel bodyContainer;
    private JPanel body;

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
        body.add(new ConfigurationRow().getMainView());
        body.add(new ConfigurationRow().getMainView());
        body.add(new ConfigurationRow().getMainView());
    }
}

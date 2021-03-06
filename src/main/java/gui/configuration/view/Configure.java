package gui.configuration.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import gui.configuration.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Configure implements Observer {
    private JPanel MainView;
    private JTextArea headerDescription;
    private JPanel bodyContainer;
    private JPanel body;
    private JButton createButton;
    private JButton cancelButton;

    private JDialog dialog;

    /**
     * Panel containing the add button.
     */
    private JPanel addButtonPanel;

    private JButton addButton;

    public Configure() {
        dialog = new JDialog();
        dialog.setTitle("Create Configuration File");
        dialog.setModal(true);
        dialog.setContentPane(MainView);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();

        initializeMainBodyPanel();
        setFurtherUICustomizations();
    }

    /**
     * Initialize main body panel that will contain configuration rows and the body's add button
     */
    private void initializeMainBodyPanel() {
        // BoxLayout layout manager is used for adding new ConfigurationRow components to this view
        // Note: body JPanel in Configure.form needs "Layout Manager" variable to not be GridLayoutManager (IntelliJ)
        //       in order to prevent a NullPointerException at runtime.
        body.setLayout(new BoxLayout(body, BoxLayout.PAGE_AXIS));

        // Add an Add button to the bottom of the list of configurations
        addButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton();
        addButton.setText("+");
        addButtonPanel.add(addButton);
        body.add(addButtonPanel);
    }

    /**
     * All further UI customizations are contained here where commenting out this method will still run the app
     * but without these customizations.
     */
    private void setFurtherUICustomizations() {
        // Create button
        createButton.setBackground(Color.decode("#24A3FF"));
        createButton.setForeground(Color.WHITE);
        createButton.setOpaque(true);
        createButton.setFocusPainted(false);
        createButton.setBorderPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
    }

    /**
     * Method needed for implementing the Observer class.  The model of the MVC model invokes this method when
     * it executes its notifyObservers() method.
     *
     * @param o   The model that invokes this method.
     * @param arg The object that is being passed from the model to be used by this method.
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("[View Configure] Observable: " + o.getClass());
        System.out.println("[View Configure] Object passed: " + arg.getClass());
        System.out.println("[View Configure] Object content size: " + ((ArrayList) arg).size());

        // Rebuild all of the contents of the body panel which contains the visual list of
        // all configuration rows to display.
        body.removeAll();
        ArrayList<ConfigurationRow> configurationRows = (ArrayList) arg;
        for (ConfigurationRow configurationRow : configurationRows) {
            body.add(configurationRow.getMainView());
        }
        body.add(addButtonPanel);
        body.revalidate();
        body.repaint();
    }

    public void addController(ActionListener controller) {
        System.out.println("[View Configure] Adding controller");
        cancelButton.addActionListener(controller);
        addButton.addActionListener(controller);
        createButton.addActionListener(controller);
    }

    /**
     * Open the dialog window.
     */
    public void openDialogWindow() {
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    /**
     * Close the dialog window.
     */
    public void closeDialogWindow() {
        dialog.setVisible(false);
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
        MainView.setLayout(new GridLayoutManager(1, 1, new Insets(30, 30, 30, 30), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        MainView.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, BorderLayout.NORTH);
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(21);
        panel2.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null));
        headerDescription = new JTextArea();
        headerDescription.setBackground(new Color(-1250068));
        headerDescription.setEditable(false);
        headerDescription.setEnabled(true);
        headerDescription.setForeground(new Color(-16777216));
        headerDescription.setLineWrap(true);
        headerDescription.setText("Enter the information for mapping the Excel column to do a lookup to the location for where the results will be placed onto the PDF document.");
        headerDescription.setWrapStyleWord(true);
        scrollPane1.setViewportView(headerDescription);
        bodyContainer = new JPanel();
        bodyContainer.setLayout(new GridLayoutManager(2, 1, new Insets(30, 0, 0, 0), -1, -1));
        panel1.add(bodyContainer, BorderLayout.CENTER);
        final JScrollPane scrollPane2 = new JScrollPane();
        bodyContainer.add(scrollPane2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(900, 600), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane2.setViewportView(panel3);
        body = new JPanel();
        body.setLayout(new GridBagLayout());
        panel3.add(body, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, BorderLayout.SOUTH);
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        panel4.add(cancelButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createButton = new JButton();
        createButton.setText("Create File");
        panel4.add(createButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainView;
    }

    public static class CloseListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    }
}

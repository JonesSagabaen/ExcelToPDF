package gui.configuration.view;

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
    private JPanel marginsPanel;
    private JTextArea headerDescription;
    private JPanel bodyContainer;
    private JPanel body;
    private JButton createButton;

    private Model model;

    /**
     * Panel containing the add button.
     */
    private JPanel addButtonPanel;

    private JButton addButton;

//    // Need to remove JFrame reference since main method already uses it.  Can't have more than one.
//    public Configure() {
//        // Initilize main JFrame
//        JFrame frame = new JFrame("Create configuration file");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        initializeMainBodyPanel();
//        frame.setContentPane(MainView);
//        frame.setSize(880, 600);
//        frame.setResizable(true);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }

    // TODO: This entire application works against IntelliJ debugger but not from fat .JAR.
    // Attempt to fix by migrating this custom class to IntelliJ generated Dialog class, "ConfigureDialog.java."
    public Configure() {
        initializeMainBodyPanel();
    }

    /**
     * Initialize main body panel that will contain configuration rows and the overall add button
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
     * Method needed for implementing the Observer class.  The model of the MVC model invokes this method when
     * it executes its notifyObservers() method.
     * @param o     The model that invokes this method.
     * @param arg   The object that is being passed from the model to be used by this method.
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

    public void addController(ActionListener controller){
        System.out.println("[View Configure] Adding controller");
        addButton.addActionListener(controller);
        createButton.addActionListener(controller);
    }

    /**
     * Use a JOptionPanel dialog window and display the contents of the Configuration creation UI within it.
     */
    public void presentDialogWindow() {
        UIManager.put("OptionPane.minimumSize", new Dimension(950,700));
        JOptionPane.showMessageDialog(null, MainView,"Create Configuration File", JOptionPane.PLAIN_MESSAGE);
    }

    public static class CloseListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    }
}

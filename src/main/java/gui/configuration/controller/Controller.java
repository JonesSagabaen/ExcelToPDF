package gui.configuration.controller;

import gui.configuration.model.Model;
import gui.configuration.view.Configure;
import gui.configuration.view.LookupPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private Model model;

    /**
     * Reference to the main Configure view.
     * IntelliJ complains it's not used locally but the inherited methods of the MVC use it.
     */
    private Configure configureView;

    /**
     * Reference to the main Lookup panel configuration views.
     * IntelliJ complains it's not used locally but the inherited methods of the MVC use it.
     */
    private ArrayList<LookupPanel> lookupPanelViews;

    public Controller() {
        lookupPanelViews = new ArrayList<>();
    }

    /**
     * Called when the hooked up buttons, in the views, are pressed.  The buttons are hooked up by the views'
     * addController() method which adds the action listener.
     * @param e     The action event detected from the button with its action listener.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("[Controller] Acting on Model");
        System.out.println("[Controller] " + e.getActionCommand() + " button clicked");
        System.out.println("[Controller] " + e.paramString() + " " + new java.util.Date(e.getWhen()));
        if (e.getActionCommand().equals("+")) {
            model.addConfigurationRow();
        }
        else if (e.getActionCommand().equals("-")) {
            // Get the main parent JPanel of the button that was pressed so the model can identify which row to delete.
            JPanel buttonParent = (JPanel) ((JButton) e.getSource()).getParent().getParent().getParent();
            System.out.println("JButton parent: " + buttonParent);
            model.deleteConfigurationRow(buttonParent);
        }
        else
            System.out.println("[Controller] Error with action event " + e.getActionCommand());
    }

    public void addModel(Model model){
        System.out.println("[Controller] Adding model");
        this.model = model;
    }

    public void addConfigureView(Configure view){
        System.out.println("[Controller] Adding Configure view");
        this.configureView = view;
    }

    public void addLookupPanelViews(LookupPanel view){
        System.out.println("[Controller] Adding LookupPanel view");
        lookupPanelViews.add(view);
    }
}

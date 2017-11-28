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

    private Configure configureView;                    // Complains that view is not used by the MVC model uses it

    private ArrayList<LookupPanel> lookupPanelViews;    // Complains that view is not used by the MVC model uses it

    public Controller() {
        lookupPanelViews = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("[Controller] Acting on Model");
        System.out.println("[Controller] " + e.getActionCommand() + " button clicked");
        System.out.println("[Controller] " + e.paramString() + " " + new java.util.Date(e.getWhen()));
        if (e.getActionCommand().equals("+")) {
            model.addConfigurationRow();
        }
        else if (e.getActionCommand().equals("-")) {
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

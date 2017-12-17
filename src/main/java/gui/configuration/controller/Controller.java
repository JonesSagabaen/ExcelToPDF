package gui.configuration.controller;

import configuration.ConfigurationFile;
import configuration.ConfigurationLookup;
import configuration.ExcelPDFConfiguration;
import gui.configuration.model.Model;
import gui.configuration.view.ConfigurationRow;
import gui.configuration.view.Configure;
import gui.configuration.view.LookupPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class Controller implements ActionListener {

    private Model model;

    /**
     * Reference to the main Configure view.
     * IntelliJ complains it's not used locally but the inherited methods of the MVC use it.
     */
    private Configure configureView;

    /**
     * Reference to the ConfigurationRow panel views.
     * IntelliJ complains it's not used locally but the inherited methods of the MVC use it.
     */
    private ArrayList<ConfigurationRow> configurationRowViews;

    /**
     * Reference to the Lookup panel views.
     * IntelliJ complains it's not used locally but the inherited methods of the MVC use it.
     */
    private ArrayList<LookupPanel> lookupPanelViews;

    public Controller() {
        configurationRowViews = new ArrayList<>();
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
            JPanel buttonParent = (JPanel) ((JButton) e.getSource()).getParent().getParent().getParent().getParent();
            System.out.println("JButton parent: " + buttonParent);
            model.deleteConfigurationRow(buttonParent);
        }
        else if (e.getActionCommand().equals("Submit")) {
            System.out.println("Creating configuration file");

            // TODO: Temporarily write to a test file
//            String configurationFileCreateFilepath = new File("").getAbsolutePath() + "/src/test/resources" + ConfigurationFile.getFilenameStandardFormat();
            String configurationFileCreateFilepath = new File("").getAbsolutePath() + "/src/test/resources/configCreatorTest.conf";

            ArrayList<ExcelPDFConfiguration> configurationsSet = new ArrayList<>();

            // TODO: This logic no longer works because LookupPanel no longer directly observed by controller
            // Try to have ConfigurationRow pass it's configPanel and then handle reading LookupPanel contents.
            for (LookupPanel lookupPanelView : lookupPanelViews) {
                System.out.println("DEBUG: Number of LookupPanels are " + lookupPanelViews.size());
                // Go through each configuration panel, convert to ExcelPDFConfiguration, add to configurationsSet list
                String[] buildLookupConfig = {lookupPanelView.getFontSizeFieldContent(), lookupPanelView.getColumnNameFieldContent(), lookupPanelView.getXCoordinateFieldContent(), lookupPanelView.getYCoordinateFieldContent()};
                ConfigurationLookup configToAdd = new ConfigurationLookup(buildLookupConfig);
                configurationsSet.add(configToAdd);
            }
            ConfigurationFile.createConfigurationFile(configurationFileCreateFilepath, configurationsSet.toArray(new ExcelPDFConfiguration[configurationsSet.size()]));
            System.out.println("Configuration file successfully created");
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

    public void addConfigurationRowViews(ConfigurationRow view){
        System.out.println("[Controller] Adding ConfigurationRow view");
        configurationRowViews.add(view);
    }

    public void addLookupPanelViews(LookupPanel view){
        System.out.println("[Controller] Adding LookupPanel view");
        lookupPanelViews.add(view);
    }
}

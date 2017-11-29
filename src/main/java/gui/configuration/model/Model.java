package gui.configuration.model;

import gui.configuration.controller.Controller;
import gui.configuration.view.LookupPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class Model extends Observable {

    private Controller controller;

    /**
     * List of configuration rows to be displayed.
     */
    private ArrayList<LookupPanel> configurationRows;

    public Model() {
        configurationRows = new ArrayList<>();
    }

    public void addConfigurationRow() {
        LookupPanel newLookupPanel = new LookupPanel();
        newLookupPanel.addController(controller);
        controller.addLookupPanelViews(newLookupPanel);
        configurationRows.add(newLookupPanel);

        // Needed for Model to communicate to Views' update() method
        setChanged();
        notifyObservers(configurationRows);
    }

    public void deleteConfigurationRow(JPanel lookupPanel) {
        Iterator<LookupPanel> iter = configurationRows.iterator();
        while (iter.hasNext()) {
            JPanel confRow = iter.next().getMainView();
            if (confRow.equals(lookupPanel)) {
                iter.remove();
                break;
            }
        }

        // Needed for Model to communicate to Views' update() method
        setChanged();
        notifyObservers(configurationRows);
    }

    public void addController(Controller controller){
        System.out.println("[Model] Adding controller");
        this.controller = controller;
    }
}

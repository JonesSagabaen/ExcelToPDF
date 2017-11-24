package gui.configuration.model;

import gui.configuration.view.LookupPanel;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    /**
     * List of configuration rows to be displayed.
     */
    private ArrayList<LookupPanel> configurationRows;

    public Model() {
        configurationRows = new ArrayList<>();
    }

    public void addConfigurationRow() {
        // TODO: The objects in this LookupPanels need to communicate to the model
        configurationRows.add(new LookupPanel());

        // Needed for Model to communicate to Views
        setChanged();
        notifyObservers(configurationRows);     // Invokes View's update() method
    }
}

package gui.configuration.model;

import gui.configuration.controller.Controller;
import gui.configuration.view.ConfigurationRow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

public class Model extends Observable {

    private Controller controller;

    /**
     * List of configuration rows to be displayed.
     */
    private ArrayList<ConfigurationRow> configurationRows;

    public Model() {
        configurationRows = new ArrayList<>();
    }

    /**
     * Update model by adding a configuration row of type Lookup.
     * After the change has been made to this model, the observers are notified by invoking the view's update() method.
     */
    public void addConfigurationRow() {
        ConfigurationRow newConfRow = new ConfigurationRow();
        newConfRow.addController(controller);
        configurationRows.add(newConfRow);

        // Notify the view observers
        setChanged();
        notifyObservers(configurationRows);
    }

    /**
     * Update model by deleting a configuration row of type Lookup.
     * After the change has been made to this model, the observers are notified by invoking the view's update() method.
     */
    public void deleteConfigurationRow(JPanel lookupPanel) {
        Iterator<ConfigurationRow> iter = configurationRows.iterator();
        while (iter.hasNext()) {
            JPanel confRow = iter.next().getMainView();
            if (confRow.equals(lookupPanel)) {
                iter.remove();
                break;
            }
        }

        // Notify the view observers
        setChanged();
        notifyObservers(configurationRows);
    }

    /**
     * Get the list of configuration rows that is managed and tracked in the model.
     * @return  The list of configuration rows displayed in this configuration UI.
     */
    public ConfigurationRow[] getConfigurationRows() {
        return configurationRows.toArray(new ConfigurationRow[configurationRows.size()]);
    }

    public void addController(Controller controller){
        System.out.println("[Model] Adding controller");
        this.controller = controller;
    }
}

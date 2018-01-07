package gui.configuration;

import gui.configuration.controller.Controller;
import gui.configuration.model.Model;
import gui.configuration.view.Configure;

public class ConfigurationLauncher {

    /**
     * Bind all MVC components needed for an instance of the Configuration dialog window
     * and then proceed to display the window to the user.
     */
    public static void launch() {
        // Initialize model and view
        Model model = new Model();
        Configure configureView = new Configure();
        Controller controller = new Controller();

        // Setup model
        model.addController(controller);
        model.addObserver(configureView);
        // Setup controller
        controller.addModel(model);
        controller.addConfigureView(configureView);
        // Setup Configure view
        configureView.addController(controller);

        // TODO: May not be needed after migrating Configure.java to ConfigureDialog.java
        // Display pop-up window
        configureView.presentDialogWindow();
    }
}

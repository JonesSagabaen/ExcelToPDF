package gui.configuration;

import gui.configuration.controller.Controller;
import gui.configuration.model.Model;
import gui.configuration.view.Configure;

public class ConfigurationMain {

    public static void main(String[] args) {
        // Instantiate MVC and bind together

        // Initialize model and view
        Model model = new Model();
        Configure configureView = new Configure();
        Controller controller = new Controller();

        // Bind view to model
        model.addObserver(configureView);

        // Bind model and view to controller
        controller.addModel(model);
        controller.addConfigureView(configureView);

        // Bind controller to view
        configureView.addController(controller);
    }

    // TODO: Add logic for binding a newly created LookupPanel view to the model
}

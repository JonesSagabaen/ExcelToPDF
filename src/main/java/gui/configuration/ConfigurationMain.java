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

        // Setup model
        model.addController(controller);
        model.addObserver(configureView);

        // Setup controller
        controller.addModel(model);
        controller.addConfigureView(configureView);

        // Setup Configure view
        configureView.addController(controller);
    }
}

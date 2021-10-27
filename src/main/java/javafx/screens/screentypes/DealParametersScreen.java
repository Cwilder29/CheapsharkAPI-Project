package javafx.screens.screentypes;

import javafx.fxml.FXMLLoader;
import javafx.screens.SelectedController;
import javafx.screens.deal.DealParametersController;

public class DealParametersScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        String screenFile = "/deal_parameters.fxml";
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(screenFile));

        controller = new DealParametersController();

        loader.setController(controller);

        return loader;
    }
}

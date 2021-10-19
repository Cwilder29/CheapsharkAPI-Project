package screens.screentypes;

import javafx.fxml.FXMLLoader;
import screens.SelectedController;
import screens.deal.DealParametersController;

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

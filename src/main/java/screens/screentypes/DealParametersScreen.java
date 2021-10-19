package screens.screentypes;

import javafx.fxml.FXMLLoader;
import screens.SelectedController;
import screens.list.DealParametersController;

public class DealParametersScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(String fileName, Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fileName));

        controller = new DealParametersController();

        loader.setController(controller);

        return loader;
    }
}

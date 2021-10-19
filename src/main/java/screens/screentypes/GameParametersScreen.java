package screens.screentypes;

import javafx.fxml.FXMLLoader;
import screens.SelectedController;
import screens.search.GameParametersController;

public class GameParametersScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(String fileName, Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fileName));

        controller = new GameParametersController();

        loader.setController(controller);

        return loader;
    }
}

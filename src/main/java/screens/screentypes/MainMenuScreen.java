package screens.screentypes;

import javafx.fxml.FXMLLoader;
import screens.MenuController;
import screens.SelectedController;

public class MainMenuScreen implements Screen {

    @Override
    public FXMLLoader getScreenController(String fileName, Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fileName));

        controller = new MenuController();

        loader.setController(controller);

        return loader;
    }
}

package javafx.screens.screentypes;

import javafx.fxml.FXMLLoader;
import javafx.screens.SelectedController;
import javafx.screens.game.GameParametersController;

public class GameParametersScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/game_parameters.fxml"));

        controller = new GameParametersController();

        loader.setController(controller);

        return loader;
    }
}

package javafx.screens.screentypes;

import javafx.fxml.FXMLLoader;
import javafx.model.Game;
import javafx.screens.SelectedController;
import javafx.screens.game.GameViewController;

public class GameViewScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view_game.fxml"));

        if(!(args[0] instanceof Game)) {
            throw new IllegalArgumentException("Invalid model. Game object!" + args[0].toString());
        }
        else if(!(args[1] instanceof String)) {
            throw new IllegalArgumentException("Invalid String." + args[1].toString());
        }
        controller = new GameViewController((Game) args[0], (String) args[1]);

        loader.setController(controller);

        return loader;
    }
}

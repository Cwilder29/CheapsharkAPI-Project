package screens.screentypes;

import javafx.fxml.FXMLLoader;
import screens.SelectedController;
import screens.game.GameListController;

public class GameListScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/list_games.fxml"));

        if(!(args[0] instanceof String)) {
            throw new IllegalArgumentException("Invalid String!" + args[0].toString());
        }
        controller = new GameListController((String) args[0]);

        loader.setController(controller);

        return loader;
    }
}

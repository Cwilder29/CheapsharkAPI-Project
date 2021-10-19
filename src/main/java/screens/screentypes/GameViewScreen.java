package screens.screentypes;

import javafx.fxml.FXMLLoader;
import model.Game;
import screens.SelectedController;
import screens.search.GameViewController;

public class GameViewScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(String fileName, Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fileName));

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

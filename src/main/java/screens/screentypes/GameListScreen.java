package screens.screentypes;

import javafx.fxml.FXMLLoader;
import model.DealParameters;
import screens.SelectedController;
import screens.search.GameListController;

public class GameListScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(String fileName, Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fileName));

        if(!(args[0] instanceof String)) {
            throw new IllegalArgumentException("Invalid String!" + args[0].toString());
        }
        controller = new GameListController((String) args[0]);

        loader.setController(controller);

        return loader;
    }
}

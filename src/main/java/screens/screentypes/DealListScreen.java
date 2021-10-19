package screens.screentypes;

import javafx.fxml.FXMLLoader;
import model.DealParameters;
import screens.SelectedController;
import screens.list.DealListController;

public class DealListScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(String fileName, Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fileName));

        if(!(args[0] instanceof DealParameters)) {
            throw new IllegalArgumentException("Invalid model. Store object! " + args[0].toString());
        }
        controller = new DealListController((DealParameters) args[0]);

        loader.setController(controller);

        return loader;
    }
}

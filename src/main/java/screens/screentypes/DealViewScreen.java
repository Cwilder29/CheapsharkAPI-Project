package screens.screentypes;

import javafx.fxml.FXMLLoader;
import model.Deal;
import model.DealParameters;
import screens.SelectedController;
import screens.list.DealViewController;

public class DealViewScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(String fileName, Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fileName));

        if(!(args[0] instanceof Deal)) {
            throw new IllegalArgumentException("Invalid model. Deal object!" + args[0].toString());
        }
        else if(!(args[1] instanceof DealParameters)) {
            throw new IllegalArgumentException("Invalid model. DealParameter object!" + args[1].toString());
        }
        controller = new DealViewController((Deal) args[0], (DealParameters) args[1]);

        loader.setController(controller);

        return loader;
    }
}

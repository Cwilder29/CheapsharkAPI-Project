package screens.screentypes;

import javafx.fxml.FXMLLoader;
import model.Deal;
import model.DealParameters;
import screens.SelectedController;
import screens.deal.DealViewController;

public class DealViewScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view_deal.fxml"));

        if(!(args[0] instanceof Deal)) {
            throw new IllegalArgumentException("Invalid model. Deal object!" + args[0].toString());
        }
        else if(!(args[1] instanceof DealParameters)) {
            throw new IllegalArgumentException("Invalid model. DealParameters object!" + args[1].toString());
        }
        controller = new DealViewController((Deal) args[0], (DealParameters) args[1]);

        loader.setController(controller);

        return loader;
    }
}

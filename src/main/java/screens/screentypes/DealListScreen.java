package screens.screentypes;

import javafx.fxml.FXMLLoader;
import model.DealParameters;
import screens.SelectedController;
import screens.deal.DealListController;

public class DealListScreen implements Screen {

    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/list_deals.fxml"));

        if(!(args[0] instanceof DealParameters)) {
            throw new IllegalArgumentException("Invalid model. Store object! " + args[0].toString());
        }
        controller = new DealListController((DealParameters) args[0]);

        loader.setController(controller);

        return loader;
    }
}

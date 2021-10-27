package javafx.screens.screentypes;

import javafx.fxml.FXMLLoader;
import javafx.model.DealParameters;
import javafx.screens.SelectedController;
import javafx.screens.deal.DealListController;

public class DealListScreen implements Screen {

    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/list_deals_table.fxml"));

        if(!(args[0] instanceof DealParameters)) {
            throw new IllegalArgumentException("Invalid model. DealParameters object! " + args[0].toString());
        }
        controller = new DealListController((DealParameters) args[0]);

        loader.setController(controller);

        return loader;
    }
}
package screens.screentypes;

import javafx.fxml.FXMLLoader;
import model.DealParameters;
import screens.SavedDealsController;
import screens.SelectedController;
import screens.deal.DealListController;

public class SavedDealsScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/list_deals.fxml"));

        controller = new SavedDealsController();

        loader.setController(controller);

        return loader;
    }
}

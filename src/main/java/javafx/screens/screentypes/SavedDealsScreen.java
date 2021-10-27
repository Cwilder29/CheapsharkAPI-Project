package javafx.screens.screentypes;

import javafx.fxml.FXMLLoader;
import javafx.screens.save.SavedDealsController;
import javafx.screens.SelectedController;

public class SavedDealsScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/list_saved_deals.fxml"));

        controller = new SavedDealsController();

        loader.setController(controller);

        return loader;
    }
}

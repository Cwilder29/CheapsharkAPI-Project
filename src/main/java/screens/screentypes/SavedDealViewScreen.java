package screens.screentypes;

import javafx.fxml.FXMLLoader;
import model.Deal;
import screens.SavedDealViewController;
import screens.SelectedController;

public class SavedDealViewScreen implements Screen {
    @Override
    public FXMLLoader getScreenController(Object... args) {
        SelectedController controller = null;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view_saved_deal.fxml"));

        if(!(args[0] instanceof Deal)) {
            throw new IllegalArgumentException("Invalid model. Deal object!" + args[0].toString());
        }
        controller = new SavedDealViewController((Deal) args[0]);

        loader.setController(controller);

        return loader;
    }
}

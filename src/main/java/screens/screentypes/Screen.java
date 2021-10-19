package screens.screentypes;

import javafx.fxml.FXMLLoader;

public interface Screen {
    FXMLLoader getScreenController(Object... args);
}

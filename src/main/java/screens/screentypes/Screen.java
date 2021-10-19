package screens.screentypes;

import javafx.fxml.FXMLLoader;

public interface Screen {
    public FXMLLoader getScreenController(String fileName, Object... args);
}

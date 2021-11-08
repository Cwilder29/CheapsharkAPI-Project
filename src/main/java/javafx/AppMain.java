package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.model.Store;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.screens.MainController;

public class AppMain extends Application {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        String strResponse = Store.updateStoreList();
        if (strResponse != null)
            super.init();
        else
            LOGGER.warn("Could not update stores in database!");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/main_view.fxml"));
        loader.setController(MainController.getInstance());
        Parent rootNode = loader.load();
        stage.setScene(new Scene(rootNode));

        stage.setTitle("Cheapshark-Project");
        stage.show();
        LOGGER.info("Start up complete.");
    }


}

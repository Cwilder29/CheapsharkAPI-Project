import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import screens.MainController;

public class AppMain extends Application {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // TODO Retrieve Stores?
        super.init();
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

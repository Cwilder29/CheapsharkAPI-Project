package screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.DealParameters;
import model.GameDeal;
import model.Store;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private Label fxTitle;

    @FXML
    private TextField fxSalePrice;

    @FXML
    private TextField fxNormalPrice;

    @FXML
    private TextField fxSaving;

    @FXML
    private TextField fxMetacritic;

    @FXML
    private TextField fxSteam;

    private GameDeal gameDeal;
    private DealParameters dealParameters;

    public GameViewController(GameDeal gameDeal, DealParameters dealParameters) {
        this.gameDeal = gameDeal;
        this.dealParameters = dealParameters;
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.GAMELIST, dealParameters);
    }

    @FXML
    void viewDeal(ActionEvent event) {
        String url = "https://www.cheapshark.com/redirect?dealID=" + gameDeal.getDealId();
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException e) {
            LOGGER.error("Could not loud website: " + e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxTitle.setText(gameDeal.getTitle());
        fxSalePrice.setText(String.valueOf(gameDeal.getSalePrice()));
        fxNormalPrice.setText(String.valueOf(gameDeal.getNormalPrice()));
        fxSaving.setText(String.valueOf(gameDeal.getSavings()));
        fxMetacritic.setText(String.valueOf(gameDeal.getMetacriticRating()));
        fxSteam.setText(String.valueOf(gameDeal.getSteamRating()));
    }
}

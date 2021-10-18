package screens.list;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.DealParameters;
import model.Deal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import screens.MainController;
import screens.MyController;
import screens.ScreenType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DealViewController implements Initializable, MyController {
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

    private Deal deal;
    private DealParameters dealParameters;

    public DealViewController(Deal deal, DealParameters dealParameters) {
        this.deal = deal;
        this.dealParameters = dealParameters;
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(ScreenType.DEAL_LIST, dealParameters);
    }

    @FXML
    void viewDeal(ActionEvent event) {
        String url = "https://www.cheapshark.com/redirect?dealID=" + deal.getDealId();
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException e) {
            LOGGER.error("Could not loud website: " + e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxTitle.setText(deal.getTitle());
        fxSalePrice.setText(String.valueOf(deal.getSalePrice()));
        fxNormalPrice.setText(String.valueOf(deal.getNormalPrice()));
        fxSaving.setText(String.valueOf(deal.getSavings()));
        fxMetacritic.setText(String.valueOf(deal.getMetacriticRating()));
        fxSteam.setText(String.valueOf(deal.getSteamRating()));
    }
}

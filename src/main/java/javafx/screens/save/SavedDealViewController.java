package javafx.screens.save;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.model.Deal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.screens.MainController;
import javafx.screens.SelectedController;
import javafx.screens.screentypes.SavedDealsScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SavedDealViewController implements Initializable, SelectedController {
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

    public SavedDealViewController(Deal deal) {
        this.deal = deal;
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(new SavedDealsScreen().getScreenController());
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

    private String checkSavings(double savings) {
        if (savings < 1) {
            return "Not on sale";
        }
        else {
            return (int) savings + "%";
        }
    }

    private String checkRating(int rating) {
        if (rating == 0)
            return "No rating available";
        else
            return String.valueOf(rating);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxTitle.setText(deal.getTitle());
        fxSalePrice.setText(String.valueOf(deal.getSalePrice()));
        fxNormalPrice.setText(String.valueOf(deal.getNormalPrice()));
        fxSaving.setText(checkSavings(deal.getSavings()));
        fxMetacritic.setText(checkRating(deal.getMetacriticRating()));
        fxSteam.setText(checkRating(deal.getSteamRating()));
    }
}

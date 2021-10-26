package screens.deal;

import httpclient.PostRequest;
import javafx.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.DealParameters;
import model.Deal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import screens.MainController;
import screens.SelectedController;
import screens.screentypes.DealListScreen;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class DealViewController implements Initializable, SelectedController {
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
    @FXML
    private Label fxSave;

    private Deal deal;
    private DealParameters dealParameters;

    public DealViewController(Deal deal, DealParameters dealParameters) {
        this.deal = deal;
        this.dealParameters = dealParameters;
    }

    @FXML
    void exit(ActionEvent event) {
        MainController.getInstance().switchView(new DealListScreen().getScreenController(dealParameters));
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

    @FXML
    void saveDeal(ActionEvent event) {
        InetAddress inetAddress = null;
        String strResponse;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        LOGGER.info("IP Address:- " + inetAddress.getHostAddress());
        String ip = inetAddress.getHostAddress();
        String url = "http://" + ip + ":8080/deals";

        JSONObject dealData = new JSONObject();
        dealData.put("title", deal.getTitle());
        dealData.put("dealID", deal.getDealId());
        dealData.put("salePrice", deal.getSalePrice());
        dealData.put("normalPrice", deal.getNormalPrice());
        dealData.put("gameID", deal.getGameId());
        dealData.put("storeID", deal.getStoreId());
        dealData.put("savings", deal.getSavings());
        dealData.put("metacriticScore", deal.getMetacriticRating());
        dealData.put("steamRatingPercent", deal.getSteamRating());
        String dealDataString = dealData.toString();

        strResponse = new PostRequest().executeRequest(url, dealDataString);

        if (strResponse != null) {
            fxSave.setText("Deal saved!");
            fxSave.setTextFill(Color.web("#38BC00"));
        }
        else {
            fxSave.setText("Did not save!");
            fxSave.setTextFill(Color.web("#FF0000"));
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

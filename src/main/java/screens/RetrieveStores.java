package screens;

import model.Game;
import model.Store;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RetrieveStores {
    private static final Logger LOGGER = LogManager.getLogger();

    public static ArrayList<Store> retrieveStores() {
        int statusCode;
        ArrayList<Store> storeList = new ArrayList<>();
        String url = "https://www.cheapshark.com/api/1.0/stores";

        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(getRequest);

            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200)
                LOGGER.info("List of stores retrieved successfully: " + statusCode);
            else {
                LOGGER.error("Could not retrieve store list from CheapShark: " + statusCode);
                response.close();
                httpclient.close();
                return null;
            }
            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            //LOGGER.info(strResponse);

            JSONArray objResponse = new JSONArray(strResponse);

            for (Object store : objResponse) {
                storeList.add(Store.fromJSONObject((JSONObject) store));
            }

            //JSONObject objResponse = new JSONObject(strResponse);
            //JSONObject infoResponse = new JSONObject(objResponse.getJSONObject("info").toString());
            //JSONArray dealResponse = new JSONArray(objResponse.getJSONArray("deals"));

            //LOGGER.info(objResponse.toString());
            //LOGGER.info(objResponse.get("info"));
            //LOGGER.info(objResponse.getJSONObject("info").toString());
            //LOGGER.info(infoResponse.getString("title"));

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return storeList;
    }


}

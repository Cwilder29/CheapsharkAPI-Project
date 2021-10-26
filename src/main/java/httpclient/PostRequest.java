package httpclient;

import javafx.Alerts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class PostRequest implements Request {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String executeRequest(String url, String body) {
        int statusCode;
        StringEntity reqEntity;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(url);
        CloseableHttpResponse response;
        String strResponse;

        try {
            LOGGER.info("Connecting...");
            reqEntity = new StringEntity(body);
            reqEntity.setContentType("application/json");
            postRequest.setEntity(reqEntity);
            response = httpclient.execute(postRequest);
            statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                LOGGER.info("Connected to " + url);
                response.close();
                httpclient.close();
                return "";
            }
            else {
                HttpEntity entity = response.getEntity();
                strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                EntityUtils.consume(entity);

                LOGGER.error("Failed to connected to: " + url + "(" + strResponse + " " + statusCode + ")");
                Alerts.infoAlert("Error!", "Could not complete request:" + strResponse);

                response.close();
                httpclient.close();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

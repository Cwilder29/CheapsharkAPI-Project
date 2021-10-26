package httpclient;

import javafx.Alerts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DeleteRequest implements Request {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public String executeRequest(String url, String body) {
        int statusCode;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete deleteRequest = new HttpDelete(url);
        CloseableHttpResponse response;
        String strResponse;

        try {
            LOGGER.info("Connecting...");
            response = httpclient.execute(deleteRequest);
            statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                LOGGER.info("Connected to " + url);
                HttpEntity entity = response.getEntity();
                strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                EntityUtils.consume(entity);

                response.close();
                httpclient.close();

                return strResponse;
            }
            else {
                LOGGER.error("Failed to connect to " + url + " (status code: " + statusCode + ")");
                Alerts.infoAlert("Error!", "Could not complete request!");
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
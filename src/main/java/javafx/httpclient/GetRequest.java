package javafx.httpclient;

import javafx.utils.Alerts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GetRequest implements Request {
    private static final Logger LOGGER = LogManager.getLogger();

    private int totalPageCount;

    @Override
    public String executeRequest(String url, String body) {
        int statusCode;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet getRequest = new HttpGet(url);
        CloseableHttpResponse response;
        String strResponse;
        Header[] headerArray;

        try {
            LOGGER.info("Connecting...");
            response = httpclient.execute(getRequest);
            statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                LOGGER.info("Connected to " + url);
                HttpEntity entity = response.getEntity();
                strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                EntityUtils.consume(entity);

                headerArray = response.getAllHeaders();

                for (Header header : headerArray) {
                    if (header.getName().equals("X-Total-Page-Count")) {
                        LOGGER.info(header.getName());
                        LOGGER.info(header.getValue());
                        this.setTotalPageCount(Integer.parseInt(header.getValue()));
                    }
                }

                response.close();
                httpclient.close();

                return strResponse;
            }
            else {
                HttpEntity entity = response.getEntity();
                strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                EntityUtils.consume(entity);

                LOGGER.error("Failed to connected to: " + url + " (" + strResponse + ": " + statusCode + ")");
                Alerts.infoAlert("Error!", "Could not complete request: " + strResponse);

                response.close();
                httpclient.close();
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }
}

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AppMain {

    public static void main(String[] args) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet getRequest = new HttpGet("https://www.cheapshark.com/api/1.0/deals?storeID=1&upperPrice=15");
            CloseableHttpResponse response = httpclient.execute(getRequest);

            System.out.println(response.getStatusLine());

            HttpEntity entity = response.getEntity();
            // use org.apache.http.util.EntityUtils to read json as string
            String strResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            EntityUtils.consume(entity);

            System.out.println(strResponse);

            JSONArray objResponse = new JSONArray(strResponse);

            response.close();
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

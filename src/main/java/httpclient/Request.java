package httpclient;

import org.json.JSONObject;

public interface Request {
    String executeRequest(String url, String body);
}

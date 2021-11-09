package javafx.model;

import javafx.httpclient.GetRequest;
import javafx.httpclient.PostRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Store {
    private int id;
    private String storeName;
    private int storeActive;

    public Store(int id, String storeName, int storeActive) {
        this.id = id;
        this.storeName = storeName;
        this.storeActive = storeActive;
    }

    public Store() {
    }

    public static String updateStoreList() {
        ArrayList<JSONObject> storeList = getStoreList();
        String url;
        InetAddress inetAddress;
        JSONArray jsonStoreArray = new JSONArray();
        PostRequest postRequest = new PostRequest();

        if (storeList == null)
            return null;

        try {
            inetAddress = InetAddress.getLocalHost();
            url = "http://" + inetAddress.getHostAddress() + ":8080/stores";

            for (JSONObject store : storeList) {
                jsonStoreArray.put(store);
            }
            return postRequest.executeRequest(url, jsonStoreArray.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<JSONObject> getStoreList() {
        ArrayList<JSONObject> storeList = new ArrayList<>();
        String url = "https://www.cheapshark.com/api/1.0/stores";
        GetRequest getRequest = new GetRequest();
        String strResponse;
        JSONArray objResponse;
        Store store;
        JSONObject storeJSON;

        strResponse = getRequest.executeRequest(url, "");
        if (strResponse != null) {
            objResponse = new JSONArray(strResponse);

            for (Object json : objResponse) {
                store = Store.fromJSONObject((JSONObject) json);

                storeJSON = new JSONObject();
                storeJSON.put("id", store.getId());
                storeJSON.put("storeName", store.getStoreName());
                storeJSON.put("storeActive", store.getStoreActive());
                storeList.add(storeJSON);
            }

            return storeList;
        }
        return null;
    }

    public static void retrieveStores(ArrayList<Store> stores) {
        InetAddress inetAddress = null;
        String strResponse;
        JSONArray objResponse;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            stores = null;
            return;
        }

        String url = "http://" + inetAddress.getHostAddress() + ":8080/stores";

        strResponse = new GetRequest().executeRequest(url, "");
        if (strResponse != null) {
            objResponse = new JSONArray(strResponse);

            for (Object store : objResponse) {
                stores.add(Store.fromJSONObjectDatabase((JSONObject) store));
            }
        }
        else
            stores = null;
    }

    public Store retrieveStore(int storeId) {
        InetAddress inetAddress = null;
        String strResponse;
        JSONObject objResponse;

        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }

        String url = "http://" + inetAddress.getHostAddress() + ":8080/stores/" + storeId;
        strResponse = new GetRequest().executeRequest(url, "");
        if (strResponse != null) {
            objResponse = new JSONObject(strResponse);
            return fromJSONObjectDatabase(objResponse);
        }
        else
            return null;
    }

    public static Store fromJSONObject(JSONObject json) {
        try {
            Store store = new Store(json.getInt("storeID"), json.getString("storeName"), json.getInt("isActive"));
            return store;
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Unable to parse store from provided json:\n " + json.toString());
        }
    }

    public static Store fromJSONObjectDatabase(JSONObject json) {
        try {
            Store store = new Store(json.getInt("id"), json.getString("storeName"), json.getInt("storeActive"));
            return store;
        } catch (Exception e) {
            System.out.println(e);
            throw new IllegalArgumentException("Unable to parse store from provided json:\n " + json.toString());
        }
    }

    @Override
    public String toString() {
        return storeName;
    }

    //accessors
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStoreActive() {
        return storeActive;
    }

    public void setStoreActive(int storeActive) {
        this.storeActive = storeActive;
    }
}

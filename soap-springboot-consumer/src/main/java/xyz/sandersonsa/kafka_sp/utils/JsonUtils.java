package xyz.sandersonsa.kafka_sp.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static boolean isValidJson(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException e) {
            try {
                new JSONArray(json);
            } catch (JSONException ne) {
                return false;
            }
        }
        return true;
    }

    public static String getByName(String json, String name){
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject.getString(name);
    }
}


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

class GlobalUtils {

    private static long getCurrentTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        return cal.getTime().getTime();
    }

    static JSONObject getJSON_V1(List<WifiResult> wifiList, String location, String group, String user) throws JSONException {
        long timestamp = getCurrentTimestamp();
        JSONObject json = new JSONObject();
        JSONArray Wifi_fingerprint = new JSONArray();

        for (WifiResult wifi : wifiList) {
            JSONObject temp = new JSONObject();
            temp.put("mac", wifi.getBSSID());
            temp.put("rssi", wifi.getRssi());
            Wifi_fingerprint.put(temp);
        }
        json.put("group", group);
        json.put("location", location);
        json.put("username", user);
        json.put("time", timestamp);
        json.put("wifi-fingerprint", Wifi_fingerprint);

        return json;
    }
}

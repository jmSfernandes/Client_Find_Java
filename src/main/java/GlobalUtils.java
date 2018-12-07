import entities.Wifi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

class GlobalUtils {
    private static final int MAX_DB = -50;
    private static final int Min_DB = -100;
    private static final String SCAN_CMD = "netsh wlan show networks mode=Bssid"; //interface="Wi-Fi"

    static int getRssiFromQuality(int quality) {
        return (quality / 2) + Min_DB;
    }


    static List<Wifi> scanAPSWindows() throws IOException {
        Runtime rt = Runtime.getRuntime();
        List<Wifi> wifiList = new ArrayList<Wifi>();
        StringBuilder scan_response = new StringBuilder();

        Process pr = rt.exec(SCAN_CMD);
        BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        Wifi wf = new Wifi();
        while ((line = br.readLine()) != null) {
            scan_response.append(line + "\n");
            if (line.toLowerCase().contains("bssid")) {
                wf.setBssid(line.split(" :")[1]);
            } else if (line.toLowerCase().contains("signal")) {
                String str_rssi = line.split(" :")[1].split("%")[0].trim();
                int rssi = (int) GlobalUtils.getRssiFromQuality(Integer.parseInt(str_rssi));
                wf.setRssi(rssi);
                wifiList.add(wf);
                wf = new Wifi();
            }
        }
        return wifiList;
    }
    static long getCurrentTimestamp(){
        Calendar cal=Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        return cal.getTime().getTime();
    }

    static JSONObject getJSON_V1(List<Wifi> wifiList,String location, String group, String user) throws JSONException {
        long timestamp= getCurrentTimestamp();
        JSONObject json= new JSONObject();
        JSONArray Wifi_fingerprint= new JSONArray();

        for(Wifi wifi:wifiList){
            Wifi_fingerprint.put(new JSONObject(wifi.toString()));
        }
        json.put("group",group);
        json.put("location",location);
        json.put("username",user);
        json.put("time",timestamp);
        json.put("wifi-fingerprint",Wifi_fingerprint);

        return json;
    }
}

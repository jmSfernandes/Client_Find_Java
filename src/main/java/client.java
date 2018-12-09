
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class client {
    private static String SERVER = " https://ml.internalpositioning.com/";
    private static final String LEARNING = "learn";
    private static final String TRACKING = "track";
    private static boolean learning = false;
    private static String GROUP = "test";
    private static String USER = "user";
    private static String LOCATION = "";
    private static String INTERFACE = "Wlan0";

    public static void main(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--s") && args.length > (i + 1))
                    SERVER = args[i + 1];
                else if (args[i].equals("--e"))
                    learning = true;
                else if (args[i].equals("--g") && args.length > (i + 1))
                    GROUP = args[i + 1];
                else if (args[i].equals("--l") && args.length >= (i + 1))
                    LOCATION = args[i + 1];
                else if (args[i].equals("--i") && args.length >= (i + 1))
                    INTERFACE = args[i + 1];
                else if (args[i].equals("--u") && args.length >= (i + 1))
                    USER = args[i + 1];

            }

            WifiScanner wifiScanner=new WifiScanner();
            List<WifiResult> wifiList  = wifiScanner.run();

            System.out.println("FOUND " + wifiList.size() + " APS");

            if(learning){
                JSONObject jsonObject= GlobalUtils.getJSON_V1(wifiList,LOCATION,GROUP,USER);
                String response= CommunicationUtils.callAPI(SERVER,LEARNING,jsonObject);
                System.out.println(new JSONObject(response).toString(1));
            }else{
                JSONObject jsonObject= GlobalUtils.getJSON_V1(wifiList,LOCATION,GROUP,USER);
                String response= CommunicationUtils.callAPI(SERVER,TRACKING,jsonObject);
                System.out.println(new JSONObject(response).toString(1));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

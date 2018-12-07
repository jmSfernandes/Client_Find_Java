
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class CommunicationUtils {
    private static final String CONTENT = "application/json";
    private static String METHOD = "POST";

    static String callAPI(String server, String endpoint, JSONObject jsonObject) throws IOException {
        HttpURLConnection urlConnection = null;

        urlConnection = (HttpURLConnection) ((new URL(server + endpoint).openConnection()));
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setRequestProperty("Content-Type", CONTENT);
        urlConnection.setRequestProperty("Accept", CONTENT);
        urlConnection.setRequestMethod(METHOD);
        urlConnection.connect();


        DataOutputStream dataOutputStream1 = new DataOutputStream(urlConnection.getOutputStream());
        BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(dataOutputStream1, "UTF-8"));
        writer1.write(jsonObject.toString());
        writer1.close();
        dataOutputStream1.close();
        int response_code = urlConnection.getResponseCode();
        StringBuilder response=new StringBuilder();
        if (response_code >= 200 && response_code<=300) {
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
        }else if(response_code>300){
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println("RESPONSE CODE: " + response_code);
        System.out.println("-------------------------------------------------------------");
        return response.toString();
    }
}

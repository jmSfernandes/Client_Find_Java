package entities;

public class Wifi {
    private String Bssid;
    private int rssi;

    public Wifi(String bssid, int rssi) {
        Bssid = bssid;
        this.rssi = rssi;
    }

    public Wifi() {

    }

    public String getBssid() {
        return Bssid;
    }

    public void setBssid(String bssid) {
        Bssid = bssid;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return "{" +
                "mac: \"" + Bssid +"\"" +
                ", rssi:" + rssi +
                '}';
    }
}

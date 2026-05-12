import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Spieler {
    public String name;
    public int price;

    public int range;
    public int accuracy;
    public int runningspeed;
    public String Position;

    public Spieler(String name, int price, int range, int accuracy, int runningspeed, String position) {
        this.name = name;
        this.price = price;
        this.range = range;
        this.accuracy = accuracy;
        this.runningspeed = runningspeed;
        Position = position;
        teststats();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public static void setPrice(int price) {
        price = price;
    }


    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getRunningspeed() {
        return runningspeed;
    }

    public void setRunningspeed(int runningspeed) {
        this.runningspeed = runningspeed;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }
    public void teststats() {
        System.out.println("Name: "+name+" price: "+price+"$ range: "+range+" meters accuracy: "+accuracy+"% runningspeed: "+runningspeed+"/10 position: "+Position);
    }
}

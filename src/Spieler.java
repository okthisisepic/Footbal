import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Spieler {
    public String name;
    public int price;
    public enum PrefferredPosition {
        Torwart,Innerverteidiger,Außenverteidiger,DefensiverMittelspieler,ZentralMittelfeldSpieler,RechterFlügelSpieler,LinkerFlügelSpieler,OffensiverMittelFeldSpieler;
    }
    public int range;
    public int accuracy;
    public int runningspeed;

    public Spieler(String name, int price, int range, int accuracy, int runningspeed, PrefferredPosition position) {
        this.name = name;
        this.price = price;
        this.range = range;
        this.accuracy = accuracy;
        this.runningspeed = runningspeed;
        Position = position;
    }




    public PrefferredPosition getPosition() {
        return Position;
    }

    public void setPosition(PrefferredPosition position) {
        Position = position;
    }


    PrefferredPosition Position;

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


}

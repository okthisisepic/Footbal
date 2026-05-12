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

    public void setPrice(int price) {
        this.price = price;
    }
    public static void gen() {
        
    }

    public static void main(String[] args) {
        gen();
    }
}

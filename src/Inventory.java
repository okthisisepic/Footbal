import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static String[] preferredPositions = {"Torwart","Innenverteidiger","Außenverteidiger","DefensiverMittelspieler","ZentralMittelfeldSpieler","RechterFlügelSpieler","LinkerFlügelSpieler","OffensiverMittelFeldSpieler"};
    public static List<Spieler> spielerinventory = new ArrayList<>();
    public static List<Liga> LeaguesOwned = new ArrayList<>();
    public static String[] players = {"Liam Carter","Noah Müller","Ethan Rossi","Lucas Silva","Oliver Schmidt","James Anderson","Mateo Fernández","Daniel Novak","Adrian Kowalski","Santiago Reyes","Felix Wagner","Marco Bianchi","Hugo Laurent","Ryan Thompson","Kai Nakamura","Zlatan Ibramhimovich ","Sand vich","Nintendo Switch","Goal Messi","Penalty Ronaldo","Harry Kane Jr","John Kickball","FC WiFi","Thomas Milch","Speedy Gonzalez FC","Small toe","Ensar Turkyeeee","Alex","Withalm Rapid","Baller","T80 fuken balling"};
    public static void buyrandomcommonplayerpack() {
        String randomname =players[(int)(Math.random() * players.length-1)];
        int price = (int)(Math.random() * 10000) + 7;
        int range = (int)(Math.random() * 20) + 14;
        int accuracy = (int)(Math.random() * 40) + 1;
        int runningspeed = (int)(Math.random() * 3) + 1;
        String Position = preferredPositions[(int)(Math.random() * 7)];
        Spieler p = new Spieler(randomname, price, range, accuracy, runningspeed, Position);
        spielerinventory.add(p);
    }
    public static void buyrandomrareplayerpack() {
        String randomname =players[(int)(Math.random() * players.length-1)];
        int price = (int)(Math.random() * 1000000) + 7;
        int range = (int)(Math.random() * 35) + 20;
        int accuracy = (int)(Math.random() * 70) + 40;
        int runningspeed = (int)(Math.random() * 6) + 3;
        String Position = preferredPositions[(int)(Math.random() * 7)];
        Spieler p = new Spieler(randomname, price, range, accuracy, runningspeed, Position);
        spielerinventory.add(p);
    }
    public static void buyrandomlegendaryplayerpack() {
        String randomname =players[(int)(Math.random() * players.length-1)];
        int price = (int)(Math.random() * 10000000) + 7;
        int range = (int)(Math.random() * 55) + 40;
        int accuracy = (int)(Math.random() * 100) + 60;
        int runningspeed = (int)(Math.random() * 10) + 8;
        String Position = preferredPositions[(int)(Math.random() * 7)];
        Spieler p = new Spieler(randomname, price, range, accuracy, runningspeed, Position);
        spielerinventory.add(p);
    }
}

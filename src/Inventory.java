
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static int mone = 1000000;
    public static String[] preferredPositions = {"Torwart","Innenverteidiger","Außenverteidiger","DefensiverMittelspieler","ZentralMittelfeldSpieler","RechterFlügelSpieler","LinkerFlügelSpieler","OffensiverMittelFeldSpieler"};
    public static List<Spieler> spielerinventory = new ArrayList<>();
    public static League league;
    public static String[] players = {"Liam Carter","Noah Müller","Ethan Rossi","Lucas Silva","Oliver Schmidt","James Anderson","Mateo Fernández","Daniel Novak","Adrian Kowalski","Santiago Reyes","Felix Wagner","Marco Bianchi","Hugo Laurent","Ryan Thompson","Kai Nakamura","Zlatan Ibramhimovich ","Sand vich","Nintendo Switch","Goal Messi","Penalty Ronaldo","Harry Kane Jr","John Kickball","FC WiFi","Thomas Milch","Speedy Gonzalez FC","Small toe","Ensar Turkyeeee","Alex","Withalm Rapid","Baller","T80 fuken balling"};
    public static void buyrandomcommonplayerpack() {
        if (mone >= 5000) {
            mone -= 5000;
            String randomname = players[(int) (Math.random() * players.length - 1)];
            int price = (int) (Math.random() * 10000) + 7;
            int range = (int) (Math.random() * 20) + 14;
            int accuracy = (int) (Math.random() * 40) + 1;
            int runningspeed = (int) (Math.random() * 3) + 1;
            String Position = preferredPositions[(int) (Math.random() * 7)];
            Spieler p = new Spieler();
            spielerinventory.add(p);
        }
    }
    public static void buyrandomrareplayerpack() {
        if (mone >= 500000) {
            mone -= 500000;
            String randomname = players[(int) (Math.random() * players.length - 1)];
            int price = (int) (Math.random() * 1000000) + 7;
            int range = (int) (Math.random() * 35) + 20;
            int accuracy = (int) (Math.random() * 70) + 40;
            int runningspeed = (int) (Math.random() * 6) + 3;
            String Position = preferredPositions[(int) (Math.random() * 7)];
            Spieler p = new Spieler();
            spielerinventory.add(p);
        }
    }
    public static void buyrandomlegendaryplayerpack() {
        if (mone >= 5000000) {
            mone -= 5000000;
            String randomname = players[(int) (Math.random() * players.length - 1)];
            int price = (int) (Math.random() * 10000000) + 7;
            int range = (int) (Math.random() * 55) + 40;
            int accuracy = (int) (Math.random() * 100) + 60;
            int runningspeed = (int) (Math.random() * 10) + 8;
            String Position = preferredPositions[(int) (Math.random() * 7)];
            Spieler p = new Spieler();
            spielerinventory.add(p);
        }
    }
    public static void createleague(String name ,int amount) {
        List<Team> empty= new ArrayList<>();
        league = new League(name,0,amount);
    }
}

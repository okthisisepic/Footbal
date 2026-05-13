import java.util.ArrayList;
import java.util.List;
/*
public class Teamclass {
    public  List<Spieler> spielerinventory = new ArrayList<>();
    public int size;
    public static String[] players = {"Liam Carter","Noah Müller","Ethan Rossi","Lucas Silva","Oliver Schmidt","James Anderson","Mateo Fernández","Daniel Novak","Adrian Kowalski","Santiago Reyes","Felix Wagner","Marco Bianchi","Hugo Laurent","Ryan Thompson","Kai Nakamura","Zlatan Ibramhimovich ","Sand vich","Nintendo Switch","Goal Messi","Penalty Ronaldo","Harry Kane Jr","John Kickball","FC WiFi","Thomas Milch","Speedy Gonzalez FC","Small toe","Ensar Turkyeeee","Alex","Withalm Rapid","Baller","T80 fuken balling"};
    public static String[] preferredPositions = {"Torwart","Innenverteidiger","Außenverteidiger","DefensiverMittelspieler","ZentralMittelfeldSpieler","RechterFlügelSpieler","LinkerFlügelSpieler","OffensiverMittelFeldSpieler"};
    public String name;

    public Teamclass(List<Spieler> spielerinventory, int size, String name) {
        this.spielerinventory = spielerinventory;
        this.size = size;
        this.name = name;
        for (int i = 0; i < size/3; i++) {
            buyrandomcommonplayerpack(spielerinventory);
            buyrandomrareplayerpack(spielerinventory);
            buyrandomlegendaryplayerpack(spielerinventory);
        }
        System.out.println(name);
    }

    public static void buyrandomcommonplayerpack(List<Spieler> spielerinventory) {
            String randomname = players[(int) (Math.random() * players.length - 1)];
            int price = (int) (Math.random() * 10000) + 7;
            int range = (int) (Math.random() * 20) + 14;
            int accuracy = (int) (Math.random() * 40) + 1;
            int runningspeed = (int) (Math.random() * 3) + 1;
            String Position = preferredPositions[(int) (Math.random() * 7)];
            Spieler p = new Spieler(randomname, price, range, accuracy, runningspeed, Position);
            spielerinventory.add(p);

    }
    public static void buyrandomrareplayerpack(List<Spieler> spielerinventory) {


            String randomname = players[(int) (Math.random() * players.length - 1)];
            int price = (int) (Math.random() * 1000000) + 7;
            int range = (int) (Math.random() * 35) + 20;
            int accuracy = (int) (Math.random() * 70) + 40;
            int runningspeed = (int) (Math.random() * 6) + 3;
            String Position = preferredPositions[(int) (Math.random() * 7)];
            Spieler p = new Spieler(randomname, price, range, accuracy, runningspeed, Position);
            spielerinventory.add(p);

    }
    public static void buyrandomlegendaryplayerpack(List<Spieler> spielerinventory) {
            String randomname = players[(int) (Math.random() * players.length - 1)];
            int price = (int) (Math.random() * 10000000) + 7;
            int range = (int) (Math.random() * 55) + 40;
            int accuracy = (int) (Math.random() * 100) + 60;
            int runningspeed = (int) (Math.random() * 10) + 8;
            String Position = preferredPositions[(int) (Math.random() * 7)];
            Spieler p = new Spieler(randomname, price, range, accuracy, runningspeed, Position);
            spielerinventory.add(p);
    }

    public static String[] getPreferredPositions() {
        return preferredPositions;
    }

    public static void setPreferredPositions(String[] preferredPositions) {
        Teamclass.preferredPositions = preferredPositions;
    }

    public static String[] getPlayers() {
        return players;
    }

    public static void setPlayers(String[] players) {
        Teamclass.players = players;
    }

}

 */

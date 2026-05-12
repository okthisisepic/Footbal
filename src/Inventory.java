import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static List<Spieler> spielerinventory = new ArrayList<>();
    public static List<Liga> LeaguesOwned = new ArrayList<>();
    public static String[] players = {"Liam Carter","Noah Müller","Ethan Rossi","Lucas Silva","Oliver Schmidt","James Anderson","Mateo Fernández","Daniel Novak","Adrian Kowalski","Santiago Reyes","Felix Wagner","Marco Bianchi","Hugo Laurent","Ryan Thompson","Kai Nakamura","Zlatan Ibramhimovich ","Sand vich","Nintendo Switch","Goal Messi","Penalty Ronaldo","Harry Kane Jr","John Kickball","FC WiFi","Thomas Milch","Speedy Gonzalez FC","Small toe","Ensar Türkmen","Alex","Withalm Rapid","Baller","T80 fuken balling"};
    public static void buyrandomcommonplayerpack() {
        String randomname =players[(int)(Math.random() * players.length-1)];
    }
}

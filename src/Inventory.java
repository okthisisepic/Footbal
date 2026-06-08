
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static int mone = 1000000;
    public static String[] preferredPositions = {"Torwart","Innenverteidiger","Außenverteidiger","DefensiverMittelspieler","ZentralMittelfeldSpieler","RechterFlügelSpieler","LinkerFlügelSpieler","OffensiverMittelFeldSpieler"};
    public static List<Spieler> spielerinventory = new ArrayList<>();
    public static ArrayList<League> leagues = new ArrayList<>();
    public static String[] players = {"Liam Carter","Noah Müller","Ethan Rossi","Lucas Silva","Oliver Schmidt","James Anderson","Mateo Fernández","Daniel Novak","Adrian Kowalski","Santiago Reyes","Felix Wagner","Marco Bianchi","Hugo Laurent","Ryan Thompson","Kai Nakamura","Zlatan Ibramhimovich ","Sand vich","Nintendo Switch","Goal Messi","Penalty Ronaldo","Harry Kane Jr","John Kickball","FC WiFi","Thomas Milch","Speedy Gonzalez FC","Small toe","Ensar Turkyeeee","Alex","Withalm Rapid","Baller","T80 fuken balling"};
    public static int tier = 0;
    public static void buyrandomcommonplayerpack() {
        if (mone >= 5000) {
            mone -= 5000;
            String randomname = players[(int) (Math.random() * players.length - 1)];
            int price = (int) (Math.random() * 10000) + 7;
            int range = (int) (Math.random() * 20) + 14;
            int accuracy = (int) (Math.random() * 40) + 1;
            int runningspeed = (int) (Math.random() * 3) + 1;
            String Position = preferredPositions[(int) (Math.random() * 7)];
            Spieler p = new Spieler(range);
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
            Spieler p = new Spieler(range);
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
            Spieler p = new Spieler(range);
            spielerinventory.add(p);
        }
    }
    public static void createLeague(String name, int promotion) {
        if (tier == 0) leagues.add(new League(name,tier));
        else {
            leagues.add(new League(name, tier, promotion));
            leagues.get(tier-1).setRelegation(promotion);
        }
        System.out.println("League created!");
    }
    public static void makeTeams(String[] names, float elo, double rating) {
        for (String name : names){
            leagues.get(tier).setTeam(new Team(name,elo,rating));
        }
        leagues.get(tier).makeMatchdayPlan();
        System.out.println("Teams created!");
    }
    public static DefaultTableModel constructTable(League l){
        DefaultTableModel table = new DefaultTableModel();
        List<Team> teams = new ArrayList<>(l.getTeams());
        teams.sort((team1, team2) -> {
            if (team2.points > team1.points) {
                return 1;
            }
            if (team1.points == team2.points) {
                if (team2.goalsTotal > team1.goalsTotal) {
                    return 1;
                } else if (team1.goalsTotal == team2.goalsTotal) {
                    if (team2.goals > team1.goals) {
                        return 1;
                    } else return -1;
                } else return -1;
            } else return -1;
        });
        table.addColumn("Pos");
        table.addColumn("Team");
        table.addColumn("MP");
        table.addColumn("W");
        table.addColumn("D");
        table.addColumn("L");
        table.addColumn("GS");
        table.addColumn("GC");
        table.addColumn("GD");
        table.addColumn("P");
        table.addRow(new Object[]{"Pos","Team","MP","W","D","L","GS","GC","GD","P"});
        for (Team t : teams){
            if (!(t.getName().equals("Free"))) {
                table.addRow(new Object[]{1, t.getName(), t.getGames(), t.getWins(), t.getDraws(), t.getLosses(), t.getGoals(), t.getGoalsAgainst(), t.getGoalsTotal(), t.getPoints()});
            }
        }
        return table;
    }
}

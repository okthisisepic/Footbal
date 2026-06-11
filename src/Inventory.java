
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    //public static int mone = 1000000;
    public static ArrayList<League> leagues = new ArrayList<>();
    public static int tier = 0;
    /*
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

     */
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

    public static void newSeason(){
        for (League l : leagues) {
            l.getTeams().sort((team1, team2) -> {
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
        }
        //relegation
        for (int i = 0; i < leagues.size()-1; i++) {
            for (int j = 0; j < leagues.get(i).getRelegation(); j++) {
                Team relegate = leagues.get(i).getTeams().get(leagues.get(i).getTeams().size()-j-2); //gets last place - last relegation spot
                leagues.get(i+1).getTeams().add(relegate);
                leagues.get(i).getTeams().remove(relegate);
            }
        }

        //promotion
        for (int i = 0; i < leagues.size()-1; i++) {
            for (int j = 0; j < leagues.get(i+1).getPromotion(); j++) {
                Team promote = leagues.get(i+1).getTeams().get(j); //gets last place - last relegation spot
                leagues.get(i).getTeams().add(promote);
                leagues.get(i+1).getTeams().remove(promote);
            }
        }
        for (League l : leagues) {
            for (Team t : l.getTeams()) {
                t.wins = t.draws = t.losses = t.points = t.games = t.goals = t.goalsAgainst = t.goalsTotal = 0;
            }
            l.setCountMatchday(1);
            l.clearPanel();
            l.makeMatchdayPlan();
        }
    }
}

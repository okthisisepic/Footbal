
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    public static ArrayList<League> leagues = new ArrayList<>();
    public static int tier = 0;

    /**
     * Creates a league
     * @param name League name
     * @param promotion Number of teams that are able to promote
     */
    public static void createLeague(String name, int promotion) {
        if (tier == 0) leagues.add(new League(name,tier));
        else {
            leagues.add(new League(name, tier, promotion));
            leagues.get(tier-1).setRelegation(promotion);
        }
        System.out.println("League created!");
    }

    /**
     * Makes teams for a league based on tier
     * @param names list of names
     * @param elo elo-rating for all teams
     * @param rating avg rating for players in a teeam
     */
    public static void makeTeams(String[] names, float elo, double rating) {
        for (String name : names){
            leagues.get(tier).setTeam(new Team(name,elo,rating));
        }
        leagues.get(tier).makeMatchdayPlan();
        System.out.println("Teams created!");
    }

    /**
     * Starts a new season by relegating/promoting teams, reseting tables and matchdays
     */
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
                Team relegate = leagues.get(i).getTeams().getLast(); //gets last place - last relegation spot
                leagues.get(i+1).getTeams().add(relegate);
                leagues.get(i).getTeams().remove(relegate);
            }
        }

        //promotion
        for (int i = 0; i < leagues.size()-1; i++) {
            for (int j = 0; j < leagues.get(i+1).getPromotion(); j++) {
                Team promote = leagues.get(i+1).getTeams().getFirst(); //gets first place - league winner
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

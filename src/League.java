import java.util.ArrayList;
import java.util.List;

public class League {
    private String name;
    private int tier;
    private int promotion;
    private int relegation;
    private ArrayList<Team> teams;
    private ArrayList<Team> champions;

    public League(String name, int tier) {
        this(name, tier, 0, 0, new ArrayList<>(), new ArrayList<>());
    }

    public League(String name, int tier, int promotion) {
        this(name, tier, promotion, 0, new ArrayList<>(), new ArrayList<>());
    }

    public League(String name, int tier, int promotion, int relegation, ArrayList<Team> teams, ArrayList<Team> champions) {
        this.name = name;
        this.tier = tier;
        this.promotion = promotion;
        this.relegation = relegation;
        this.teams = teams;
        this.champions = champions;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTier() {
        return tier;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getRelegation() {
        return relegation;
    }

    public void setRelegation(int relegation) {
        this.relegation = relegation;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeam(Team team) {
        teams.add(team);
    }

    public ArrayList<Team> getChampions() {
        return champions;
    }

    public void showTable() {
        ArrayList<Team> displayTable = new ArrayList<>(teams);
        displayTable.sort((team1, team2) -> {
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
        System.out.printf("%-7.5s %-20s %5s %5s %5s %5s %5s %5s %10s %10s \n","Place", "Team", "W", "D", "L", "GS", "GA", "GD", "P", "(ELO)");
        System.out.println("-------------------------------------------------------------------------------------------------");
        int place = 0;
        for (Team team : displayTable) {
            if (!team.name.equals("Free")) {
                place++;
                System.out.print(place+".");
                Team.constructTable(team);
            }
        }
    }

    public void afterSeason(){
        Team champion = new Team("null");
        int points = 0;
            for (Team t : this.getTeams()) {
                if (points < t.points) {
                    champion = t;
                    points = t.points;
                }
                if (champion.points == t.points) {
                    if (t.goalsTotal > champion.goalsTotal) {
                        champion = t;
                    }
                }
            }
        System.out.println(champion.name+" has won the league!");
        System.out.println();
        champions.add(champion);
        champion.championships++;
    }
}


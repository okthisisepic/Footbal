import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class League {
    private String name;
    private int tier;
    private int promotion;
    private int relegation;
    private ArrayList<Team> teams;
    private ArrayList<Team> champions;
    private ArrayList<ArrayList<Match>> matchdays = new ArrayList<>();
    private int countMatchday = 0;
    private JPanel resultsPanel = new JPanel();

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

    public ArrayList<ArrayList<Match>> getMatchdays() {
        return matchdays;
    }

    public int getCountMatchday() {
        return countMatchday;
    }

    public void setCountMatchday(int countMatchday) {
        this.countMatchday = countMatchday;
    }

    public void makeMatchdayPlan() {
        if (teams.size() % 2 != 0) {
            teams.add(new Team("Free"));
        }
        int teamAmount = teams.size();
        for (int round = 0; round < 2; round++) {
            for (int matchday = 0; matchday < teamAmount - 1; matchday++) {
                ArrayList<Match> matches = new ArrayList<>();
                for (int j = 0; j < teamAmount / 2; j++) {
                    Team team1 = teams.get(j);
                    Team team2 = teams.get(teamAmount-1-j);
                    if (!team1.name.equals("Free") && !team2.name.equals("Free")) {
                        if (round == 0) {
                            matches.add(new Match(team1, team2));
                        } else {
                            matches.add(new Match(team2, team1));
                        }
                    }
                }
                matchdays.add(matches);
                Collections.rotate(teams.subList(1, teamAmount), 1);
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


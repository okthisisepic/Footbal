import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private int countMatchday = 1;
    private JPanel resultsPanel = new JPanel();

    /**
     * Makes a League with only a name and tier
     * @param name
     * @param tier
     */
    public League(String name, int tier) {
        this(name, tier, 0, 0, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Makes a league with a name, tier and how many teams are able to promote
     * @param name
     * @param tier
     * @param promotion
     */
    public League(String name, int tier, int promotion) {
        this(name, tier, promotion, 0, new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Makes a league with all possible params
     * @param name
     * @param tier
     * @param promotion
     * @param relegation
     * @param teams
     * @param champions
     */
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

    public JPanel getResultsPanel() {
        return resultsPanel;
    }

    public void clearPanel(){
        resultsPanel.removeAll();
    }

    /**
     * Makes a league table
     * @return league table
     */
    public DefaultTableModel constructTable(){
        DefaultTableModel table = new DefaultTableModel();
        List<Team> teams = new ArrayList<>(getTeams());
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
        int place = 1;
        for (Team t : teams){
            if (!(t.getName().equals("Free"))) {
                table.addRow(new Object[]{place, t.getName(), t.getGames(), t.getWins(), t.getDraws(), t.getLosses(), t.getGoals(), t.getGoalsAgainst(), t.getGoalsTotal(), t.getPoints()});
                place++;
            }
        }
        return table;
    }

    /**
     * Makes a match day plan. Teams play in a round-robin system, the method ensures that an odd number of teams can still have a clean match plan.
     */
    public void makeMatchdayPlan() {
        matchdays.clear();
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
                resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.Y_AXIS));
            }
        }
    }
    public String outputallteams() {
        String retString = "";
        for (int i = 0; i < teams.size(); i++) {
            retString +=teams.get(i).getName();
            retString+= " ";
            retString +=teams.get(i).getBudget();
            retString+= " ";
            retString +=teams.get(i).getChampionships();
            retString+= " ";
            retString +=teams.get(i).getElo();
            retString+= " ";
            retString +=teams.get(i).getDraws();
            retString+= " ";
            retString +=teams.get(i).getGames();
            retString+= " ";
            retString +=teams.get(i).getGoals();
            retString+= " ";
            retString +=teams.get(i).getGoalsAgainst();
            retString+= " ";
            retString +=teams.get(i).getGoalsTotal();
            retString+= " ";
            retString +=teams.get(i).getLosses();
            retString+= " ";
            retString +=teams.get(i).getPoints();
            retString+= " ";
            retString +=teams.get(i).getWins();
            retString+= " ";
            retString +=teams.get(i).ALLPLAYERS();
        }
        return retString;
    }
}


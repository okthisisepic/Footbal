import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Team {
        String name;
        List<Spieler> players = new ArrayList<>();
        double budget;
        int games;
        int wins;
        int draws;
        int losses;
        int goals;
        int goalsAgainst;
        int goalsTotal;
        int points;
        int championships;
        float elo;

        public Team(String name){
            this(name,0,0,0,0,0,0,0,0,0,500);
        }

        public Team(String name,float elo){
            this(name,0,0,0,0,0,0,0,0,0,elo);
        }

        public Team(String name,int games,int wins,int draws,int losses,int goals,int goalsAgainst,int goalsTotal,int points,int championships,float elo){
            this.name = name;
            this.players = generatePlayerList();
            this.games = games;
            this.wins = wins;
            this.draws = draws;
            this.losses = losses;
            this.goals = goals;
            this.goalsAgainst = goalsAgainst;
            this.goalsTotal = goalsTotal;
            this.points = points;
            this.championships = championships;
            this.elo = elo;
        }

        public List<Spieler> generatePlayerList(){
            while (true) {
                List<Spieler> players = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    players.add(new Spieler());
                }
                int countAttackers = 0;
                int countMidfielders = 0;
                int countDefenders = 0;
                int countGoalkeepers = 0;
                for (Spieler p : players){
                    switch (p.getPosition()){
                        case ATT -> countAttackers++;
                        case MID -> countMidfielders++;
                        case DEF -> countDefenders++;
                        case GK -> countGoalkeepers++;
                    }
                }
                if (countAttackers >= 3 && countMidfielders >= 3 && countDefenders >= 4 && countGoalkeepers >= 1) return players;
                else players.clear();
            }
        }

    public String getName() {
        return name;
    }

    public List<Spieler> getPlayers() {
        return players;
    }

    public double getBudget() {
        return budget;
    }

    public int getGames() {
        return games;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public int getGoals() {
        return goals;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoalsTotal() {
        return goalsTotal;
    }

    public int getPoints() {
        return points;
    }

    public int getChampionships() {
        return championships;
    }

    public float getElo() {
        return elo;
    }

        public static void constructTable(Team team){
            System.out.printf("%-5s %-20s %5d %5d %5d %5d %5d %5d %10d %15f\n","",team.name,team.wins,team.draws,team.losses,team.goals,team.goalsAgainst,team.goalsTotal,team.points,team.elo);
        }
    }

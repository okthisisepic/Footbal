import java.util.ArrayList;
import java.util.List;

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
            this(name,0,0,0,0,0,0,0,0,0,500,50);
        }

        public Team(String name,float elo){
            this(name,0,0,0,0,0,0,0,0,0,elo,50);
        }

        public Team(String name,float elo, double rating){
        this(name,0,0,0,0,0,0,0,0,0,elo,rating);
    }

        public Team(String name,int games,int wins,int draws,int losses,int goals,int goalsAgainst,int goalsTotal,int points,int championships,float elo,double rating){
            this.name = name;
            this.players = generatePlayerList(rating);
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

        public List<Spieler> generatePlayerList(double rating){
            int countAttackers = 0;
            int countMidfielders = 0;
            int countDefenders = 0;
            int countGoalkeepers = 0;
            while (true) {
                List<Spieler> players = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    players.add(new Spieler(rating));
                }

                for (Spieler p : players){
                    switch (p.getPosition()){
                        case ATT -> countAttackers++;
                        case MID -> countMidfielders++;
                        case DEF -> countDefenders++;
                        case GK -> countGoalkeepers++;
                    }
                }
                if (countAttackers >= 3 && countMidfielders >= 3 && countDefenders >= 4 && countGoalkeepers >= 1){ return players;}
                else{
                    countAttackers = countMidfielders = countDefenders = countGoalkeepers = 0;
                    players.clear();
                }
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
 }

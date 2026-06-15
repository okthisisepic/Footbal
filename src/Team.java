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

    /**
     * Constructs a Team class with a single name
     * @param name name
     */
    public Team(String name){
            this(name,0,0,0,0,0,0,0,0,0,500,50);
        }

    /**
     * Constructs a Team class with a name and elo-rating
     * @param name name
     * @param elo elo-rating
     */
        public Team(String name,float elo){
            this(name,0,0,0,0,0,0,0,0,0,elo,50);
        }

    /**
     * Constructs a Team class with a name, elo-rating and an average rating a player can have
     * @param name name
     * @param elo elo-rating
     * @param rating avg player rating
     */
        public Team(String name,float elo, double rating){
        this(name,0,0,0,0,0,0,0,0,0,elo,rating);
    }

    /**
     * Constructs a team with all params possible
     * @param name
     * @param games
     * @param wins
     * @param draws
     * @param losses
     * @param goals
     * @param goalsAgainst
     * @param goalsTotal
     * @param points
     * @param championships
     * @param elo
     * @param rating
     */
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

    /**
     * Generates players with an avg rating, then adds them in a list. Ensures that there aare at least 3 Attackers, 3 Midfielders, 4 Defenders and 1 Goalkeeper.
     * @param rating
     * @return
     */
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

    public float getElo() {
        return elo;
    }
 }

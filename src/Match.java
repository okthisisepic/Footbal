import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Match {
    private Team team1;
    private Team team2;

    public Match(Team team1, Team team2){
        this.team1 = team1;
        this.team2 = team2;
        prepareMatch();
    }

    private void prepareMatch() {
        Set<Spieler> sortTeam1 = new TreeSet<>(new Comparator<Spieler>() {
            @Override
            public int compare(Spieler o1, Spieler o2) {
                return Double.compare(o1.getRating(), o2.getRating());
            }
        });
        Set<Spieler> sortTeam2 = new TreeSet<>(new Comparator<Spieler>() {
            @Override
            public int compare(Spieler o1, Spieler o2) {
                return Double.compare(o1.getRating(), o2.getRating());
            }
        });
        sortTeam1.addAll(team1.getPlayers());
        sortTeam2.addAll(team2.getPlayers());

        double teamStrength1 = team1.getElo() + (getStartingStrength(sortTeam1) * 15 * 0.25);
        double teamStrength2 = team2.getElo() + (getStartingStrength(sortTeam2) * 15 * 0.25);

        double lambda1 = 10 + (teamStrength1 - teamStrength2) * 0.01;
        double lambda2 = 10 + (teamStrength1 - teamStrength2) * 0.01;

        int expectedAttacksA = poissonRandom(lambda1);
        int expectedAttacksB = poissonRandom(lambda2);
        playMatch(sortTeam1, sortTeam2, expectedAttacksA, expectedAttacksB);
    }

    private double getStartingStrength(Set<Spieler> sortTeam){
        int count = 1;
        double getStrengthTeam = 0;
            for (Spieler p : sortTeam){
                if ((count >= 1 && count <= 3) && p.getPosition().equals(POSITION.ATT)){
                    count++;
                    getStrengthTeam++;
                }
                if ((count >= 4 && count <= 6) && p.getPosition().equals(POSITION.MID)){
                    count++;
                    getStrengthTeam++;
                }
                if ((count >= 7 && count <= 10) && p.getPosition().equals(POSITION.DEF)){
                    count++;
                    getStrengthTeam++;
                }
                if (count < 10 && p.getPosition().equals(POSITION.GK)){
                    count++;
                    getStrengthTeam++;
                }
            }
            return getStrengthTeam / 11;
    }
    private double attackStrength(Set<Spieler> players){
        double strength = 0;
        for (Spieler p : players) {
            if (p.getPosition().equals(POSITION.ATT)) strength += p.getRating();
            if (p.getPosition().equals(POSITION.MID)) strength += 0.5*p.getRating();
        }
        return strength;
    }
    private double defenseStrength(Set<Spieler> players) {
        double strength = 0;
        for (Spieler p : players) {
            if (p.getPosition().equals(POSITION.DEF)) strength += p.getRating();
            if (p.getPosition().equals(POSITION.MID)) strength += 0.5*p.getRating();
        }
        return strength;
    }

    private void playMatch(Set<Spieler> playersA, Set<Spieler> playersB, int expectedAttacksA, int expectedAttacksB){
        int goalsA = calculateAttacks(team1,team2,playersA,playersB,expectedAttacksA);
        int goalsB = calculateAttacks(team2,team1,playersB,playersA,expectedAttacksB);
        team1.goalsTotal = team1.getGoals() - team1.getGoalsAgainst();
        team2.goalsTotal = team2.getGoals() - team2.getGoalsAgainst();
        updateElo(team1,team2, goalsA, goalsB, expectedAttacksA, expectedAttacksB);
    }

    private int calculateAttacks(Team attackingTeam,Team defendingTeam,Set<Spieler> playersAttacking, Set<Spieler> playersDefending, int expectedAttacks){
        // calculate chances
        double attackStrength = attackStrength(playersAttacking);
        double defenseStrength = defenseStrength(playersDefending);
        int goals = 0;
        int onTarget = 0;
        int offTarget = 0;
        int blocked = 0;
        double attackQuality = attackStrength / (attackStrength + defenseStrength); //quality between 0 and 1
        for (int i = 0; i < expectedAttacks; i++) {
            if (Math.random() < attackQuality){
                if (Math.random() < 0.3 + attackQuality * 0.4) {
                    onTarget++;
                    Spieler goalkeeper = new Spieler();
                    for (Spieler p : playersDefending){
                        if (p.getPosition().equals(POSITION.GK)){ goalkeeper = p;
                        }
                    }
                    double saveChance = goalkeeper.getRating() / (goalkeeper.getRating() + attackStrength/4.5);
                    if (Math.random() > saveChance){
                        goals++;
                    }
                }
                else offTarget++;
            }
            else blocked++;
        }


        //calculate Ratings
        ArrayList<Spieler> attackers = new ArrayList<>();
        ArrayList<Spieler> midfieldersA = new ArrayList<>();
        ArrayList<Spieler> midfieldersB = new ArrayList<>();
        ArrayList<Spieler> defenders = new ArrayList<>();
        Spieler goalkeeper = new Spieler();

        for (Spieler p : playersAttacking){
            if (p.getPosition().equals(POSITION.ATT)) attackers.add(p);
            if (p.getPosition().equals(POSITION.MID)) midfieldersA.add(p);
        }
        for (Spieler p : playersDefending){
            if (p.getPosition().equals(POSITION.MID)) midfieldersB.add(p);
            if (p.getPosition().equals(POSITION.DEF)) defenders.add(p);
            if (p.getPosition().equals(POSITION.DEF)) goalkeeper = p;
        }
        // per goals
        for (int i = 0; i < goals; i++) {
            if (Math.random() < 0.75){
                int select = (int) (Math.random()*3);
                attackers.get(select).setRating(attackers.get(select).getRating()+0.5);
            }
            else {
                int select = (int) (Math.random()*3);
                midfieldersA.get(select).setRating(midfieldersA.get(select).getRating()+0.5);
            }
            goalkeeper.setRating(goalkeeper.getRating()-0.4);
        }
        // per onTarget
        for (int i = 0; i < onTarget; i++) {
            if (Math.random() < 0.75){
                int select = (int) (Math.random()*3);
                attackers.get(select).setRating(attackers.get(select).getRating()+0.15);
                if (Math.random() < 0.75){
                    int select2 = (int) (Math.random()*4);
                    defenders.get(select2).setRating(defenders.get(select2).getRating()-0.15);
                }
                else {
                    int select2 = (int) (Math.random()*3);
                    midfieldersB.get(select2).setRating(midfieldersA.get(select2).getRating()-0.15);
                }
            }
            else {
                int select = (int) (Math.random()*3);
                midfieldersA.get(select).setRating(midfieldersA.get(select).getRating()+0.15);
                if (Math.random() < 0.75){
                    int select2 = (int) (Math.random()*4);
                    defenders.get(select2).setRating(defenders.get(select2).getRating()-0.15);
                }
                else {
                    int select2 = (int) (Math.random()*3);
                    midfieldersB.get(select2).setRating(midfieldersA.get(select2).getRating()-0.15);
                }
            }

        }
        // per offTarget
        for (int i = 0; i < offTarget; i++) {
            if (Math.random() < 0.75){
                int select = (int) (Math.random()*4);
                attackers.get(select).setRating(attackers.get(select).getRating()-0.15);
            }
            else {
                int select = (int) (Math.random()*3);
                midfieldersA.get(select).setRating(midfieldersA.get(select).getRating()-0.15);
            }
        }
        // per blocked attack
        for (int i = 0; i < blocked; i++) {
            if (Math.random() < 0.75){
                int select = (int) (Math.random()*3);
                attackers.get(select).setRating(attackers.get(select).getRating()-0.1);
                if (Math.random() < 0.75){
                    int select2 = (int) (Math.random()*4);
                    defenders.get(select2).setRating(defenders.get(select2).getRating()+0.15);
                }
                else {
                    int select2 = (int) (Math.random()*3);
                    midfieldersB.get(select2).setRating(midfieldersA.get(select2).getRating()+0.15);
                }
            }
            else {
                int select = (int) (Math.random()*3);
                midfieldersA.get(select).setRating(midfieldersA.get(select).getRating()-0.15);
                if (Math.random() < 0.75){
                    int select2 = (int) (Math.random()*4);
                    defenders.get(select2).setRating(defenders.get(select2).getRating()+0.15);
                }
                else {
                    int select2 = (int) (Math.random()*3);
                    midfieldersB.get(select2).setRating(midfieldersA.get(select2).getRating()+0.15);
                }
            }
        }
        goalkeeper.setRating((onTarget-goals)*0.3);

        attackingTeam.goals+=goals;
        defendingTeam.goalsAgainst+=goals;

        // replace players
        for (Spieler p : attackers){
            for (Spieler t : attackingTeam.getPlayers()){
                if (p.equals(t)) attackingTeam.players.remove(t); attackingTeam.players.add(p);
            }
        }
        for (Spieler p : midfieldersA){
            for (Spieler t : attackingTeam.getPlayers()){
                if (p.equals(t)) attackingTeam.players.remove(t); attackingTeam.players.add(p);
            }
        }
        for (Spieler p : midfieldersB){
            for (Spieler t : defendingTeam.getPlayers()){
                if (p.equals(t)) defendingTeam.players.remove(t); defendingTeam.players.add(p);
            }
        }
        for (Spieler p : defenders){
            for (Spieler t : team2.getPlayers()){
                if (p.equals(t)) defendingTeam.players.remove(t); defendingTeam.players.add(p);
            }
        }

        return goals;
    }

    private static int poissonRandom(double lambda) {
        double L = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;

        do {
            k++;
            p *= Math.random();
        } while (p > L);

        return k - 1;
    }

    public static void updateElo(Team a, Team b, int toreA, int toreB, int chanceForA, int chanceForB){
        final int k = 32;
        float eloA = a.elo;
        float eloB = b.elo;

        if(toreA > toreB){
            a.elo = eloA + k*(1-chanceForA);
            b.elo = eloB + k*(-chanceForB);
        }
        else if(toreA < toreB){
            a.elo = eloA + k*(-chanceForA);
            b.elo = eloB + k*(1-chanceForB);
        }
        else {
            a.elo = eloA + k*(0.5f-chanceForA);
            b.elo = eloB + k*(0.5f-chanceForB);
        }
    }
}

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
        double attackStrength = attackStrength(playersA);
        double defenseStrength = defenseStrength(playersB);
        int goalsA = 0;
        int onTargetA = 0;
        int offTargetA = 0;
        int blockedByB = 0;
        double attackQualityA = attackStrength / (attackStrength + defenseStrength); //quality between 0 and 1
        for (int i = 0; i < expectedAttacksA; i++) {
            if (Math.random() < attackQualityA){
                if (Math.random() < 0.3 + attackQualityA * 0.4) {
                    onTargetA++;
                    Spieler goalkeeper = new Spieler();
                    for (Spieler p : playersB){
                        if (p.getPosition().equals(POSITION.GK)){ goalkeeper = p;
                        }
                    }
                    double saveChance = goalkeeper.getRating() / (goalkeeper.getRating() + attackStrength/4.5);
                    if (Math.random() > saveChance){
                        goalsA++;
                    }
                }
                else offTargetA++;
            }
            else blockedByB++;
        }
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

    public static void updateElo(Team a, Team b, int toreA, int toreB){
        final int k = 32;
        float eloA = a.elo;
        float eloB = b.elo;
        float chanceForA = eloA / (eloA+eloB);
        float chanceForB = eloB / (eloA+eloB);

        if(toreA > toreB){
            a.elo = eloA + k*(1-chanceForA);
            b.elo = eloB + k*(0-chanceForB);
        }
        else if(toreA < toreB){
            a.elo = eloA + k*(0-chanceForA);
            b.elo = eloB + k*(1-chanceForB);
        }
        else {
            a.elo = eloA + k*(0.5f-chanceForA);
            b.elo = eloB + k*(0.5f-chanceForB);
        }
    }
}

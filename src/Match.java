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

    public static void playGame(Team a, Team b, int toreA, int toreB){
        System.out.println();
        System.out.println(a.name+" "+toreA+" - "+toreB+" "+b.name);
        a.games++;
        b.games++;
        a.goals += toreA;
        a.goalsAgainst += toreB;
        b.goals += toreB;
        b.goalsAgainst += toreA;
        a.goalsTotal = a.goals - a.goalsAgainst;
        b.goalsTotal = b.goals - b.goalsAgainst;
        if(toreA > toreB){
            a.wins++;
            b.losses++;
            a.points += 3;
        }
        else if(toreA < toreB){
            b.wins++;
            a.losses++;
            b.points += 3;
        }
        else {
            a.points++;
            a.draws++;
            b.points++;
            b.draws++;
        }
        updateElo(a,b,toreA,toreB);
    }

    private void prepareMatch(){
        Set<Spieler> sortTeam1 = new TreeSet<>(new Comparator<Spieler>() {
            @Override
            public int compare(Spieler o1, Spieler o2) {
                return Double.compare(o1.getRating(),o2.getRating());
            }
        });
        Set<Spieler> sortTeam2 = new TreeSet<>(new Comparator<Spieler>() {
            @Override
            public int compare(Spieler o1, Spieler o2) {
                return Double.compare(o1.getRating(),o2.getRating());
            }
        });
        sortTeam1.addAll(team1.getPlayers());
        sortTeam2.addAll(team2.getPlayers());
        int count = 1;
        double getStrengthTeam1 = 0;
        for (Spieler p : sortTeam1){
            if ((count >= 1 && count <= 4) && p.getPosition().equals(POSITION.ATT)){

            }
        }
    }

    public static int calculateGoals(float eloA, float eloB) {
        double eloDiff = eloA - eloB;
        eloDiff = Math.max(-150, Math.min(150, eloDiff));

        double advantage = 1.0 / (1.0 + Math.exp(-eloDiff / 200.0));
        double expectedGoals = 0.8 + advantage * 1.0f;

        return poissonRandom(expectedGoals);
    }

    private static int poissonRandom(double lambda) {
        double multi = 1.4;
        lambda *= multi;
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

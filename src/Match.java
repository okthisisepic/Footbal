import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Match {
    private Team team1;
    private Team team2;
    private JPanel matchResultsPanel = new JPanel();

    public Match(Team team1, Team team2){
        this.team1 = team1;
        this.team2 = team2;
    }

    public void startMatch() {
        List<Spieler> sortTeam1 = new ArrayList<>(team1.getPlayers());
        sortTeam1.sort(Comparator.comparingDouble(Spieler::getRating));
        List<Spieler> sortTeam2 = new ArrayList<>(team2.getPlayers());
        sortTeam2.sort(Comparator.comparingDouble(Spieler::getRating));

        double teamStrength1 = team1.getElo() + (getStartingStrength(sortTeam1) * 15 * 0.25);
        double teamStrength2 = team2.getElo() + (getStartingStrength(sortTeam2) * 15 * 0.25);

        double lambda1 = 10 + (teamStrength1 - teamStrength2) * 0.01;
        double lambda2 = 10 + (teamStrength1 - teamStrength2) * 0.01;

        int expectedAttacksA = poissonRandom(lambda1);
        int expectedAttacksB = poissonRandom(lambda2);
        playMatch(sortTeam1, sortTeam2, expectedAttacksA, expectedAttacksB);
    }

    private double getStartingStrength(List<Spieler> sortTeam){
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
    private double attackStrength(List<Spieler> players){
        double strength = 0;
        for (Spieler p : players) {
            if (p.getPosition().equals(POSITION.ATT)) strength += p.getRating();
            if (p.getPosition().equals(POSITION.MID)) strength += 0.5*p.getRating();
        }
        return strength;
    }
    private double defenseStrength(List<Spieler> players) {
        double strength = 0;
        for (Spieler p : players) {
            if (p.getPosition().equals(POSITION.DEF)) strength += p.getRating();
            if (p.getPosition().equals(POSITION.MID)) strength += 0.5*p.getRating();
        }
        return strength;
    }

    private void playMatch(List<Spieler> playersA, List<Spieler> playersB, int expectedAttacksA, int expectedAttacksB){
        int goalsA = calculateAttacks(team1,team2,playersA,playersB,expectedAttacksA);
        int goalsB = calculateAttacks(team2,team1,playersB,playersA,expectedAttacksB);
        team1.goalsTotal = team1.getGoals() - team1.getGoalsAgainst();
        team2.goalsTotal = team2.getGoals() - team2.getGoalsAgainst();
        if (goalsA > goalsB){ team1.points+=3; team1.wins++; team2.losses++;}
        if (goalsB > goalsA){ team2.points+=3; team2.wins++; team1.losses++;}
        if (goalsA == goalsB){ team1.points++; team2.points++; team1.draws++; team2.draws++;}
        team1.games++; team2.games++;
        constructMatchPanel(goalsA,goalsB);
        updateElo(team1,team2, goalsA, goalsB);
    }

    private int calculateAttacks(Team attackingTeam,Team defendingTeam,List<Spieler> playersAttacking, List<Spieler> playersDefending, int expectedAttacks){
        // calculate chances
        double attackStrength = attackStrength(playersAttacking);
        double defenseStrength = defenseStrength(playersDefending);
        int goals = 0;
        int onTarget = 0;
        int offTarget = 0;
        int blocked = 0;
        Spieler goalkeeper = new Spieler(50);
        double attackQuality = attackStrength / (attackStrength + defenseStrength); //quality between 0 and 1
        for (int i = 0; i < expectedAttacks; i++) {
            if (Math.random() < attackQuality){
                if (Math.random() < 0.3 + attackQuality * 0.4) {
                    onTarget++;

                    boolean goalkeeperInitialized = false;
                    for (Spieler p : playersDefending) {
                        if (p.getPosition().equals(POSITION.GK) && !goalkeeperInitialized) {
                            goalkeeper = p;
                            goalkeeperInitialized = true;
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

        //get Players based on Position
        ArrayList<Spieler> attackers = new ArrayList<>();
        ArrayList<Spieler> midfieldersA = new ArrayList<>();
        ArrayList<Spieler> midfieldersB = new ArrayList<>();
        ArrayList<Spieler> defenders = new ArrayList<>();

        for (Spieler p : playersAttacking){
            if (attackers.size() <= 3) {
                if (p.getPosition().equals(POSITION.ATT)) attackers.add(p);
            }
            if (midfieldersA.size() <= 4) {
                if (p.getPosition().equals(POSITION.MID)) midfieldersA.add(p);
            }
        }
        for (Spieler p : playersDefending){
            if (midfieldersB.size() <= 4) {
                if (p.getPosition().equals(POSITION.MID)) midfieldersB.add(p);
            }
            if (defenders.size() <= 4) {
                if (p.getPosition().equals(POSITION.DEF)) defenders.add(p);
            }
                if (p.getPosition().equals(POSITION.DEF) && p.getRating() > goalkeeper.getRating()) goalkeeper = p;
        }
        // calculate ratings
        // per goals
        for (int i = 0; i < goals; i++) {
            if (Math.random() < 0.75){
                int select = (int) (Math.random()*3);
                attackers.get(select).setRating(attackers.get(select).getRating()+0.2);
            }
            else {
                int select = (int) (Math.random()*3);
                midfieldersA.get(select).setRating(midfieldersA.get(select).getRating()+0.2);
            }
            goalkeeper.setRating(goalkeeper.getRating()-0.15);
        }

            for (int i = 0; i < onTarget; i++) {
                if (Math.random() < 0.75){
                    int select = (int) (Math.random()*3);
                    attackers.get(select).setRating(attackers.get(select).getRating()+0.1);
                    if (Math.random() < 0.75){
                        int select2 = (int) (Math.random()*4);
                        defenders.get(select2).setRating(defenders.get(select2).getRating()-0.1);
                    }
                    else {
                        int select2 = (int) (Math.random()*3);
                        midfieldersB.get(select2).setRating(midfieldersA.get(select2).getRating()-0.1);
                    }
                }
                else {
                    int select = (int) (Math.random()*3);
                    midfieldersA.get(select).setRating(midfieldersA.get(select).getRating()+0.1);
                    if (Math.random() < 0.75){
                        int select2 = (int) (Math.random()*4);
                        defenders.get(select2).setRating(defenders.get(select2).getRating()-0.1);
                    }
                    else {
                        int select2 = (int) (Math.random()*3);
                        midfieldersB.get(select2).setRating(midfieldersA.get(select2).getRating()-0.1);
                    }
                }

            }
        // per offTarget
        for (int i = 0; i < offTarget; i++) {
            if (Math.random() < 0.75){
                int select = (int) (Math.random()*3);
                attackers.get(select).setRating(attackers.get(select).getRating()-0.05);
            }
            else {
                int select = (int) (Math.random()*3);
                midfieldersA.get(select).setRating(midfieldersA.get(select).getRating()-0.05);
            }
        }
        // per blocked attack
        for (int i = 0; i < blocked; i++) {
            if (Math.random() < 0.75){
                int select = (int) (Math.random()*3);
                attackers.get(select).setRating(attackers.get(select).getRating()-0.05);
                if (Math.random() < 0.75){
                    int select2 = (int) (Math.random()*4);
                    defenders.get(select2).setRating(defenders.get(select2).getRating()+0.1);
                }
                else {
                    int select2 = (int) (Math.random()*3);
                    midfieldersB.get(select2).setRating(midfieldersA.get(select2).getRating()+0.1);
                }
            }
            else {
                int select = (int) (Math.random()*3);
                midfieldersA.get(select).setRating(midfieldersA.get(select).getRating()-0.05);
                if (Math.random() < 0.75){
                    int select2 = (int) (Math.random()*4);
                    defenders.get(select2).setRating(defenders.get(select2).getRating()+0.1);
                }
                else {
                    int select2 = (int) (Math.random()*3);
                    midfieldersB.get(select2).setRating(midfieldersA.get(select2).getRating()+0.1);
                }
            }
        }
        goalkeeper.setRating((onTarget-goals)*0.2);
        attackingTeam.goals+=goals;
        defendingTeam.goalsAgainst+=goals;
        // replace players
        for (Spieler p : attackers) {
            for (int i = 0; i < attackingTeam.players.size(); i++) {
                if (attackingTeam.players.get(i).equals(p)) {
                    attackingTeam.players.set(i, p);
                    break;
                }
            }
        }

        for (Spieler p : midfieldersA) {
            for (int i = 0; i < attackingTeam.players.size(); i++) {
                if (attackingTeam.players.get(i).equals(p)) {
                    attackingTeam.players.set(i, p);
                    break;
                }
            }
        }

        for (Spieler p : midfieldersB) {
            for (int i = 0; i < defendingTeam.players.size(); i++) {
                if (defendingTeam.players.get(i).equals(p)) {
                    defendingTeam.players.set(i, p);
                    break;
                }
            }
        }

        for (Spieler p : defenders) {
            for (int i = 0; i < defendingTeam.players.size(); i++) {
                if (defendingTeam.players.get(i).equals(p)) {
                    defendingTeam.players.set(i, p);
                    break;
                }
            }
        }

        for (Spieler p : defendingTeam.getPlayers()) {
            for (int i = 0; i < defendingTeam.players.size(); i++) {
                if (p.getPosition().equals(POSITION.GK)) defendingTeam.players.set(i,goalkeeper); break;
            }
        }
        System.out.println("Goals: "+goals);
        System.out.println("On Target: "+onTarget);
        System.out.println("Off Target: "+offTarget);
        System.out.println("blocked/wasted chances: "+blocked);
        System.out.println();
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

    public static void updateElo(Team a, Team b, int toreA, int toreB){
        final int k = 32;
        float eloA = a.elo;
        float eloB = b.elo;
        float chanceForA = eloA / (eloA+eloB);
        float chanceForB = eloB / (eloA+eloB);

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

    public void constructMatchPanel(int goalsA, int goalsB){
        matchResultsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(team1.getName()+" "+goalsA+" - "+goalsB+" "+team2.getName());
        matchResultsPanel.add(label);
    }

    public JPanel getMatchResultsPanel() {
        return matchResultsPanel;
    }
}
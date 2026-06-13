import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Match {
    private Team team1;
    private Team team2;
    private JPanel matchResultsPanel = new JPanel();
    private ArrayList<String> eventList = new ArrayList<>();

    public Match(Team team1, Team team2){
        this.team1 = team1;
        this.team2 = team2;
    }

    public void startMatch() {
        matchResultsPanel.setLayout(new BoxLayout(matchResultsPanel,BoxLayout.Y_AXIS));
        team1.getPlayers().sort(Comparator.comparingDouble(Spieler::getRating).reversed());
        team2.getPlayers().sort(Comparator.comparingDouble(Spieler::getRating).reversed());

        for (Spieler p : team1.getPlayers()){
            if (p.getDaysInjured() > 0) p.setDaysInjured(p.getDaysInjured()-1);
            if (p.getDaysSuspended() > 0) p.setDaysSuspended(p.getDaysSuspended()-1);
            p.setGotYellowInMatch(false);
        }
        for (Spieler p : team2.getPlayers()){
            if (p.getDaysInjured() > 0) p.setDaysInjured(p.getDaysInjured()-1);
            if (p.getDaysSuspended() > 0) p.setDaysSuspended(p.getDaysSuspended()-1);
            p.setGotYellowInMatch(false);
        }

        ArrayList<Spieler> startingElevenTeamA = getStartingEleven(team1);
        ArrayList<Spieler> startingElevenTeamB = getStartingEleven(team2);

        double teamStrength1 = team1.getElo() + (getStartingStrength(startingElevenTeamA) * 15 * 0.25);
        double teamStrength2 = team2.getElo() + (getStartingStrength(startingElevenTeamB) * 15 * 0.25);

        double lambda1 = 15 + (teamStrength1 - teamStrength2) * 0.015;
        double lambda2 = 15 + (teamStrength1 - teamStrength2) * 0.015;

        int expectedAttacksA = poissonRandom(lambda1);
        int expectedAttacksB = poissonRandom(lambda2);
        playMatch(startingElevenTeamA,startingElevenTeamB,expectedAttacksA, expectedAttacksB);
    }

    private void playMatch(ArrayList<Spieler> startPlayersA, ArrayList<Spieler> startPlayersB ,int expectedAttacksA, int expectedAttacksB){
        int goalsA = calculateAttacks(team1,team2,startPlayersA,startPlayersB,expectedAttacksA);
        int goalsB = calculateAttacks(team2,team1,startPlayersB,startPlayersA,expectedAttacksB);
        team1.goalsTotal = team1.getGoals() - team1.getGoalsAgainst();
        team2.goalsTotal = team2.getGoals() - team2.getGoalsAgainst();
        if (goalsA > goalsB){ team1.points+=3; team1.wins++; team2.losses++;}
        if (goalsB > goalsA){ team2.points+=3; team2.wins++; team1.losses++;}
        if (goalsA == goalsB){ team1.points++; team2.points++; team1.draws++; team2.draws++;}
        team1.games++; team2.games++;
        JLabel result = new JLabel(team1.getName()+" "+goalsA+" - "+goalsB+" "+team2.getName());
        result.setFont(new Font("Serif", Font.PLAIN, 20));
        matchResultsPanel.add(result);
        constructPanel();
        updateElo(team1,team2, goalsA, goalsB);
    }

    private ArrayList<Spieler> getStartingEleven(Team t){
        ArrayList<Spieler> returnPlayers = new ArrayList<>();
        int countAttackers = 0;
        int countMidfielders = 0;
        int countDefenders = 0;
        int countGoalkeepers = 0;
        for (Spieler p : t.getPlayers()){
            if (p.getDaysSuspended()==0 && p.daysInjured==0) {
                if (p.getPosition().equals(POSITION.ATT) && countAttackers < 3) {
                    returnPlayers.add(p);countAttackers++;
                } else if (p.getPosition().equals(POSITION.MID) && countMidfielders < 3) {
                    returnPlayers.add(p);countMidfielders++;
                } else if (p.getPosition().equals(POSITION.DEF) && countDefenders < 4) {
                    returnPlayers.add(p);countDefenders++;
                } else if (p.getPosition().equals(POSITION.GK) && countGoalkeepers < 1) {
                    returnPlayers.add(p);countGoalkeepers++;
                }
            }
        }
        return returnPlayers;
    }

    private double getStartingStrength(List<Spieler> p){
        double getStrengthTeam = 0;
        for (Spieler player : p){ getStrengthTeam += player.getRating();}
        return getStrengthTeam / 11;
    }
    private double getAttackStrength(List<Spieler> players){
        double strength = 0;
        for (Spieler p : players) {
            if (p.getPosition().equals(POSITION.ATT)) strength += p.getRating();
            if (p.getPosition().equals(POSITION.MID)) strength += 0.5*p.getRating();
            if (p.getPosition().equals(POSITION.DEF)) strength += 0.25*p.getRating();
        }
        return strength;
    }
    private double getDefenseStrength(List<Spieler> players) {
        double strength = 0;
        for (Spieler p : players) {
            if (p.getPosition().equals(POSITION.DEF)) strength += p.getRating();
            if (p.getPosition().equals(POSITION.MID)) strength += 0.5*p.getRating();
            if (p.getPosition().equals(POSITION.ATT)) strength += 0.25*p.getRating();
        }
        return strength;
    }

    private int calculateAttacks(Team attackingTeam,Team defendingTeam,ArrayList<Spieler> startPlayersAttackingSide,ArrayList<Spieler> startPlayersDefendingSide,int expectedAttacks){
        // calculate chances
        double attackStrength = getAttackStrength(startPlayersAttackingSide);
        double defenseStrength = getDefenseStrength(startPlayersDefendingSide);
        int goals = 0;
        int onTarget = 0;
        int offTarget = 0;
        int blocked = 0;
        Spieler goalkeeper = new Spieler(50);
        for (int i = 0; i < expectedAttacks; i++) {
            int min = (int)((i+1 + Math.random()) * 90.0 / expectedAttacks);
            double attackQuality = attackStrength / (attackStrength + defenseStrength); //quality between 0 and 1
            if (Math.random() < attackQuality) {
                if (Math.random() < 0.3 + attackQuality * 0.4) {
                    onTarget++;
                    double rand = Math.random();
                    Spieler randplayer;
                    if (rand > 0.9){
                        while (true) {
                            randplayer = startPlayersAttackingSide.get((int) (Math.random() * startPlayersAttackingSide.size()));
                            if (randplayer.getPosition().equals(POSITION.DEF)) {
                                randplayer.setRating(randplayer.getRating()+0.1); break;
                            }
                        }
                    }
                    else if (rand > 0.65){
                        while (true) {
                            randplayer = startPlayersAttackingSide.get((int) (Math.random() * startPlayersAttackingSide.size()));
                            if (randplayer.getPosition().equals(POSITION.MID)) {
                                randplayer.setRating(randplayer.getRating()+0.1); break;
                            }
                        }
                    }
                    else {
                        while (true) {
                            randplayer = startPlayersAttackingSide.get((int) (Math.random() * startPlayersAttackingSide.size()));
                            if (randplayer.getPosition().equals(POSITION.ATT)) {
                                randplayer.setRating(randplayer.getRating() + 0.1); break;
                            }
                        }
                    }
                    double rand2 = Math.random();
                    if (rand2 > 0.9){
                        updateRating(-0.1,POSITION.ATT,startPlayersDefendingSide);
                    }
                    else if (rand2 > 0.65){
                        updateRating(-0.1,POSITION.MID,startPlayersDefendingSide);
                    }
                    else updateRating(-0.1,POSITION.DEF,startPlayersDefendingSide);
                        for (Spieler p : startPlayersDefendingSide) {
                            if (p.getPosition().equals(POSITION.GK)) {
                                    goalkeeper = p;
                                    break;
                                }
                            }
                    double saveChance = goalkeeper.getRating() / (goalkeeper.getRating() + randplayer.getRating());
                    if (Math.random() > saveChance) {
                        goals++;
                        randplayer.setRating(randplayer.getRating()+0.2);
                        goalkeeper.setRating(goalkeeper.getRating()-0.2);
                        eventList.add(min+"' "+randplayer.getName()+" ("+attackingTeam.getName()+") Goal");
                    }
                    else goalkeeper.setRating(goalkeeper.getRating()+0.2);
                } else {
                    offTarget++;
                    // per offTarget
                    double rand = Math.random();
                    if (rand > 0.9){
                        updateRating(-0.05,POSITION.DEF,startPlayersAttackingSide);
                    }
                    else if (rand > 0.65){
                        updateRating(-0.05,POSITION.MID,startPlayersAttackingSide);
                    }
                    else updateRating(-0.05,POSITION.ATT,startPlayersAttackingSide);
                    double rand2 = Math.random();
                    if (rand2 > 0.9){
                        updateRating(-0.05,POSITION.ATT,startPlayersDefendingSide);
                    }
                    else if (rand2 > 0.65){
                        updateRating(-0.05,POSITION.MID,startPlayersDefendingSide);
                    }
                    else updateRating(-0.05,POSITION.DEF,startPlayersDefendingSide);
                }
            } else {
                blocked++;
                //red card
                if (Math.random() < 0.005){
                    int random = (int) (Math.random()*startPlayersDefendingSide.size());
                    Spieler p = startPlayersDefendingSide.get(random);
                    eventList.add(min+"' "+p.getName()+" ("+defendingTeam.getName()+") Red Card");
                    p.setRedCards(p.getRedCards()+1);
                        p.setDaysSuspended(2);
                        for (int j = 0; j < defendingTeam.getPlayers().size(); j++) {
                            if (p.equals(defendingTeam.getPlayers().get(random))) startPlayersDefendingSide.set(random,p);
                        }
                        startPlayersDefendingSide = getStartingEleven(defendingTeam);
                }
                //yellow card
                else if (Math.random() < 0.09){
                    int random = (int) (Math.random()*startPlayersDefendingSide.size());
                    Spieler p = startPlayersDefendingSide.get(random);

                    p.setYellowCards(p.getYellowCards()+1);
                    if (p.isGotYellowInMatch()){
                        p.setDaysSuspended(2);
                        eventList.add(min+"' "+p.getName()+" ("+defendingTeam.getName()+") Yellow-Red Card");
                        for (int j = 0; j < defendingTeam.getPlayers().size(); j++) {
                            if (p.equals(defendingTeam.getPlayers().get(random))) startPlayersDefendingSide.set(random,p);
                        }
                        startPlayersDefendingSide = getStartingEleven(defendingTeam);
                    }
                    else{ eventList.add(min+"' "+p.getName()+" ("+defendingTeam.getName()+") Yellow Card"); p.setGotYellowInMatch(true);}
                }

                // per blocked attack
                double rand = Math.random();
                if (rand > 0.9){
                    updateRating(+0.1,POSITION.ATT,startPlayersDefendingSide);
                }
                else if (rand > 0.65){
                    updateRating(+0.1,POSITION.MID,startPlayersDefendingSide);
                }
                else updateRating(+0.1,POSITION.DEF,startPlayersDefendingSide);
                double rand2 = Math.random();
                if (rand2 > 0.9){
                    updateRating(-0.05,POSITION.DEF,startPlayersAttackingSide);
                }
                else if (rand2 > 0.65){
                    updateRating(+0.1,POSITION.MID,startPlayersAttackingSide);
                }
                else updateRating(+0.1,POSITION.ATT,startPlayersAttackingSide);

            }

            //injury
            if (Math.random() < 0.005){
                if (Math.random() > 0.15){ //attackingTeam
                    int random = (int) (Math.random()*startPlayersAttackingSide.size());
                    Spieler p = startPlayersAttackingSide.get(random);
                    eventList.add(min+"' "+p.getName()+" ("+attackingTeam.getName()+") Injury");
                    p.setDaysInjured((int) (Math.random()*9));
                    for (int j = 0; j < attackingTeam.getPlayers().size(); j++) {
                        if (p.equals(attackingTeam.getPlayers().get(random))) startPlayersAttackingSide.set(random,p);
                    }
                    startPlayersAttackingSide = getStartingEleven(attackingTeam);
                } else { // defendingTeam
                    int random = (int) (Math.random()*startPlayersDefendingSide.size());
                    Spieler p = startPlayersDefendingSide.get(random);
                    eventList.add(min+"' "+p.getName()+" ("+defendingTeam.getName()+") Injury");
                    p.setDaysInjured((int) (Math.random()*9));
                    for (int j = 0; j < defendingTeam.getPlayers().size(); j++) {
                        if (p.equals(defendingTeam.getPlayers().get(random))) startPlayersDefendingSide.set(random,p);
                    }
                    startPlayersDefendingSide = getStartingEleven(defendingTeam);
                }
            }
        }

        attackingTeam.goals+=goals;
        defendingTeam.goalsAgainst+=goals;
        // replace players
        for (Spieler p : startPlayersAttackingSide){
            for (int i = 0; i < attackingTeam.getPlayers().size(); i++) {
                if (p.equals(attackingTeam.getPlayers().get(i))){
                    attackingTeam.getPlayers().set(i,p);
                }
            }
        }
        for (Spieler p : startPlayersDefendingSide){
            for (int i = 0; i < defendingTeam.getPlayers().size(); i++) {
                if (p.equals(defendingTeam.getPlayers().get(i))){
                    defendingTeam.getPlayers().set(i,p);
                }
            }
        }
        for (int i = 0; i < startPlayersDefendingSide.size(); i++) {
            if (startPlayersDefendingSide.get(i).getPosition().equals(POSITION.GK)) startPlayersDefendingSide.set(i,goalkeeper);
        }


        System.out.println("Goals: "+goals);
        System.out.println("On Target: "+onTarget);
        System.out.println("Off Target: "+offTarget);
        System.out.println("blocked/wasted chances: "+blocked);
        System.out.println();


        return goals;
    }

    private void updateRating(double updateValue,POSITION position,ArrayList<Spieler> playerList) {
        while (true){
            int random = (int) (Math.random()* playerList.size());
            if (playerList.get(random).getPosition().equals(position)){ playerList.get(random).setRating(playerList.get(random).getRating()+updateValue); break;}
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

    private void constructPanel(){
        eventList.sort((o1, o2) -> {
        String s1 = "";
        String s2 = "";
        for (int i = 0; i < o1.length(); i++) {
            if (Character.isDigit(o1.charAt(i))) s1+=o1.charAt(i);
            else break;
        }
        for (int i = 0; i < o2.length(); i++) {
            if (Character.isDigit(o2.charAt(i))) s2+=o2.charAt(i);
            else break;
        }
        return Integer.compare(Integer.parseInt(s1), Integer.parseInt(s2));
    });

        for (String event : eventList){
            matchResultsPanel.add(new JLabel(event));
        }
    }

    public JPanel getMatchResultsPanel() {
        return matchResultsPanel;
    }
}
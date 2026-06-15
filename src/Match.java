import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Match {
    private Team team1;
    private Team team2;
    private JPanel matchResultsPanel = new JPanel();
    private ArrayList<String> eventList = new ArrayList<>();

    /**
     * Constructs Match-Class
     * @param team1 first Team (home)
     * @param team2 second Team (away)
     */
    public Match(Team team1, Team team2){
        this.team1 = team1;
        this.team2 = team2;
    }

    /**
     * Starts/Prepares a match, players from each team will be sorted by rating, to make it easier to make a starting eleven for each team. Next, a quantity of chances will be computed, to make simulation possible.
     */
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

    /**
     * Plays the match. After calculating all possible chances, points, goals and so on, is getting updated by their respective team, which then their power-rating (ELO) will be influenced. 
     * @param startPlayersA 11 starting players from first team
     * @param startPlayersB 11 starting players from second team
     * @param expectedAttacksA All possible chances for the first team
     * @param expectedAttacksB All possible chances for the second team
     */
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

    /**
     * Gets the strongest 11 players of a team. This method follows a simple 4-3-3 formation system(3 ATT, 3 MID, 4 DEF, 1 GK). The method also checks whether a player is eligible for an upcoming match e.g. injuries, suspensions.
     * @param t A team
     * @return strongest 11 players.
     */
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

    /**
     * Computes the combined strength of a starting eleven divided by 11.
     * @param p List of players
     * @return Average strength
     */
    private double getStartingStrength(List<Spieler> p){
        double getStrengthTeam = 0;
        for (Spieler player : p){ getStrengthTeam += player.getRating();}
        return getStrengthTeam / 11;
    }

    /**
     * Determines the total strength a starting eleven is capable of performing attacks.
     * @param players List of players of a starting eleven
     * @return total attack strength
     */
    private double getAttackStrength(List<Spieler> players){
        double strength = 0;
        for (Spieler p : players) {
            if (p.getPosition().equals(POSITION.ATT)) strength += p.getRating();
            if (p.getPosition().equals(POSITION.MID)) strength += 0.5*p.getRating();
            if (p.getPosition().equals(POSITION.DEF)) strength += 0.25*p.getRating();
        }
        return strength;
    }

    /**
     * Determines the total strength a starting eleven is capable of defending incoming attacks.
     * @param players List of players of a starting eleven
     * @return total attack strength
     */
    private double getDefenseStrength(List<Spieler> players) {
        double strength = 0;
        for (Spieler p : players) {
            if (p.getPosition().equals(POSITION.DEF)) strength += p.getRating();
            if (p.getPosition().equals(POSITION.MID)) strength += 0.5*p.getRating();
            if (p.getPosition().equals(POSITION.ATT)) strength += 0.25*p.getRating();
        }
        return strength;
    }

    /**
     * Calculate all possible attacks performed by the attacking team. Chances can either be blocked, go off target, go on target but saved by the keeper, or lead to a goal. Each event has its benefits and consequences. Yellow and red cards and even injuries can occur. Players improve and get worse depending on the performance a player made.
     * @param attackingTeam Team making attacks
     * @param defendingTeam Team defending attacks
     * @param startPlayersAttackingSide Starting eleven of attacking team
     * @param startPlayersDefendingSide Starting eleven of defending team
     * @param expectedAttacks all attacks the attacking team is expected to make
     * @return goals for the attacking team
     */
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
                    startPlayersDefendingSide = updateStartingElevenAfterRedCard(defendingTeam, startPlayersDefendingSide, min);
                }
                //yellow card
                else if (Math.random() < 0.09){
                    startPlayersDefendingSide = updateStartingElevenAfterYellowCard(defendingTeam, startPlayersDefendingSide, min);
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
                    startPlayersAttackingSide = updateTeamInjury(attackingTeam, startPlayersAttackingSide, min);
                } else { // defendingTeam
                    startPlayersDefendingSide = updateTeamInjury(defendingTeam, startPlayersDefendingSide, min);
                }
            }
        }

        attackingTeam.goals+=goals;
        defendingTeam.goalsAgainst+=goals;
        // replace players
        updatePlayersOfTeam(attackingTeam, startPlayersAttackingSide);
        updatePlayersOfTeam(defendingTeam, startPlayersDefendingSide);

        //replace goalkeeper
        for (int i = 0; i < startPlayersDefendingSide.size(); i++) {
            if (startPlayersDefendingSide.get(i).getPosition().equals(POSITION.GK)) startPlayersDefendingSide.set(i,goalkeeper);
        }

        return goals;
    }

    /**
     * Updates the starting eleven of a team after an injury was occurred.
     * @param t Team
     * @param startPlayers players starting
     * @param min The minute the event occurs
     * @return list of updated players
     */
    private ArrayList<Spieler> updateTeamInjury(Team t, ArrayList<Spieler> startPlayers, int min) {
        int random = (int) (Math.random()* startPlayers.size());
        Spieler p = startPlayers.get(random);
        eventList.add(min +"' "+p.getName()+" ("+ t.getName()+") Injury");
        p.setDaysInjured((int) (Math.random()*9));
        for (int j = 0; j < t.getPlayers().size(); j++) {
            if (p.equals(t.getPlayers().get(random))) startPlayers.set(random,p);
        }
        startPlayers = getStartingEleven(t);
        return startPlayers;
    }

    /**
     * Updates the starting eleven of the defending team, when a yellow card was issued.
     * @param defendingTeam .
     * @param startPlayersDefendingSide .
     * @param min The minute this event occurred
     * @return updated list of players
     */
    private ArrayList<Spieler> updateStartingElevenAfterYellowCard(Team defendingTeam, ArrayList<Spieler> startPlayersDefendingSide, int min) {
        int random = (int) (Math.random()* startPlayersDefendingSide.size());
        Spieler p = startPlayersDefendingSide.get(random);

        p.setYellowCards(p.getYellowCards()+1);
        if (p.isGotYellowInMatch()){
            p.setDaysSuspended(2);
            eventList.add(min +"' "+p.getName()+" ("+ defendingTeam.getName()+") Yellow-Red Card");
            for (int j = 0; j < defendingTeam.getPlayers().size(); j++) {
                if (p.equals(defendingTeam.getPlayers().get(random))) startPlayersDefendingSide.set(random,p);
            }
            startPlayersDefendingSide = getStartingEleven(defendingTeam);
        }
        else{ eventList.add(min +"' "+p.getName()+" ("+ defendingTeam.getName()+") Yellow Card"); p.setGotYellowInMatch(true);}
        return startPlayersDefendingSide;
    }

    /**
     * Updates the starting eleven of the defending team, when a red card was issued.
     * @param defendingTeam .
     * @param startPlayersDefendingSide .
     * @param min The minute this event occurred
     * @return updated list of players
     */
    private ArrayList<Spieler> updateStartingElevenAfterRedCard(Team defendingTeam, ArrayList<Spieler> startPlayersDefendingSide, int min) {
        int random = (int) (Math.random()* startPlayersDefendingSide.size());
        Spieler p = startPlayersDefendingSide.get(random);
        eventList.add(min +"' "+p.getName()+" ("+ defendingTeam.getName()+") Red Card");
        p.setRedCards(p.getRedCards()+1);
        p.setDaysSuspended(2);
        for (int j = 0; j < defendingTeam.getPlayers().size(); j++) {
            if (p.equals(defendingTeam.getPlayers().get(random))) startPlayersDefendingSide.set(random,p);
        }
        startPlayersDefendingSide = getStartingEleven(defendingTeam);
        return startPlayersDefendingSide;
    }

    /**
     * Sets players of a starting team in their team
     * @param t Team
     * @param startPlayers starting eleven
     */
    private void updatePlayersOfTeam(Team t, ArrayList<Spieler> startPlayers) {
        for (Spieler p : startPlayers){
            for (int i = 0; i < t.getPlayers().size(); i++) {
                if (p.equals(t.getPlayers().get(i))){
                    t.getPlayers().set(i,p);
                }
            }
        }
    }

    /**
     * Updates a players rating by adding updateValue
     * @param updateValue The value added to the player
     * @param position postion of the player
     * @param playerList list of starting players of a team
     */
    private void updateRating(double updateValue,POSITION position,ArrayList<Spieler> playerList) {
        while (true){
            int random = (int) (Math.random()* playerList.size());
            if (playerList.get(random).getPosition().equals(position)){ playerList.get(random).setRating(playerList.get(random).getRating()+updateValue); break;}
        }
    }

    /**
     * An algorithm that gets used to determine the amount of expected attacks a team can get
     * @param lambda a given value to influence the algorithm
     * @return exceptedAttacks
     */
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

    /**
     * Updates the power-rating(ELO Rating) of a team based on ELOs between teams and the match end result
     * @param a
     * @param b
     * @param toreA
     * @param toreB
     */
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

    /**
     * Sort match events by minute and adds them to a JPanel called matchResultsPanel
     */
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TeamMain {
    public static ArrayList<League> leagues = new ArrayList<>();
    static int tier = 0;
    public static void main(String[] args) throws InterruptedException {
       menu();
    }
    public static void mainGame() throws InterruptedException {
        for (League l : leagues) {
            int teamAmount = l.getTeams().size();
            if (!l.getTeams().isEmpty()) {
                System.out.println(l.getName());
                for (int rounds = 1; rounds <= 2; rounds++) {
                    for (int i = 0; i < teamAmount - 1; i++) {
                        for (int j = 0; j < teamAmount / 2; j++) {
                            Team team1 = l.getTeams().get(j);
                            Team team2 = l.getTeams().get(teamAmount - 1 - j);
                            if (!team1.name.equals("Free") && !team2.name.equals("Free")) {
                                clearScreen();
                                Team.playGame(team1, team2, Team.calculateGoals(team1.elo, team2.elo), Team.calculateGoals(team2.elo, team1.elo));
                            }
                        }
                        Collections.rotate(l.getTeams().subList(1, teamAmount), 1);
                        l.showTable();
                    }
                }
                System.out.println();
            } else System.out.println("Create a league first!");
            l.afterSeason();
        }
        System.out.println("Session ended, create new teams or start a new season");
        newSeason();
        menu();
    }

    private static void menu() throws InterruptedException {
        while (true) {
            System.out.println();
            System.out.println("1. Simulate");
            System.out.println("2. Create Teams");
            System.out.println("3. Create Leagues");
            System.out.println("4. See Championships");
            System.out.println("5. Quit game");
            System.out.println();
            String inPut = readLine("Input: ");
            switch (inPut){
                case "1": mainGame(); break;
                case "2": makeTeams(); break;
                case "3": makeLeagues(); break;
                case "4": checkChampions(); break;
                case "5": System.exit(0);
            }
        }
    }

    private static void makeLeagues(){
        while (true) {
            String inLeague = readLine("Put the name for the league(Tier "+tier+") ('end' stop creating new): ");
            if (!inLeague.isEmpty()) {
                if (inLeague.equals("end")) {
                    break;
                } else {
                    tier++;
                    leagues.add(new League(inLeague,tier));
                }
            }
        }
        for (int i = 0; i < leagues.size()-1; i++) {
            String in = readLine("How many relegation/promotion spots between Tier "+i+" and Tier "+(i+1)+"?");
            leagues.get(i).setRelegation(Integer.parseInt(in));
            leagues.get(i+1).setPromotion(Integer.parseInt(in));
        }
    }

    private static void makeTeams() {
        for (League l : leagues) {
            while (true) {
                String inTeam = readLine("Put in teams (in League "+l.getName()+") (type 'end' to end creating teams): ");
                if (!inTeam.isEmpty()) {
                    if (inTeam.equals("end")) {
                        break;
                    } else {
                        l.getTeams().add(new Team(inTeam));
                    }
                }
            }
            if (l.getTeams().size() % 2 != 0) {
                l.getTeams().add(new Team("Free", 0));
            }
        }
    }

    private static void newSeason(){
        for (League l : leagues) {
            l.getTeams().sort((team1, team2) -> {
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
        }
        //relegation
        for (int i = 0; i < leagues.size()-1; i++) {
            for (int j = 0; j < leagues.get(i).getRelegation(); j++) {
                Team relegate = leagues.get(i).getTeams().get(leagues.get(i).getTeams().size()-j-2); //gets last place - last relegation spot
                leagues.get(i+1).getTeams().add(relegate);
                leagues.get(i).getTeams().remove(relegate);
            }
        }

        //promotion
        for (int i = 0; i < leagues.size()-1; i++) {
            for (int j = 0; j < leagues.get(i+1).getPromotion(); j++) {
                Team promote = leagues.get(i+1).getTeams().get(j); //gets last place - last relegation spot
                leagues.get(i).getTeams().add(promote);
                leagues.get(i+1).getTeams().remove(promote);
            }
        }

        for (League l : leagues) {
            for (Team t : l.getTeams()) {
                t.wins = t.draws = t.losses = t.points = t.games = t.goals = t.goalsAgainst = t.goalsTotal = 0;
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void checkChampions(){
        System.out.println();
        System.out.println("---CHAMPIONS---");
        System.out.println();
        for (League l : leagues) {
            System.out.println(l.getName()+" champions:");
            for (Team t : l.getTeams()) {
                if(t.championships > 0) System.out.println(t.championships + "x " + t.name);
            }
        }
        System.out.println();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}

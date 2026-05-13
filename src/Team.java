public class Team {
        String name;
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

        public static void constructTable(Team team){
            System.out.printf("%-5s %-20s %5d %5d %5d %5d %5d %5d %10d %15f\n","",team.name,team.wins,team.draws,team.losses,team.goals,team.goalsAgainst,team.goalsTotal,team.points,team.elo);
        }
    }

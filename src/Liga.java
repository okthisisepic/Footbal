import java.util.ArrayList;
import java.util.List;

public class Liga {
    public List<Team> Teams = new ArrayList<>();
    public String name;
    public int networth;
    public int amountofteams;

    public Liga(List<Team> teams, String name, int networth, int amountofteams) {
        Teams = teams;
        this.name = name;
        this.networth = networth;
        this.amountofteams = amountofteams;
    }

    /*
    public void makesumteasm() {
        List<Spieler> empty= new ArrayList<>();
        for (int i = 0; i < amountofteams; i++) {
            Teams.add(new Team(empty,9,name+" Team"+(i+1)));
        }

    }

     */
}

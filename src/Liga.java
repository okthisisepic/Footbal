import java.util.ArrayList;
import java.util.List;

public class Liga {
    public List<Teamclass> Teams = new ArrayList<>();
    public String name;
    public int networth;
    public int amountofteams;

    public Liga(List<Teamclass> teams, String name, int networth, int amountofteams) {
        Teams = teams;
        this.name = name;
        this.networth = networth;
        this.amountofteams = amountofteams;
        makesumteasm();
    }


    public void makesumteasm() {
        List<Spieler> empty= new ArrayList<>();
        for (int i = 0; i < amountofteams; i++) {
            Teams.add(new Teamclass(empty,9,name+" Team"+(i+1)));
        }

    }

}

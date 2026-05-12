import java.util.ArrayList;
import java.util.List;

public class Liga {
    public List<Spieler> PlayersinLeague = new ArrayList<>();
    public String name;
    public int networth;

    public Liga(int networth, String name, List<Spieler> playersinLeague) {
        this.networth = networth;
        this.name = name;
        PlayersinLeague = playersinLeague;
        System.out.println(name);
    }

}

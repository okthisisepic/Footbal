import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

enum POSITION{ATT, MID, DEF, GK}

public class Spieler {
    private String name = "";
    private double price;
    private double rating;
    private POSITION position;

    public Spieler() {
        generatePlayer();
    }

    public void generatePlayer() {
        try {
            List<String> line = Files.readAllLines(Path.of("src/namenliste.txt"));
            String[] names = line.getFirst().split(",");
            //Name generieren
            name += names[(int) (Math.random()* names.length)];
            name += " "+names[(int) (Math.random()* names.length)];
            price = Math.nextUp(Math.random()*100000);
            int decidePosition = (int) (Math.random()*11);
            rating = (int) (Math.random() * 65) + 35;
            if (decidePosition == 10){
                position = POSITION.GK;
            }
            if (decidePosition < 9){
                position = POSITION.DEF;
            }
            if (decidePosition < 6){
                position = POSITION.MID;
            }
            if (decidePosition < 3){
                position = POSITION.ATT;
            }
            teststats();
        } catch (Exception e){
            System.out.println("Fehler beim Spieler generieren!");
        }
        teststats();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public POSITION getPosition() {
        return position;
    }

    public void teststats() {
        System.out.println("Name: "+getName()+" price: "+getPrice()+"$ rating: "+getRating()+" position: "+getPosition());
    }
}

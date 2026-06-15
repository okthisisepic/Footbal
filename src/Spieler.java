import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

enum POSITION{ATT, MID, DEF, GK}

public class Spieler {
    private String name = "";
    private double rating;
    private POSITION position;
    private int yellowCards = 0;
    private int redCards = 0;
    private boolean gotYellowInMatch = false;
    private int daysSuspended = 0;
    int daysInjured = 0;


    public Spieler(double rating) {
        generatePlayer(rating);
    }

    public void generatePlayer(double rating) {
        try {
            List<String> line = Files.readAllLines(Path.of("src/namenliste.txt"));
            String[] names = line.getFirst().split(",");
            //Name generieren
            name += names[(int) (Math.random()* names.length)];
            name += names[(int) (Math.random()* names.length)];
            int decidePosition = (int) (Math.random()*11);
            this.rating = (int) ((Math.random() * 30) - 15 + rating);
            if (decidePosition == 10){
                position = POSITION.GK;
            }
            if (decidePosition <= 9){
                position = POSITION.DEF;
            }
            if (decidePosition < 6){
                position = POSITION.MID;
            }
            if (decidePosition < 3){
                position = POSITION.ATT;
            }
        } catch (Exception e){
            System.out.println("Fehler beim Spieler generieren!");
        }
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public POSITION getPosition() {
        return position;
    }

    public void setPosition(POSITION position) {
        this.position = position;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getDaysSuspended() {
        return daysSuspended;
    }

    public void setDaysSuspended(int daysSuspended) {
        this.daysSuspended = daysSuspended;
    }

    public int getDaysInjured() {
        return daysInjured;
    }

    public void setDaysInjured(int daysInjured) {
        this.daysInjured = daysInjured;
    }

    public boolean isGotYellowInMatch() {
        return gotYellowInMatch;
    }

    public void setGotYellowInMatch(boolean gotYellowInMatch) {
        this.gotYellowInMatch = gotYellowInMatch;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Spieler spieler = (Spieler) o;
        return Objects.equals(name, spieler.name) && position == spieler.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position);
    }
}

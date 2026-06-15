import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

enum POSITION{ATT, MID, DEF, GK}

/**
 * This is the player class....
 */
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

    /**
     * This function generates a Player using a rating parameter
     * <p>
     * @param rating number for the rating of the player
     */
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

    /**
     * @return returns the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @return returns the rating of the player
     */
    public double getRating() {
        return rating;
    }

    /**
     * @return returns the position of the player
     */
    public POSITION getPosition() {
        return position;
    }

    /**
     * @param position sets the Position of the player
     */
    public void setPosition(POSITION position) {
        this.position = position;
    }

    /**
     * @param rating sets the rating of the player
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @param name sets the name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return gets the amount of yellow cards this player has received
     */
    public int getYellowCards() {
        return yellowCards;
    }

    /**
     * @param yellowCards sets the amount of yellow cards this player has received
     */
    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    /**
     * @return gets the amount of days this player has been suspended
     */
    public int getDaysSuspended() {
        return daysSuspended;
    }

    /**
     * @param daysSuspended sets the amount of days this player has been suspended
     */
    public void setDaysSuspended(int daysSuspended) {
        this.daysSuspended = daysSuspended;
    }

    /**
     * @return gets the amount of days this player was injured :(
     */
    public int getDaysInjured() {
        return daysInjured;
    }

    /**
     * @param daysInjured sets the amount of days this player was injured :(
     */
    public void setDaysInjured(int daysInjured) {
        this.daysInjured = daysInjured;
    }

    /**
     * @return gets if the player has received a yellow card in the current match
     */
    public boolean isGotYellowInMatch() {
        return gotYellowInMatch;
    }

    /**
     * @param gotYellowInMatch sets if the player has received a yellow card in the current match
     */
    public void setGotYellowInMatch(boolean gotYellowInMatch) {
        this.gotYellowInMatch = gotYellowInMatch;
    }

    /**
     * @return gets all red cards the player has received
     */
    public int getRedCards() {
        return redCards;
    }

    /**
     * @param redCards sets the amount of red cards the player has received
     */
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

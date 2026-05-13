enum POSITION{ATT, MID, DEF, GK}

public class Spieler {
    private String name;
    private double price;
    private double rating;
    private POSITION position;

    public Spieler() {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.position = position;
    }

    public static void generatePlayer() {

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

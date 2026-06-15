/**
 * Football Simulator - V1.0
 * @author Ensar Türkmen, Viktor Burger
 */
import javax.swing.*;
import java.awt.event.MouseWheelEvent;


public class Main {
    /**
     * Starts the programm
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MW mw = new MW();
            } catch (InterruptedException e) {
                System.out.println("Stuff went down");
            }
        });
    }
}
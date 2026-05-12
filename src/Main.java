import javax.swing.*;
import java.awt.event.MouseWheelEvent;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MW mw = new MW();
                mw.show();
            }
        });
    }
}
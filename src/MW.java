import javax.swing.*;

public class MW {
    private JFrame window;

    public MW() {
        window = new JFrame();
        window.setTitle("Fobal simulator");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(700,700);
        window.setLocationRelativeTo(null);
    }
    public void show() {
        window.setVisible(true);
    }
}

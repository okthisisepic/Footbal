import javax.swing.*;
import java.awt.*;

public class MW {
    private JFrame window;

    public MW() throws InterruptedException {
        System.out.println("Stealing your ram....");
        Thread.sleep(1000);
        System.out.println("Mining bitcoin....");
        Thread.sleep(1000);
        System.out.println("stealing your bandwidth....");
        Thread.sleep(100);
        System.out.println("Theft");
        Thread.sleep(100);
        System.out.println("its gunna start reaallll soon trust");
        Thread.sleep(1000);
        System.out.println("ok fine");
        Thread.sleep(100);
        run();
    }
    public void run() {
        window = new JFrame();
        window.setTitle("Fobal simulator");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(700,700);
        window.setLocationRelativeTo(null);
        JPanel scenebuilderknockoff = new JPanel();
        scenebuilderknockoff.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        Button generatebutton = new Button("Generate Player");
        scenebuilderknockoff.add(generatebutton);
        window.add(scenebuilderknockoff, BorderLayout.CENTER);
        scenebuilderknockoff.setBackground(Color.BLUE);


    }
    public void show() {
        window.setVisible(true);
    }
}

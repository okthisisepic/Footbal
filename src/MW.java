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
        Taskbar();



    }
    public void show() {
        window.setVisible(true);
    }
    public void Taskbar() {
        JPanel TASKLEITSE = new JPanel();
        TASKLEITSE.setLayout(new FlowLayout(FlowLayout.LEFT,10,5)); // hier kannst sagen wo die elemente beginnen lass es aber LEFT urr clean Flow layout macht so left right arrangen oder top bottom
        JButton generatebutton = new JButton("Generate Player"); //button :3
        generatebutton.setFocusable(false); //macht das du net unabsichtlich mit enter taste was drückst
        //ImageIcon generateicon = new ImageIcon("generateiconPlaceHolder.png"); placeholder kannst ändern
        //generatebutton.setIcon(generateicon);
        TASKLEITSE.add(generatebutton);
        //das Placeholder icon ist urr riesig
        generatebutton.setToolTipText("generate a random Player");
        window.add(TASKLEITSE, BorderLayout.SOUTH); //CENTER = Ganzer screen lol NORTH ist oben custimize ts wie du willst SOUTH ist so taskleiste was urrrr geilo ausieht
        TASKLEITSE.setBackground(Color.BLUE);
    }
}

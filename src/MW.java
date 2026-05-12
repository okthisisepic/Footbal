import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// in this class not only is the ui stored here but also the inventory for direct access to the ui
public class MW {
    private JFrame window;
    public static Inventory inventory = new Inventory();
    public MW() throws InterruptedException {
        System.out.println("Stealing your ram....");
        Thread.sleep(1000);
        System.out.println("Mining bitcoin....");
        Thread.sleep(1000);
        System.out.println("stealing your bandwidth....");
        Thread.sleep(500);
        System.out.println("Theft");
        Thread.sleep(500);
        System.out.println("stealing");
        Thread.sleep(500);
        System.out.println("this is all a joke no sue pls");
        Thread.sleep(500);
        for (int i = 0; i < 30; i++) {
            System.out.println("this is all a joke no sue pls");
            Thread.sleep(100);
        }
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
        //das Placeholder icon ist urr riesig nicht empfehlenswert

        TASKLEITSE.add(generatebutton);
        generatebutton.setToolTipText("generate a random Player");


        window.add(TASKLEITSE, BorderLayout.SOUTH); //CENTER = Ganzer screen lol NORTH ist oben custimize ts wie du willst SOUTH ist so taskleiste was urrrr geilo ausieht
        TASKLEITSE.setBackground(Color.BLUE);

        generatebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory.buyrandomcommonplayerpack();
            }
        });
    }
}

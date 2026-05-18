import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

// in this class not only is the ui stored here but also the inventory for direct access to the ui
public class MW {
    private JFrame window;
    public static Inventory inventory = new Inventory();

    public MW() throws InterruptedException {
        System.out.println("Stealing your ram....");
        Thread.sleep(500);
        System.out.println("Mining bitcoin....");
        Thread.sleep(500);
        System.out.println("stealing your bandwidth....");
        Thread.sleep(500);
        System.out.println("Theft");
        Thread.sleep(500);
        System.out.println("stealing");
        Thread.sleep(500);
        run();

    }
/*
    public void start(){
        JFrame startWindow = new JFrame();
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        JLabel startLabel = new JLabel("Fantasy Football Simulator");
        JButton createLeagues = new JButton("Create Leagues");
        JButton viewLeagues = new JButton("View Leagues");
        startWindow.setVisible(true);
        startWindow.setTitle("Fobal simulator");
        startWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startWindow.setSize(700, 700);
        startWindow.setLocationRelativeTo(null);
        startPanel.add(startLabel);
        startPanel.add(createLeagues);
        startPanel.add(viewLeagues);
        startWindow.add(startPanel);
        createLeagues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
    }

 */
    public void run() {
        window = new JFrame();
        window.setTitle("Fobal simulator");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(700, 700);
        window.setLocationRelativeTo(null);
        contentbroswer();
    }

    public void show() {
        window.setVisible(true);
    }

    public void Taskbar() {


        JPanel TASKLEITSE = new JPanel();

        TASKLEITSE.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // hier kannst sagen wo die elemente beginnen lass es aber LEFT urr clean Flow layout macht so left right arrangen oder top bottom
        JButton generatebutton = new JButton("commonplayer 5000$"); //button :3
        generatebutton.setFocusable(false); //macht das du net unabsichtlich mit enter taste was drückst


        //ImageIcon generateicon = new ImageIcon("generateiconPlaceHolder.png"); placeholder kannst ändern
        //generatebutton.setIcon(generateicon);
        //das Placeholder icon ist urr riesig nicht empfehlenswert

        TASKLEITSE.add(generatebutton);
        generatebutton.setToolTipText("Buy a common player for 5000$");
        JButton rarebutton = new JButton("rare player 500000$"); //button :3
        rarebutton.setFocusable(false); //macht das du net unabsichtlich mit enter taste was drückst

        TASKLEITSE.add(generatebutton);
        generatebutton.setToolTipText("Buy a common player for 5000$");

        TASKLEITSE.add(rarebutton);
        rarebutton.setToolTipText("Buy a rare player for 500000$");


        JButton legendarybutton = new JButton("legendary player 5000000$"); //button :3
        legendarybutton.setFocusable(false); //macht das du net unabsichtlich mit enter taste was drückst


        TASKLEITSE.add(legendarybutton);
        legendarybutton.setToolTipText("Buy a legendary player for 5000000$");

        JLabel Monebar = new JLabel(String.valueOf(Inventory.mone) + "$");
        Monebar.setToolTipText("Your money");
        Monebar.setForeground(Color.green);
        TASKLEITSE.add(Monebar);
        window.add(TASKLEITSE, BorderLayout.SOUTH); //CENTER = Ganzer screen lol NORTH ist oben custimize ts wie du willst SOUTH ist so taskleiste was urrrr geilo ausieht
        TASKLEITSE.setBackground(Color.BLUE);

        generatebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory.buyrandomcommonplayerpack();
                Monebar.setText(String.valueOf(Inventory.mone + "$"));
            }
        });
        rarebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory.buyrandomrareplayerpack();
                Monebar.setText(String.valueOf(Inventory.mone + "$"));
            }
        });
        legendarybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inventory.buyrandomlegendaryplayerpack();
                Monebar.setText(String.valueOf(Inventory.mone + "$"));
            }
        });
        JButton inventorybutton = new JButton("Inventory");
        TASKLEITSE.add(inventorybutton);
        inventorybutton.setFocusable(false);
        inventorybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventorywindow();
            }
        });


    }

    public void contentbroswer() {
        JPanel Contentbroswer = new JPanel();

        Contentbroswer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton createbutton = new JButton("Create your league:name and amount of teams");
        JTextField name = new JTextField(10); //10 ist die längedes felds
        JLabel nameofleague = new JLabel("Inventory.league.name");
        nameofleague.setForeground(Color.blue);
        Contentbroswer.add(createbutton);
        Contentbroswer.add(name);
        Contentbroswer.add(nameofleague);
        nameofleague.setVisible(false);
        createbutton.setFocusable(false);
        JTextField amount = new JTextField(10);
        Contentbroswer.add(amount);
        window.add(Contentbroswer, BorderLayout.NORTH); //CENTER = Ganzer screen lol NORTH ist oben custimize ts wie du willst SOUTH ist so taskleiste was urrrr geilo ausieht
        Contentbroswer.setBackground(Color.RED);
        createbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!name.getText().isEmpty() & !name.getText().isBlank() & !amount.getText().isEmpty() & !amount.getText().isBlank()) {
                    if (Integer.parseInt(amount.getText()) >10) {
                        System.out.println("no we are not doing ts thats way to many teams");
                    } else {
                        Inventory.createleague(name.getText(), Integer.parseInt(amount.getText()));
                        createbutton.setVisible(false);
                        name.setVisible(false);
                        nameofleague.setText(Inventory.league.getName());
                        nameofleague.setVisible(true);
                        amount.setVisible(false);
                        Taskbar();
                        gaming();
                    }
                }
            }
        });

    }

    public void gaming() {
        JPanel Gamewindow = new JPanel();
        JLabel gaming = new JLabel("G A M I N G");
        Gamewindow.add(gaming);
        Gamewindow.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        window.add(Gamewindow, BorderLayout.CENTER); //CENTER = Ganzer screen lol NORTH ist oben custimize ts wie du willst SOUTH ist so taskleiste was urrrr geilo ausieht
        Gamewindow.setBackground(Color.GREEN);
    }

    public void inventorywindow() {
        JFrame invenwindow = new JFrame();
        invenwindow.setSize(700, 700);
        invenwindow.setTitle("Inventory");
        invenwindow.setVisible(true);


        JPanel Teamlist = new JPanel();
        Teamlist.setLayout(new BoxLayout(Teamlist, BoxLayout.Y_AXIS));


        for (int i = 0; i < Inventory.league.getTeams().size(); i++) {
            Teamlist.add(new JButton(Inventory.league.getTeams().get(i).name));

            for (int j = 0; j < Inventory.league.getTeams().get(i).players.size(); j++) {
                Teamlist.add(new JPanel().add(new JLabel(Inventory.league.getTeams().get(i).players.get(j).getName())));
            }


        }
        JScrollPane Scrollbox = new JScrollPane(Teamlist);
        invenwindow.add(Scrollbox);

        Teamlist.setBackground(Color.green);


    }
}

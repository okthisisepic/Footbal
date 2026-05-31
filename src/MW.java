import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

// in this class not only is the ui stored here but also the inventory for direct access to the ui
public class MW {
    private JFrame window;

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
        System.out.println("Its time");
        Thread.sleep(500);
        System.out.println("to play");
        Thread.sleep(500);
        System.out.println("with some");
        Thread.sleep(500);
        System.out.println("B A L L S");
        Thread.sleep(500);
        createwindowwhichisseperateforsomeweirdreasonbutididntdecidethatitwasntmeitsjustthatensarreallyreallywantsthistobeseperatesohereweare();

    }
    public void createwindowwhichisseperateforsomeweirdreasonbutididntdecidethatitwasntmeitsjustthatensarreallyreallywantsthistobeseperatesohereweare() {

        JFrame startWindow = new JFrame("Fobal simulator");
        JPanel startPanel = new JPanel(new GridBagLayout());
        startPanel.setBackground(Color.blue);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel startLabel = new JLabel("Fobal simulator");
        JButton createLeagues = new JButton("Create Leagues");

        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createLeagues.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Elemente hinzufügen
        content.add(startLabel);
        content.add(Box.createVerticalStrut(20));
        content.add(createLeagues);
        content.add(Box.createVerticalStrut(10));


        startPanel.add(content);
        startWindow.add(startPanel);
        startWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startWindow.setSize(700, 700);
        startWindow.setLocationRelativeTo(null);
        startWindow.setVisible(true);

        createLeagues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
                startWindow.dispose();
            }
        });
    }

    public void run() {
        window = new JFrame();
        window.setTitle("Fobal simulator");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(700, 700);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        contentbroswer();
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
        JPanel mainPanel = new JPanel(new GridBagLayout());
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        JPanel displayTierPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JPanel leagueNameContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JPanel inputTeamsContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JPanel averageRatingContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JPanel setEloContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JPanel setPromotionRelegationContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JPanel Contentbroswer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JLabel displayTier = new JLabel(String.format("Tier:  %n",Inventory.tier));
        JLabel labelForLeagueName  = new JLabel("Insert the name of your desired league");
        JTextField leagueName = new JTextField(20);
        JLabel labelForInputTeams  = new JLabel("Insert your teams by name, seperate with ','.");
        JTextField inputTeams = new JTextField(20);
        JLabel labelForAverageRating  = new JLabel("Set the average rating for players in this league (default 50)");
        JTextField averageRating = new JTextField(20);
        JLabel labelForSetElo  = new JLabel("Set ELO-Rating (default 500)");
        JTextField setElo = new JTextField(20);
        JLabel labelForSetPromotion  = new JLabel("Set number of teams to promote to next upper tier");
        JTextField setPromotion = new JTextField(20);
        displayTierPanel.add(displayTier);
        leagueNameContent.add(labelForLeagueName);
        leagueNameContent.add(leagueName);
        inputTeamsContent.add(labelForInputTeams);
        inputTeamsContent.add(inputTeams);
        averageRatingContent.add(labelForAverageRating);
        averageRatingContent.add(averageRating);
        setEloContent.add(labelForSetElo);
        setEloContent.add(setElo);
        setPromotionRelegationContent.add(labelForSetPromotion);
        setPromotionRelegationContent.add(setPromotion);
        setPromotionRelegationContent.setVisible(Inventory.tier != 0);
        displayTierPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leagueNameContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputTeamsContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        averageRatingContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        setEloContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        setPromotionRelegationContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(displayTierPanel);
        mainContent.add(leagueNameContent);
        mainContent.add(inputTeamsContent);
        mainContent.add(averageRatingContent);
        mainContent.add(setEloContent);
        mainContent.add(setPromotionRelegationContent);
        mainPanel.add(mainContent);
        window.add(mainPanel,BorderLayout.CENTER);
        JButton createbutton = new JButton("Create League");
        JTextField name = new JTextField(10); //10 ist die längedes felds
        JLabel nameofleague = new JLabel("Inventory.league.name");
        nameofleague.setForeground(Color.blue);
        Contentbroswer.add(createbutton);
        nameofleague.setVisible(false);
        createbutton.setFocusable(false);
        JTextField amount = new JTextField(10);
        Contentbroswer.add(amount);
        window.add(Contentbroswer, BorderLayout.NORTH); //CENTER = Ganzer screen lol NORTH ist oben custimize ts wie du willst SOUTH ist so taskleiste was urrrr geilo ausieht
        Contentbroswer.setBackground(Color.RED);
        createbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!leagueName.getText().isBlank() && !inputTeams.getText().isBlank()) {
                    String[] teamNames = inputTeams.getText().split(",");
                    Inventory.createleague(leagueName.getText(), teamNames.length);
                    createbutton.setVisible(false);
                    name.setVisible(false);
                    nameofleague.setText(Inventory.league.getName());
                    nameofleague.setVisible(true);
                    amount.setVisible(false);
                    mainPanel.setVisible(false);
                    Taskbar();
                    gaming();
                }
            }
        });

    }

    public void gaming() {
        JPanel Gamewindow = new JPanel();
        JButton gaming = new JButton("G A M I N G");
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

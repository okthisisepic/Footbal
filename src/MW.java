import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// in this class not only is the ui stored here but also the inventory for direct access to the ui
public class MW {
    private JFrame viewWindow;
    private JFrame window;

    public MW() throws InterruptedException {
        System.out.println("B A L L S");
        Thread.sleep(500);
        createStartWindow();
    }
    public void createStartWindow() {

        JFrame startWindow = new JFrame("Football simulator");
        JPanel startPanel = new JPanel(new GridBagLayout());
        startPanel.setBackground(Color.blue);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel startLabel = new JLabel("Football simulator");
        JButton createLeagues = new JButton("Create Leagues");
        JButton viewLeagues = new JButton("View Leagues");

        startLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        createLeagues.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewLeagues.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Elemente hinzufügen
        content.add(startLabel);
        content.add(Box.createVerticalStrut(20));
        content.add(createLeagues);
        content.add(Box.createVerticalStrut(10));
        content.add(viewLeagues);


        startPanel.add(content);
        startWindow.add(startPanel);
        startWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startWindow.setSize(700, 700);
        startWindow.setLocationRelativeTo(null);
        startWindow.setVisible(true);

        createLeagues.addActionListener(_ -> {
            run();
            startWindow.dispose();
        });
        viewLeagues.addActionListener(_ -> {
            runViewWindow();
            startWindow.dispose();
        });
    }

    public void run() {
        window = new JFrame();
        window.setTitle("Create Leagues");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(700, 700);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        contentbroswer();
    }

    public void runViewWindow(){
        viewWindow = new JFrame();
        viewWindow.setTitle("View Leagues");
        viewWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewWindow.setSize(900, 900);
        viewWindow.setLocationRelativeTo(null);
        viewWindow.setVisible(true);
        Taskbar();
        gaming();
    }

    public void Taskbar() {
        JPanel TASKLEISTE = new JPanel();
        TASKLEISTE.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // hier kannst sagen, wo die elemente beginnen lass es aber LEFT urr clean Flow layout macht so left right arrangen oder top bottom
        //ImageIcon generateicon = new ImageIcon("generateiconPlaceHolder.png"); placeholder kannst ändern
        //generatebutton.setIcon(generateicon);
        //das Placeholder icon ist urr riesig nicht empfehlenswert
        viewWindow.add(TASKLEISTE, BorderLayout.SOUTH); //CENTER = Ganzer screen lol NORTH ist oben custimize ts wie du willst SOUTH ist so taskleiste was urrrr geilo ausieht
        TASKLEISTE.setBackground(Color.BLUE);

        JButton inventorybutton = new JButton("Inventory");
        TASKLEISTE.add(inventorybutton);
        inventorybutton.setFocusable(false);
        inventorybutton.addActionListener(_ -> {
            if (!Inventory.leagues.isEmpty()) inventorywindow();
        });
        JButton newSeasonButton = new JButton("New Season");
        TASKLEISTE.add(newSeasonButton);
        newSeasonButton.setFocusable(false);
        newSeasonButton.addActionListener(_ -> {
            int countCompletedLeagues = 0;
             for (League l : Inventory.leagues){
                 if (l.getTeams().size()*2-1==l.getCountMatchday()) countCompletedLeagues++;
             }
             if (Inventory.leagues.size()==countCompletedLeagues){
                 Inventory.newSeason();
             }
             else{
                 JLabel label = new JLabel("Alle Ligen müssen zuerst simuliert werden!");
                 TASKLEISTE.remove(label);
                 TASKLEISTE.add(label);
             }
        });
        JButton exitButton = new JButton("Exit");
        TASKLEISTE.add(exitButton);
        exitButton.setFocusable(false);
        exitButton.addActionListener(_ -> {
            createStartWindow();
            viewWindow.dispose();
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
            JLabel displayTier = new JLabel("Tier: " + Inventory.tier);
            JLabel labelForLeagueName = new JLabel("Insert the name of your desired league");
            JTextField leagueName = new JTextField(20);
            JLabel labelForInputTeams = new JLabel("Insert your teams by name, seperate with ','.");
            JTextField inputTeams = new JTextField(20);
            JLabel labelForAverageRating = new JLabel("Set the average rating for players in this league (default 50)");
            JTextField averageRating = new JTextField(20);
            JLabel labelForSetElo = new JLabel("Set ELO-Rating (default 500)");
            JTextField setElo = new JTextField(20);
            JLabel labelForSetPromotion = new JLabel("Set number of teams to promote to next upper tier");
            JTextField setPromotion = new JTextField(20);
            JPanel exitPanel = new JPanel();
            JButton exit = new JButton("Exit");
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
            setPromotionRelegationContent.setVisible(false);
            exitPanel.add(exit);
            Contentbroswer.add(exitPanel);
            displayTierPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leagueNameContent.setAlignmentX(Component.CENTER_ALIGNMENT);
            inputTeamsContent.setAlignmentX(Component.CENTER_ALIGNMENT);
            averageRatingContent.setAlignmentX(Component.CENTER_ALIGNMENT);
            setEloContent.setAlignmentX(Component.CENTER_ALIGNMENT);
            setPromotionRelegationContent.setAlignmentX(Component.CENTER_ALIGNMENT);
            exitPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainContent.add(displayTierPanel);
            mainContent.add(leagueNameContent);
            mainContent.add(inputTeamsContent);
            mainContent.add(averageRatingContent);
            mainContent.add(setEloContent);
            mainContent.add(setPromotionRelegationContent);
            mainPanel.add(mainContent);
            window.add(mainPanel, BorderLayout.CENTER);
            JButton createbutton = new JButton("Create League");
            JTextField name = new JTextField(10); //10 ist die längedes felds
            JLabel nameofleague = new JLabel("Inventory.league.name");
            nameofleague.setForeground(Color.blue);
            Contentbroswer.add(createbutton);
            nameofleague.setVisible(false);
            createbutton.setFocusable(false);
            window.add(Contentbroswer, BorderLayout.NORTH); //CENTER = Ganzer screen lol NORTH ist oben custimize ts wie du willst SOUTH ist so taskleiste was urrrr geilo ausieht
            Contentbroswer.setBackground(Color.RED);
            createbutton.addActionListener(_ -> {
                if (!leagueName.getText().isBlank() && !inputTeams.getText().isBlank()) {
                    String[] teamNames = inputTeams.getText().split(",");
                    if (setPromotion.getText().isBlank()) setPromotion.setText("0");
                    if (setElo.getText().isBlank()) setElo.setText("500");
                    if (averageRating.getText().isBlank()) averageRating.setText("50");
                    Inventory.createLeague(leagueName.getText(), Integer.parseInt(setPromotion.getText()));
                    Inventory.makeTeams(teamNames, Float.parseFloat(setElo.getText()), Double.parseDouble(averageRating.getText()));
                    Inventory.tier++;
                    displayTier.setText("Tier: "+Inventory.tier);
                    leagueName.setText("");
                    inputTeams.setText("");
                    averageRating.setText("");
                    setElo.setText("");
                    setPromotion.setText("");
                    setPromotionRelegationContent.setVisible(true);
                }
            });

            exit.addActionListener(_ -> {
                window.dispose();
                createStartWindow();
            });
        }

    public void gaming() {
        JPanel gameWindow = new JPanel();
        for (League l : Inventory.leagues){
            JButton b = new JButton(l.getName());
            gameWindow.add(b);
            b.addActionListener(_ -> buildWindowForLeague(l));
        }
        gameWindow.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        viewWindow.add(gameWindow, BorderLayout.CENTER); //CENTER = Ganzer screen lol NORTH ist oben custimize ts wie du willst SOUTH ist so taskleiste was urrrr geilo ausieht
        gameWindow.setBackground(Color.GREEN);
    }

    public void buildWindowForLeague(League l){
        JFrame leagueWindow = new JFrame();
        leagueWindow.setTitle("View League: " + l.getName());
        leagueWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leagueWindow.setSize(1200, 900);
        leagueWindow.setLocationRelativeTo(null);
        JPanel leaguePanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton close = new JButton("Close");
        topPanel.add(close);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton simulateMatchday = new JButton("Simulate Matchday");
        JButton simulateAll = new JButton("Simulate all Matchdays");
        bottomPanel.add(simulateMatchday);
        bottomPanel.add(simulateAll);
        leaguePanel.add(topPanel, BorderLayout.NORTH);
        leaguePanel.add(bottomPanel, BorderLayout.SOUTH);
        leagueWindow.add(leaguePanel);
        DefaultTableModel constructTable = l.constructTable();
        JTable table = new JTable(constructTable);
        JSplitPane centerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(l.getResultsPanel()),table);
        centerPanel.setDividerLocation(1200/2);
        leaguePanel.add(centerPanel, BorderLayout.CENTER);
        leagueWindow.setVisible(true);

        simulateMatchday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (l.getCountMatchday() <= l.getTeams().size()*2-2) {
                    ArrayList<Match> getMatches = l.getMatchdays().get(l.getCountMatchday()-1);
                    for (Match m : getMatches) {
                        m.startMatch();
                        l.getResultsPanel().add(m.getMatchResultsPanel());
                        l.getResultsPanel().add(Box.createVerticalStrut(15));
                    }
                    l.setCountMatchday(l.getCountMatchday() + 1);
                    DefaultTableModel constructTable = l.constructTable();
                    centerPanel.removeAll();
                    centerPanel.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, l.getResultsPanel(), table.add(new JTable(constructTable))));
                }
            }
        });

        simulateAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (true) {
                    if (l.getCountMatchday() <= l.getTeams().size() * 2 - 2) {
                        ArrayList<Match> getMatches = l.getMatchdays().get(l.getCountMatchday()-1);
                        for (Match m : getMatches) {
                            m.startMatch();
                            l.getResultsPanel().add(m.getMatchResultsPanel());
                            l.getResultsPanel().add(Box.createVerticalStrut(15));
                        }
                        DefaultTableModel constructTable = l.constructTable();
                        centerPanel.removeAll();
                        centerPanel.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, l.getResultsPanel(), table.add(new JTable(constructTable))));
                        l.setCountMatchday(l.getCountMatchday() + 1);
                    }
                    else break;
                }
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leagueWindow.dispose();
            }
        });
    }


    public void inventorywindow() {
        JFrame invenwindow = new JFrame();
        invenwindow.setSize(700, 700);
        invenwindow.setTitle("Inventory");
        invenwindow.setVisible(true);


        JPanel Teamlist = new JPanel();
        Teamlist.setLayout(new BoxLayout(Teamlist, BoxLayout.Y_AXIS));
        for (League l : Inventory.leagues) {
            for (Team t : l.getTeams()) {
                if (!(t.getName().equals("Free"))) {
                    Teamlist.add(new JButton(t.getName()));
                    for (Spieler p : t.getPlayers()) {
                        Teamlist.add(new JLabel(p.getName() + " " + p.getPosition() + " " + p.getRating()));
                    }
                }
            }
        }
        JScrollPane Scrollbox = new JScrollPane(Teamlist);
        invenwindow.add(Scrollbox);

        Teamlist.setBackground(Color.green);
    }

}

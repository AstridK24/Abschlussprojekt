import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Fight extends JDialog {
    private Figure player;
    private Figure enemy;
    private JButton fight;
    private JButton runaway;
    private ImageIcon iconEnemy;
    private JLabel labelEnemy;

    private ImageIcon iconClub;
    private JLabel labelClub;

    private JComboBox comboBox;

    private JLabel enemyName;
    private JLabel enemyLive;
    private JLabel enemyPower;

    private JLabel playerName;
    private JLabel playerLive;
    private JLabel playerPower;

    public int retVal = -1;
    private ArrayList<Figure> club;

    //////////////////////////////

    public Fight(Figure player, Figure enemy) {
        this.player = player;
        this.enemy = enemy;
        this.setModal(true);

        setSize(500, 300);
        setTitle("Der große Kampf!!!");
        setLocationRelativeTo(null);//mittig
        fight = new JButton("Angriff!");
        fight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doFight();
            }
        });

        runaway = new JButton("Ich muss mal...");
        runaway.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doRunaway();
            }
        });

        iconEnemy = new ImageIcon(enemy.getImage());
        labelEnemy = new JLabel(iconEnemy);

        iconClub = new ImageIcon(player.getImage());
        labelClub = new JLabel(iconClub);

        club = player.getClubmembers();
        int clubSize = club.size() + 1;

        String[] herosNames = new String[clubSize];
        herosNames[0] = player.getName();
        for (int i = 0; i < club.size(); i++) {
            herosNames[i + 1] = club.get(i).getName();
        }
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.YELLOW);
        //panel.setLayout(new BorderLayout());
        comboBox = new JComboBox(herosNames);
        comboBox.setBackground(Color.GREEN);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doSelect();
            }
        });

        panel.add(labelEnemy, BorderLayout.WEST);
        panel.add(comboBox, BorderLayout.CENTER);
        panel.add(labelClub, BorderLayout.EAST);

        enemyName = new JLabel("Name: " + enemy.getName(), SwingConstants.CENTER);
        //enemyName.setVerticalAlignment(JLabel.CENTER);
        enemyLive = new JLabel("Leben: " + enemy.getLife(), SwingConstants.CENTER);
        enemyPower = new JLabel("Kraft: " + enemy.getPower(), SwingConstants.CENTER);

        playerName = new JLabel("Name: " + player.getName(), SwingConstants.CENTER);
        //playerName.setVerticalAlignment(JLabel.CENTER);
        playerLive = new JLabel("Leben: " + player.getLife(), SwingConstants.CENTER);
        playerPower = new JLabel("Kraft: " + player.getPower(), SwingConstants.CENTER);

        //int size1 = 800;
        //int size2 = 800;

        JPanel panelEnemy = new JPanel(new BorderLayout());
        //panelEnemy.setBackground(Color.CYAN);
        panelEnemy.setBackground(new Color(121, 241, 121));
        panelEnemy.setPreferredSize(new Dimension(200, 150));
        panelEnemy.add(enemyName, BorderLayout.NORTH);
        panelEnemy.add(enemyLive, BorderLayout.CENTER);
        panelEnemy.add(enemyPower, BorderLayout.SOUTH);

        JPanel panelPlayer = new JPanel(new BorderLayout());
        //panelPlayer.setBackground(Color.CYAN);
        panelPlayer.setBackground(new Color(121, 241, 121));
        panelPlayer.setPreferredSize(new Dimension(200, 150));
        panelPlayer.add(playerName, BorderLayout.NORTH);
        panelPlayer.add(playerLive, BorderLayout.CENTER);
        panelPlayer.add(playerPower, BorderLayout.SOUTH);


        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBackground(Color.YELLOW);
        //panel2.setLayout(new BorderLayout());
        panel2.setMinimumSize(new Dimension(100, 150));
        panel2.add(panelEnemy, BorderLayout.WEST);
        panel2.add(new JLabel("vs", SwingConstants.CENTER), BorderLayout.CENTER);//vs ist mittig
        panel2.add(panelPlayer, BorderLayout.EAST);

        JPanel panel3 = new JPanel();
        //panel3.setLayout();
        panel3.add(fight);
        panel3.add(runaway);

        this.add(panel, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);
        this.add(panel3, BorderLayout.SOUTH);

    }
    //////////////////////////////

    /***
     * player exits fight
     */
    public void doRunaway() {
        retVal = -1;
        dispose();
    }

    /***
     * player trys to hit, enemy trys to hit
     */
    public void doFight() {
        Figure good; // spieler oder club

        int selected = comboBox.getSelectedIndex();
        if (selected == 0) { //spieler ist ausgewählt
            good = player;
        } else {
            good = club.get(selected - 1);
        }
        Random random = new Random();
        int sum = good.getPower() + enemy.getPower();
        int value = random.nextInt(sum) + 1;
        if (value <= good.getPower()) {//der gute hat einen treffer gemacht
            enemy.setLife(enemy.getLife() - 1);//dem gegner wird ein herz abgezogen
        } else {//der böse hat einen treffer gemacht
            good.setLife(good.getLife() - 1);//der gute verliert ein herz
        }
        playerLive.setText("Leben:" + good.getLife());
        enemyLive.setText("Leben:" + enemy.getLife());
        if (enemy.getLife() < 1) {//gegner ist ein würstchen und hat verloren
            retVal = 1;
            enemy.setVisible(false);
            dispose();
        }
        if (good.getLife() < 1) {//spieler oder einer vom club ist gestorben
            if (good == player) {
                retVal = 0;
                dispose();
            } else {
                player.removeClubMember(good.getName());
                comboBox.removeItemAt(selected);
            }
        }
    }

    /***
     * select the superhero(es)/player for the fight
     */
    public void doSelect() { //figur auswählen die kämpfen soll
        int selected = comboBox.getSelectedIndex();
        if (selected == 0) { //spieler ist ausgewählt
            labelClub.setIcon(new ImageIcon(player.getImage()));
            playerName.setText("Name:" + player.getName());
            playerLive.setText("Leben:" + player.getLife());
            playerPower.setText("Kraft:" + player.getPower());
        } else { //einer aus dem Club soll kämpfen
            labelClub.setIcon(new ImageIcon(club.get(selected - 1).getImage()));
            playerName.setText("Name:" + club.get(selected - 1).getName());
            playerLive.setText("Leben:" + club.get(selected - 1).getLife());
            playerPower.setText("Kraft:" + club.get(selected - 1).getPower());
        }
        repaint();
    }
}

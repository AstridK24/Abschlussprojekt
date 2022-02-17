import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board extends JPanel /*implements KeyListener*/ { //spielfeld

    //private ArrayList<String> boardData = new ArrayList<>();//für level zum auslesen
    private Level level;
    private Directions movement = Directions.NOTHING;//bewegung vom spieler
    private Timer timer;//damit das spiel läuft(hauptschleife)
    private int count = 0;
    Data d = new Data();//gemeinsame daten

    /////////////////////////////////////////////
    public Board(String playerSelected, String playerName) {
        setBackground(Color.GREEN);//hintergrundfarbe


        loadTilesData();//lädt kacheln
        d.playerName = playerName;
        d.width = 15;//weite 25*zellenweite
        d.height = 10;//10*zellenhöhe
        d.cellHeight = 64;//zellenhöhe in px
        d.cellWidth = 64;//zellenweite in px
        d.playerSelected  = playerSelected;
        d.inventory = new Inventory(); // klasse zum anzeigen vom backpack
        d.club = new Club();
        d.status = new Status();
        d.time = 1000;
        d.points = 0;
        level = new Level("file/Level1", d);

        setLayout(new BorderLayout());
        JPanel backGround = new JPanel();
        backGround.setMinimumSize(new Dimension(d.width*d.cellWidth,d.height*d.cellHeight));
        JPanel east = new JPanel();
        east.setLayout(new BorderLayout());
        east.add(d.status,BorderLayout.NORTH);
        east.add(d.inventory,BorderLayout.CENTER);
        east.add(d.club,BorderLayout.SOUTH);


        add(backGround,BorderLayout.CENTER);//add(d.inventory,BorderLayout.EAST);add(d.club,BorderLayout.SOUTH);
        add(east,BorderLayout.EAST);
        //addKeyListener(this);//keylistener einbinden
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());//tastatur einbinden
        timer = new Timer(200, new ActionListener() {//aktion alle 200ms
            @Override
            public void actionPerformed(ActionEvent e) {

                if (isVisible()) {
                    count++;
                    if (count >= 5) {
                        count = 0;
                        d.time--;
                    }
                    boolean gameOver = false;
                    if ((d.time < 1)) {
                        gameOver = true;
                    }
                    if (level.noLife()) {
                        gameOver = true;
                    }
                    if (!gameOver) {

                        level.movePlayer(movement);//spieler bewegen
                        movement = Directions.NOTHING;//wieder zurücksetzen sonst läuft die figur ständig
                        level.moveFigures();//alle figuren bewegen
                        String nextLevel = level.nextLevel;
                        if (!nextLevel.isEmpty()) {
                            level = new Level(nextLevel, d);
                        }
                    } else {
                        setHighscore();
                        setVisible(false);
                    }
                    repaint();//neu zeichnen
                }
            }
        });
        timer.start();//timer starten
    }

    /////////////////////////////////////////////////

    private class MyDispatcher implements KeyEventDispatcher {//keylistener funktioniert nicht deswegen keyeventdispatcher-wird vom keyfocusmanager aufgerufen
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {

            if (e.getID() == KeyEvent.KEY_PRESSED) // Taste wurde gedrückt (und noch nicht ausgelassen)
            {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        movement = Directions.LEFT;
                       // System.out.println("links");
                        break;
                    case KeyEvent.VK_RIGHT:
                      //  System.out.println("rechts");
                        movement = Directions.RIGHT;
                        break;
                    case KeyEvent.VK_UP:
                       // System.out.println("rauf");
                        movement = Directions.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        movement = Directions.DOWN;
                       // System.out.println("runter");
                        break;
                    default:
                        movement = Directions.NOTHING;
                       // System.out.println("nichts");
                }
            }
            return false;
        }
    }


    /***
     *
     * @param g graphics handler
     * @return void
     */
    public void paint(Graphics g) {//zeichne spielbrett
        //super.paint(g);
        super.paint(g);
        Image img = level.createBoard();//zeichne spielbrett in bild ein
        g.drawImage(img, 0, 0, this);//zeigt bild bei 0_0 im jframe an
    }

    /***
     *
     * @return size of board
     */
    @Override
    public Dimension getPreferredSize() { // gib größe des spielfeldes zurück

        int width = d.cellWidth *d.width + d.inventory.getWidth(); // breite vom spielfeldbild + breite vom inventar
        int height = d.cellHeight*d.height; // höhe des spielfeldes
        return new Dimension(width, height);
    }

    private void loadTilesData() {//level objekte namen geben und nach eigenschafeten festlegen
        String path = "img/map/";
        String pathRosa = "img/mapRosa/";
        String pathBlau = "img/mapBlau/";

        d.tiles.put("w", new Tile(path + "steinweg.png", true));
        d.tiles.put("W", new Tile(pathRosa + "wegrosa.png", true));
        d.tiles.put("1", new Tile(pathBlau + "wegblau.png", true));
        d.tiles.put("x", new Tile(path + "wegStein.png", true));
        d.tiles.put("y", new Tile(path + "wiese2.png", true));
        d.tiles.put("z", new Tile(path + "wiese3.png", true));
        d.tiles.put("Y", new Tile(pathRosa + "wieserosa.png", true));
        d.tiles.put("Z", new Tile(pathRosa + "wieserosa2.png", true));
        d.tiles.put("2", new Tile(pathBlau + "wieseblau.png", true));
        d.tiles.put("3", new Tile(pathBlau + "wieseblau2.png", true));

        d.tiles.put("h", new Tile(path + "strauch3.png", false));
        d.tiles.put("H", new Tile(pathRosa + "baumrosa.png", false));
        d.tiles.put("K", new Tile(pathRosa + "kugellila.png", false));
        d.tiles.put("4", new Tile(pathBlau + "baumblau.png", false));
        d.tiles.put("5", new Tile(pathBlau + "kugelblau.png", false));
        d.tiles.put("f", new Tile(path + "felsen3.png", false));
        d.tiles.put("F", new Tile(pathRosa + "felsenlila.png", false));
        d.tiles.put("S", new Tile(pathRosa + "stachellila.png", false));
        d.tiles.put("T", new Tile(pathRosa + "wasserrosa.png", false));
        d.tiles.put("6", new Tile(pathBlau + "felsenblau.png", false));
        d.tiles.put("7", new Tile(pathBlau + "stachelblau.png", false));
        d.tiles.put("8", new Tile(pathBlau + "wasserblau.png", false));

        d.tiles.put("a", new Tile(path + "h1.png", false));
        d.tiles.put("b", new Tile(path + "h2.png", false));
        d.tiles.put("c", new Tile(path + "h3.png", false));
        d.tiles.put("d", new Tile(path + "h4.png", false));
    }

    private void setHighscore() {
        ArrayList<String>highscores = new ArrayList<>();
        String fileName = "data/highscore.txt";
        String highscoreString = d.points+" "+d.playerName;
        File file;

        try {
            file = new File(fileName);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                highscores.add(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
           highscores.clear();
        }

        if (!highscores.isEmpty()) {
            highscores.remove(highscores.size()-1);
        }
        int index = -1;
        int i = 0;
        while ((i < highscores.size())&& (index < 0)){
            String[] subStrings = highscores.get(i).split(" ");
            if (subStrings.length == 2) {
                int cur = Integer.parseInt(subStrings[0]);
                if (cur < d.points) {
                   index = i;
                }
            }
            i++;
        }

        if (index < 0) {
            highscores.add(highscoreString);
        } else {
            highscores.add(index,highscoreString);
        }

    /*    file = new File(fileName);
        file.delete();*/

        file = new File(fileName);
        try {
            FileWriter f2 = new FileWriter(file, false);

            for (int j = 0; j < 10; j++) {
                if (highscores.size() > j) {
                    f2.write(highscores.get(j)+"\n");
                }
            }
            f2.write(highscoreString+"\n");

            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {//tastatureingabe
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                System.out.println("links");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("rechts");
                break;
            case KeyEvent.VK_UP:
                System.out.println("rauf");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("runter");
                break;
            default:
                System.out.println("nichts");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

*/
}


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board extends JPanel /*implements KeyListener*/ { //spielfeld

    //private ArrayList<String> boardData = new ArrayList<>();//für level zum auslesen
    private Level level;
    private Directions movement = Directions.nothing;//bewegung vom spieler
    private Timer timer;//damit das spiel läuft(hauptschleife)
    private int count = 0;
    Data d = new Data();//gemeinsame daten

    /////////////////////////////////////////////
    public Board(String playerSelected) {
        setBackground(Color.GREEN);//hintergrundfarbe


        LoadTilesData();//lädt kacheln

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
                count++;
                if (count >=5){
                    count = 0;
                    d.time--;
                }
                level.MovePlayer(movement);//spieler bewegen
                movement = Directions.nothing;//wieder zurücksetzen sonst läuft die figur ständig
                level.MoveFigures();//alle figuren bewegen

                repaint();//neu zeichnen
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
                        movement = Directions.left;
                       // System.out.println("links");
                        break;
                    case KeyEvent.VK_RIGHT:
                      //  System.out.println("rechts");
                        movement = Directions.right;
                        break;
                    case KeyEvent.VK_UP:
                       // System.out.println("rauf");
                        movement = Directions.up;
                        break;
                    case KeyEvent.VK_DOWN:
                        movement = Directions.down;
                       // System.out.println("runter");
                        break;
                    default:
                        movement = Directions.nothing;
                       // System.out.println("nichts");
                }
            }
            return false;
        }
    }

    public void paint(Graphics g) {//zeichne spielbrett
        //super.paint(g);
        super.paint(g);
        Image img = level.CreateBoard();//zeichne spielbrett in bild ein
        g.drawImage(img, 0, 0, this);//zeigt bild bei 0_0 im jframe an
    }

    @Override
    public Dimension getPreferredSize() { // gib größe des spielfeldes zurück

        int width = d.cellWidth *d.width + d.inventory.getWidth(); // breite vom spielfeldbild + breite vom inventar
        int height = d.cellHeight*d.height; // höhe des spielfeldes
        return new Dimension(width, height);
    }

    private void LoadTilesData() {//level objekte namen geben und nach eigenschafeten festlegen
        String path = "img/map/";
        String pathRosa = "img/mapRosa/";
        String pathBlau = "img/mapBlau/";

        d.tiles.put("w", new Tile(path + "steinweg.png", true, false));
        d.tiles.put("W", new Tile(pathRosa + "wegrosa.png", true, false));
        d.tiles.put("1", new Tile(pathBlau + "wegblau.png", true, false));
        d.tiles.put("x", new Tile(path + "wegStein.png", true, false));
        d.tiles.put("y", new Tile(path + "wiese2.png", true, false));
        d.tiles.put("Y", new Tile(pathRosa + "wieserosa.png", true, false));
        d.tiles.put("Z", new Tile(pathRosa + "wieserosa2.png", true, false));
        d.tiles.put("2", new Tile(pathBlau + "wieseblau.png", true, false));
        d.tiles.put("3", new Tile(pathBlau + "wieseblau2.png", true, false));


        d.tiles.put("h", new Tile(path + "strauch3.png", false, false));
        d.tiles.put("H", new Tile(pathRosa + "baumrosa.png", false, false));
        d.tiles.put("K", new Tile(pathRosa + "kugellila.png", false, false));
        d.tiles.put("4", new Tile(pathBlau + "baumblau.png", false, false));
        d.tiles.put("5", new Tile(pathBlau + "kugelblau.png", false, false));
        d.tiles.put("f", new Tile(path + "felsen3.png", false, false));
        d.tiles.put("F", new Tile(pathRosa + "felsenlila.png", false, false));
        d.tiles.put("S", new Tile(pathRosa + "stachellila.png", false, false));
        d.tiles.put("T", new Tile(pathRosa + "wasserrosa.png", false, false));
        d.tiles.put("6", new Tile(pathBlau + "felsenblau.png", false, false));
        d.tiles.put("7", new Tile(pathBlau + "stachelblau.png", false, false));
        d.tiles.put("8", new Tile(pathBlau + "wasserblau.png", false, false));


        d.tiles.put("a", new Tile(path + "h1.png", false, false));
        d.tiles.put("b", new Tile(path + "h2.png", false, false));
        d.tiles.put("c", new Tile(path + "h3.png", false, false));
        d.tiles.put("d", new Tile(path + "h4.png", false, false));

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


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

    Data d = new Data();//gemeinsame daten

    /////////////////////////////////////////////
    public Board(String playerSelected) {
        setBackground(Color.GREEN);//hintergrundfarbe
        //setFocusable(true);//aktives fenster - wahr
        //requestFocusInWindow();//verlangt fokus


        LoadTilesData();//lädt kacheln

        d.width = 15;//weite 25*zellenweite
        d.height = 10;//10*zellenhöhe
        d.cellHeight = 64;//zellenhöhe in px
        d.cellWidth = 64;//zellenweite in px
        d.playerSelected  = playerSelected;
        d.inventory = new Inventory(); // klasse zum anzeigen vom backpack
        level = new Level("file/Level1", d);

        setLayout(new BorderLayout());
        JPanel backGround = new JPanel();
        backGround.setMinimumSize(new Dimension(d.width*d.cellWidth,d.height*d.cellHeight));
        add(backGround,BorderLayout.CENTER);add(d.inventory,BorderLayout.EAST);
        //addKeyListener(this);//keylistener einbinden
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());//tastatur einbinden
        timer = new Timer(200, new ActionListener() {//aktion alle 200ms
            @Override
            public void actionPerformed(ActionEvent e) {

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
        d.tiles.put("a", new Tile(path + "wasser.png", false, true));

        d.tiles.put("h", new Tile(path + "strauch3.png", false, false));

        d.tiles.put("f", new Tile(path + "loch.png", false, true));
        d.tiles.put("p", new Tile(path + "feuer.png", false, true));
        d.tiles.put("n", new Tile(path + "stacheln.png", false, true));

        d.tiles.put("w", new Tile(path + "steinweg.png", true, false));

        d.tiles.put("c", new Tile(path + "h1.png", false, false));
        d.tiles.put("d", new Tile(path + "h2.png", false, false));
        d.tiles.put("e", new Tile(path + "h3.png", false, false));
        d.tiles.put("g", new Tile(path + "h4.png", false, false));
        d.tiles.put("t", new Tile(path + "tuer.png", false, true));
        d.tiles.put("i", new Tile(path + "kiste.png", false, false));
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


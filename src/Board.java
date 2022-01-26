import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board extends JPanel implements KeyListener { //spielfeld

    private ArrayList<String> boardData = new ArrayList<>();//für level zum auslesen
    private Level level;

    Data d = new Data();

    /////////////////////////////////////////////
    public Board() {
        setBackground(Color.GREEN);//hintergrundfarbe
        setFocusable(true);//aktives fenster - wahr

        LoadTilesData();//lädt kacheln

        d.width = 15;//weite 25*zellenweite
        d.height = 8;//10*zellenhöhe
        d.cellHeight = 64;//zellenhöhe in px
        d.cellWidth = 64;//zellenweite in px

        level = new Level("file/Level1", d);


    }

    /////////////////////////////////////////////////
    public void paint(Graphics g) {//zeichne spielbrett
        //super.paint(g);
        super.paint(g);
        Image img = level.CreateBoard();//zeichne spielbrett in bild ein
        g.drawImage(img, 0, 0, this);//zeigt bild bei 0_0 im jframe an
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


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

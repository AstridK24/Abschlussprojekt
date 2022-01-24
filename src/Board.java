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

    private int width;//spielflächenbreitebreite
    private int height;//spielflächenhöhe
    private int cellWidth;//zellenbreite
    private int cellHeight;//zellenhöhe

    private HashMap<String, Tile> tiles = new HashMap<String, Tile>();//weg usw - kacheln

    /////////////////////////////////////////////
    public Board() {

        LoadTilesData();//lädt kacheln


        width = 15;//weite 25*zellenweite
        height = 8;//10*zellenhöhe
        cellHeight = 64;//zellenhöhe in px
        cellWidth = 64;//zellenweite in px
        setBackground(Color.GREEN);//hintergrundfarbe

        setFocusable(true);//aktives fenster - wahr

    }
/////////////////////////////////////////////////



    private void LoadTilesData() {//level objekte namen geben und nach eigenschafeten festlegen
        String path = "img/map/";
        tiles.put("a",new Tile(path+"wasser.png",false,true));

        tiles.put("h",new Tile(path+"strauch3.png",false,false));

        tiles.put("f",new Tile(path+"loch.png",false,true));

        tiles.put("w",new Tile(path+"steinweg.png",true,false));

        tiles.put("c",new Tile(path+"h1.png",false,false));
        tiles.put("d",new Tile(path+"h2.png",false,false));
        tiles.put("e",new Tile(path+"h3.png",false,false));
        tiles.put("g",new Tile(path+"h4.png",false,false));
        tiles.put("t",new Tile(path+"tuer.png",false,true));
        tiles.put("i",new Tile(path+"kiste.png",false,false));

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

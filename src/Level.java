import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {

    private String levelPath;//pfad vom level
    private Data d;//gemeinsame daten für level und board
    private int plusX = 0;//verschiebung des gesammten angezeigten bildes x
    private int plusY = 0;//y
    private ArrayList<String> boardData = new ArrayList<>();//für level zum auslesen


    ////////////////////////////////////////////////////
    public Level(String levelPath, Data d) {
        this.levelPath = levelPath;
        this.d = d;
        Load();//methode laden
    }
    ////////////////////////////////////////////////////

    private void Load() {//laden vom levelfile
        File file = new File(levelPath + "/data.txt");//levelpfad und inhalt
        boardData.clear();//brett löschen
        Scanner scan = null;//zum einlesen vom file-level
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scan.hasNext()) {
            String curLine = scan.nextLine();
            boardData.add(curLine);
        }
    }

    public BufferedImage CreateBoard() {
        BufferedImage bufImg = new BufferedImage(d.cellWidth * d.width, d.cellHeight * d.height, BufferedImage.TYPE_INT_ARGB);
        //bufimg = zellenweite x weite = gesamtbreite vom level
        //zellenhöhe x höhe = gesamthöhe vom level
        //type int argb = farbbild mit fähigkeit transparenten hintergrund
        Graphics g = bufImg.getGraphics();//für bild in bild

        //durchlaufe das gesamte spielbrett: - zeichne hintergrund
        for (int x = 0; x < d.width; x++) {//0 bis max breite
            for (int y = 0; y < d.height; y++) {//0 bis max höhe
                String curCell = GetLevelData(x + plusX, y + plusY);//curcell bekommt die id der kachel an der koorrdinate x,y

                BufferedImage cellImg = GetCellImage(curCell);//cellimg= bild der curcell
                g.drawImage(cellImg, x * d.cellWidth, y * d.cellHeight, null);
                //zeichnet cellimg an der aktuellen pos ein
            }
        }
        return bufImg;
    }

    private BufferedImage GetCellImage(String id) {//liefert kachelimg zur id-id

        BufferedImage img;//rückgabebild

        if (d.tiles.containsKey(id)) {//gibt es id in der kachelmap
            img = d.tiles.get(id).GetImage();//img wird zum img von der id in der kachelmap
        } else {//gibt es die id nicht - mache schwarzes bild
            img = new BufferedImage(d.cellWidth, d.cellHeight, BufferedImage.TYPE_INT_ARGB);//höhe und breite der zelle
            ///////////////////////////////////////////
            Graphics g = img.getGraphics();
            g.setColor(Color.black);//schwarze zelle
            g.fillRect(0, 0, d.cellWidth, d.cellHeight);
        }

        img = ResizeImage(img); //img.getSubimage(0,0,cellWidth,cellHeight);
        return img;
    }

    private BufferedImage ResizeImage(Image img) {//bildgröße anpassen
        BufferedImage resImg = new BufferedImage(d.cellWidth, d.cellHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resImg.createGraphics();
        g.drawImage(img, 0, 0, d.cellWidth, d.cellHeight, null);
        g.dispose();
        return resImg;
    }

    private String GetLevelData(int x, int y) { //holt die gewünschte zelle aus den leveldaten
        String curCell = "";//curcell am anfang leer
        if ((boardData.size() > y) && (y >= 0)) {//wenn größe vom level grösser als höhe ist
            String curLine = boardData.get(y);//größe vom level soll max höhe bekommen
            if ((curLine.length() > x) && (x >= 0)) {//wenn höhe vom level größer als höhe ist
                curCell = Character.toString(curLine.charAt(x));//????
            }
        }
        return curCell;
    }
}

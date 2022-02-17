import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {//kacheln
    private BufferedImage img;
    private int width;//breite der zelle
    private int height;//höhe der zelle
    private boolean walkable;//begehbar

/////////////////////////////////////////////////////////////////////
    public Tile(String fileName, boolean walkable) {//konstruktor
        width = 64;//breite
        height = 64;//höhe
        img = readImage(fileName);//bildpfad
        this.walkable = walkable;//begehbar
    }
//////////////////////////////////////////////////////////////////

    private BufferedImage readImage(String fileName) {//holt das bild
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) { //datei nicht gefunden - erzeuge schwarzes rechteck
            img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            g.setColor(Color.black);//zelle schwarz
            g.fillRect(0,0,width,height);//größe des rechtecks-zelle
        }
        return img;
    }

    /***
     *
     * @return get image of tile
     */
    public BufferedImage getImage() {
        return img;
    }

    /***
     *
     * @return true if tile is walkable
     */
    public boolean isWalkable() {//begebar
        return walkable;
    }

    /***
     *
     * @param walkable set true if tile is walkable
     */
    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

}


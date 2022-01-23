import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {//kacheln
    private BufferedImage img;
    private int width;//breite der zelle
    private int height;//höhe der zelle
    private boolean walkable;//begehbar
    private boolean dangerous;//gefährlich

/////////////////////////////////////////////////////////////////////
    public Tile(String fileName, boolean walkable, boolean dangerous) {//konstruktor

        width = 64;//breite
        height = 64;//höhe
        img = ReadImage(fileName);//bildpfad
        this.walkable = walkable;//begehbar
        this.dangerous = dangerous;//gefährlich
    }
//////////////////////////////////////////////////////////////////

    private BufferedImage ReadImage(String fileName) {//holt das bild
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            g.setColor(Color.black);//zelle schwarz
            g.fillRect(0,0,width,height);//größe des rechtecks-zelle
        }
        return img;
    }

    public BufferedImage GetImage() {
        return img;
    }

    public boolean isWalkable() {//begebar
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isDangerous() {//gefährlich
        return dangerous;
    }

    public void setDangerous(boolean dangerous) {
        this.dangerous = dangerous;
    }


}

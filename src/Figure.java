import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Figure {
    private HashMap<String, BufferedImage> imgs = new HashMap<String, BufferedImage>();
    private int width;//breite der figur
    private int height;//höhe der figur


    //////////////////////////////////

    public Figure() {
        width = 64;//64px breit
        height = 64;//64px hoch

    }
    /////////////////////////////////

    private BufferedImage ReadImage(String fileName) {//holt das bild
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            g.setColor(Color.black);//wenn kein bild dann schwarzes feld
            g.fillRect(0, 0, width, height);
        }
        return img;
    }

    public void LoadImages(String name) {
        imgs.clear();
        String direction = "hlrv";
        for (int i = 0; i < direction.length(); i++) {
            for (int j = 1; j < 4; j++) {
                String id = Character.toString(direction.charAt(i)) + j;
                String fileName = name + " " + id + ".png";
                BufferedImage curImg = ReadImage(fileName);
                imgs.put(id, curImg);
            }
        }
    }
}

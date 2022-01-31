import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Item {
    private int width = 64;//px
    private int height = 64;//px
    private int x;//pos x
    private int y;// pos y
    private String fileName;//datei mit item daten
    private boolean isVisible = true;//sichtbar
    private boolean isCollectable = true;//aufsammeln
    private String name = "";
    private Image image;


    ////////////////////////////////7
    public Item(int x, int y, String fileName) {
        this.x = x;
        this.y = y;
        this.fileName = fileName;
        Load();
    }
////////////////////////////////

    private void Load() {//laden
        File file = new File(fileName);// datei mit itemdaten öffnen
        Scanner scan = null;//zum einlesen vom z.b. apfel.txt
        try {
            scan = new Scanner(file);//zum fil auslesen
            while (scan.hasNext()) {
                String curLine = scan.nextLine();
                if (!curLine.isEmpty()) {//wenn leere zeile kommt, dann spliten
                    String[] subStrings = curLine.split("=");//bei =
                    if (subStrings.length == 2) {//kann nur 2 sein
                        String key = subStrings[0].toLowerCase().trim();
                        String value = subStrings[1].trim();

                        switch (key) {
                            case "name":
                                name = value;
                                break;
                            case "image":
                                image = ReadImage(value);
                                break;
                            case "visible":
                                isVisible = value.equals("1");
                                break;
                        }
                    }
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage ReadImage(String fileName) {//holt das bild
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = img.getGraphics();
            g.setColor(Color.black);//zelle schwarz
            g.fillRect(0, 0, width, height);//größe des rechtecks-item
        }
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isCollectable() {
        return isCollectable;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}

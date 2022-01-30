import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Figure {
    private HashMap<String, BufferedImage> imgs = new HashMap<String, BufferedImage>();
    private int width;//breite der figur
    private int height;//höhe der figur
    private String fileName;//name der bilddatei
    private int x = 0;//position breite start
    private int y = 0;//position höhe start
    private String name;
    private boolean visible = true;
    private int live = 1;
    private boolean bad = false;
    private boolean moveable = true;
    private int xLo = 0;
    private int yLo = 0;
    private int xRu = 100;
    private int yRu = 100;
    private Directions direction = Directions.down;
    private int step = 2;

    //////////////////////////////////

    public Figure(int x, int y, String fileName) {
        width = 64;//64px breit
        height = 64;//64px hoch
        this.x = x;
        this.y = y;
        this.fileName = fileName;

        Load();
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
            g.fillRect(0, 0, width, height);//größe vom rechteck-figur
        }
        return img;
    }

    public void LoadImages(String name) {
        imgs.clear();
        String direction = "hlrv";//richtungen der figur
        for (int i = 0; i < direction.length(); i++) {
            for (int j = 1; j < 4; j++) {
                String id = Character.toString(direction.charAt(i)) + j;
                String fileName = name + " " + id + ".png";
                BufferedImage curImg = ReadImage(fileName);
                imgs.put(id, curImg);
            }
        }
    }

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
                                LoadImages(value);
                                break;
                            case "visible":
                                visible = value.equals("1");
                                break;
                            case "live":
                                live  = Integer.parseInt(value);
                                break;
                            case "bad":
                                bad = value.equals("1");
                                break;
                            case "moveable":
                                moveable =value.equals("1");
                                break;
                            case "xlo":
                                xLo = Integer.parseInt(value);
                                break;
                            case "ylo":
                                yLo = Integer.parseInt(value);
                                break;
                            case "xru":
                                xRu = Integer.parseInt(value);
                                break;
                            case "yru":
                                yRu = Integer.parseInt(value);
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

    public Image getImage() {
        String id = "v1";
        switch (direction) {
            case left:
                id = "l" + step;
                break;
            case right:
                id = "r" + step;
                break;
            case up:
                id = "h" + step;
                break;
            case down:
                id = "v" + step;
                break;
        }
        Image curImg = imgs.get(id);



        return curImg;
    }

    public void MakeStep() {
        step = step + 1;
        if (step > 3) {
            step = 1;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getxLo() {
        return xLo;
    }

    public int getyLo() {
        return yLo;
    }

    public int getxRu() {
        return xRu;
    }

    public int getyRu() {
        return yRu;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }
}

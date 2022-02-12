import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Item {
    private int width = 64;//px
    private int height = 64;//px
    private int x;//pos x
    private int y;// pos y
    private String fileName;//datei mit item daten
    private boolean isVisible = true;//sichtbar
    private boolean isCollectable = true;//aufsammeln

    private int telex = -1;
    private int teley = -1;
    private int gold = 0;//kohle
    private int live = 0;//energie - herzen
    private int power = 0;//kraft beim kampf
    private int time = 0;//zusätzliche zeit
    //private boolean walkable2 = true;//item begehbar

    private String name = "";
    private String need = "";
    private String level = "";

    private Image image;
    private ArrayList<String> vips = new ArrayList<>();


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
                            case "collectable":
                                isCollectable = value.equals("1");
                                break;
                            case "tele":
                                String[] coordinates = value.split(",");//bei ,
                                if (coordinates.length == 2) {
                                    telex = Integer.parseInt(coordinates[0].trim());
                                    teley = Integer.parseInt(coordinates[1].trim());
                                }
                                break;
                            case "vip":
                                vips.add(value);
                                break;
                            case "gold":
                                gold = Integer.parseInt(value);
                                break;
                            case "live":
                                live = Integer.parseInt(value);
                                break;
                            case "power":
                                power = Integer.parseInt(value);
                                break;
                            case "time":
                                time = Integer.parseInt(value);
                                break;
                            case "need":
                                need = value;
                                break;
                            case "level":
                                level = value;
                                break;
                           /* case "walkable2":
                                walkable2 = value.equals("1");
                                break;*/

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

    public String getName() {
        return name;
    }

    public int getTelex() {
        return telex;
    }

    public int getTeley() {
        return teley;
    }

    public int getGold() {
        return gold;
    }

    public int getLive() {
        return live;
    }

    public int getPower() {
        return power;
    }

    public int getTime() {
        return time;
    }

    public String getNeed() {
        return need;
    }

    public String getLevel() {
        return level;
    }

    public ArrayList<String> getVips() {
        return vips;
    }

    public boolean isVisible() {
        return isVisible;
    }

    /*public boolean isWalkable2() {
        return isWalkable2();
    }*/

    public boolean isCollectable() {
        return isCollectable;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}

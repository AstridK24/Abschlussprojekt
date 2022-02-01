import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
    private boolean visible = true;//sichtbar
    private int live = 1;//energie - herzen
    private boolean bad = false;//gut oder böse
    private boolean moveable = true;//beweglich
    private int xLo = 0;//poslio
    private int yLo = 0;//poslio
    private int xRu = 100;//posreu
    private int yRu = 100;//posreu
    private Directions direction = Directions.down;
    private int step = 2;//li mitte rechts

    private String[] text = {"",""}; // texte die die figur sagen kann

    private String[] option = {"","","",""};  // antwortmöglichkeiten

    private String need = "";//braucht
    private String give = "";//gibt
    private boolean vanish = false;//verschwindet
    private int strength = 1;//stärke
    private int gold = 0;//kohle
    private boolean follow = false;


    private ArrayList<Item> backpack = new ArrayList<>(); // rucksack für items
    private int backpackMax = 5; // maximale anzahl der items die im rucksack sein dürfen

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
                            case "visible"://sichtbar
                                visible = value.equals("1");
                                break;
                            case "live"://energieherzen
                                live  = Integer.parseInt(value);
                                break;
                            case "bad"://gut - böse
                                bad = value.equals("1");
                                break;
                            case "moveable"://beweglich
                                moveable =value.equals("1");
                                break;
                            case "xlo"://breite li o
                                xLo = Integer.parseInt(value);
                                break;
                            case "ylo"://höhe li o
                                yLo = Integer.parseInt(value);
                                break;
                            case "xru"://breite re u
                                xRu = Integer.parseInt(value);
                                break;
                            case "yru"://höhe re u
                                yRu = Integer.parseInt(value);
                                break;
                            case "text1":
                                text[0] = value;
                                break;
                            case "text2":
                                text[1] = value;
                                break;
                            case "option1"://nein
                                option[0] = value;
                                break;
                            case "option2"://nein
                                option[1] = value;
                                break;
                            case "option3"://nein
                                option[2] = value;
                                break;
                            case "option4"://nein
                                option[3] = value;
                                break;
                            case "need"://tauschobjekt apfel
                                need = value;
                                break;
                            case "give"://tauschobjekt schlüssel
                                give = value;
                                break;
                            case "vanish"://verschwindet
                                vanish = value.equals("1");
                                break;
                            case "strength"://stärke
                                strength = Integer.parseInt(value);
                                break;
                            case "gold"://hat geld
                                gold = Integer.parseInt(value);
                                break;
                            case "follow"://hero folgt
                                follow = value.equals("1");
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

    public boolean AddIdem(Item item){ // items in rucksack geben, liefert false wenn rucksack schon voll ist
        boolean retVal = false;
        
        if (backpack.size() < backpackMax){
            retVal = true;
            backpack.add(item);
        }

        return retVal;
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

    public String[] getTexts() {
        return text;
    }

    public String[] getOptions() {
        return option;
    }

    public String getNeed() {
        return need;
    }

    public String getName() {
        return name;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public ArrayList<Item> getBackpack() {
        return backpack;
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

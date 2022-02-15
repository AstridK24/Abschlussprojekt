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
    private ArrayList<Item> backpack = new ArrayList<>(); // rucksack für items
    private ArrayList<Figure> clubmembers = new ArrayList<>();//alles figuren die mit dem spieler im club sind


    private String fileName;//name der bilddatei
    private String name;//name der figur
    private String need = "";//braucht
    private String give = "";//gibt
    private String[] text = {"", ""};//texte die die figur sagen kann
    private String[] option = {"", "", "", ""};//antwortmöglichkeiten

    private int width;//breite der figur
    private int height;//höhe der figur
    private int x = 0;//position breite start
    private int y = 0;//position höhe start
    private int step = 2;//li mitte rechts
    private int xLo = 0;//poslio
    private int yLo = 0;//poslio
    private int xRu = 100;//posreu
    private int yRu = 100;//posreu
    private int backpackMax = 5; // maximale anzahl der items die im rucksack sein dürfen
    private int clubmembersMax = 4; // maximale anzahl der figuren die dem spieler im club folgen können
    private int gold = 0;//kohle
    private int life = 1;//energie - herzen
    private int power = 1;//kraft beim kampf

    private boolean vanish = false;//verschwindet
    private boolean visible = true;//sichtbar
    private boolean bad = false;//gut oder böse
    private boolean moveable = true;//beweglich
    private boolean following = false;//figur kann zum club beitreten

    private Directions direction = Directions.DOWN;//richtung in die die figur gerade schaut

    //////////////////////////////////
    public Figure(int x, int y, String fileName) {
        width = 64;//64px breit
        height = 64;//64px hoch
        this.x = x;
        this.y = y;
        this.fileName = fileName;

        load();
    }
    /////////////////////////////////

    private BufferedImage readImage(String fileName) {//holt das bild
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

    /***
     *
     * @param name of file of figure without direction tag
     * load figure images with directions
     */
    public void loadImages(String name) {
        imgs.clear();
        String direction = "hlrv";//richtungen der figur
        for (int i = 0; i < direction.length(); i++) {
            for (int j = 1; j < 4; j++) {
                String id = Character.toString(direction.charAt(i)) + j;
                String fileName = name + " " + id + ".png";
                BufferedImage curImg = readImage(fileName);
                imgs.put(id, curImg);
            }
        }
    }

    private void load() {//laden
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
                                loadImages(value);
                                break;
                            case "visible"://sichtbar
                                visible = value.equals("1");
                                break;
                            case "live"://energieherzen
                                life = Integer.parseInt(value);
                                break;
                            case "bad"://gut - böse
                                bad = value.equals("1");
                                break;
                            case "moveable"://beweglich
                                moveable = value.equals("1");
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
                            case "power"://stärke
                                power = Integer.parseInt(value);
                                break;
                            case "gold"://hat geld
                                gold = Integer.parseInt(value);
                                break;
                            case "follow"://hero folgt
                                following = value.equals("1");
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

    /***
     * clears all clubmembers from list
     */
    public void clearClubmembers() {
        clubmembers.clear();
    }

    /***
     * clears all items from list
     */
    public void clearBackpack() {
        backpack.clear();
    }

    /***
     *
     * @return image depending on current figure direction
     * returns image depending on current figure direction v1-3, h1-3, l1-3, r1-3
     */
    public Image getImage() {
        String id = "v1";
        switch (direction) {
            case LEFT:
                id = "l" + step;
                break;
            case RIGHT:
                id = "r" + step;
                break;
            case UP:
                id = "h" + step;
                break;
            case DOWN:
                id = "v" + step;
                break;
        }
        Image curImg = imgs.get(id);


        return curImg;
    }

    /***
     * animates figure
     */
    public void makeStep() {
        step = step + 1;
        if (step > 3) {
            step = 1;
        }
    }

    /***
     *
     * @param item adds item to list
     * @return true if backpack was not full
     */
    public boolean addItem(Item item) { // items in rucksack geben, liefert false wenn rucksack schon voll ist
        boolean retVal = false;

        if (backpack.size() < backpackMax) {
            retVal = true;
            backpack.add(item);
        }

        return retVal;
    }

    /***
     *
     * @param itemName removes item from backpack
     * @return true if item was found and removed
     */
    public boolean removeItem(String itemName) {//item aus rucksack löschen
        boolean retVal = false;
        int index = hasItem(itemName);
        if (index > -1) {
            backpack.remove(index);
        }
        return retVal;
    }

    /***
     *
     * @param figure adds figure to list
     * @return true if club was not full
     */
    public boolean addClubMember(Figure figure) { // items in rucksack geben, liefert false wenn rucksack schon voll ist
        boolean retVal = false;

        if (clubmembers.size() < clubmembersMax) {
            retVal = true;
            clubmembers.add(figure);
        }

        return retVal;
    }

    /***
     *
     * @param memberName removes member from club
     * @return true if member was found and removed
     */
    public boolean removeClubMember(String memberName) {
        boolean retVal = false;
        int index = -1;
        for (int i = 0; i < clubmembers.size(); i++) {
            if (clubmembers.get(i).getName().equalsIgnoreCase(memberName)) {
                index = i;
            }
        }

        if (index > -1) {
            retVal = true;
            clubmembers.remove(index);
        }
        return retVal;
    }

    /***
     *
     * @param itemName searches item in list
     * @return -1 if not found else index if found
     */
    public int hasItem(String itemName) {
        int retVal = -1;
        for (int i = 0; i < backpack.size(); i++) {
            Item item = backpack.get(i);
            if (itemName.equalsIgnoreCase(item.getName())) {
                retVal = i;
            }
        }
        return retVal;
    }


    /***
     *
     * @return x position of figure
     */
    public int getX() {
        return x;
    }

    /***
     *
     * @return y position of figure
     */
    public int getY() {
        return y;
    }

    /***
     *
     * @return x position of the upper left corner of the rectangle in which the figure may move on the board
     */
    public int getxLo() {
        return xLo;
    }

    /***
     *
     * @return y position of the upper left corner of the rectangle in which the figure may move on the board
     */
    public int getyLo() {
        return yLo;
    }

    /***
     *
     * @return x position of the lower right corner of the rectangle in which the figure may move on the board
     */
    public int getxRu() {
        return xRu;
    }

    /***
     *
     * @return y position of the lower right corner of the rectangle in which the figure may move on the board
     */
    public int getyRu() {
        return yRu;
    }

    /***
     *
     * @return texts that the figure can say
     */
    public String[] getTexts() {
        return text;
    }

    /***
     *
     * @return options that the player can select
     */
    public String[] getOptions() {
        return option;
    }

    /***
     *
     * @return itemname of the item that the figure needs
     */
    public String getNeed() {
        return need;
    }

    /***
     *
     * @return value of lifepoints
     */
    public int getLife() {
        return life;
    }

    /***
     *
     * @return value of powerpoints
     */
    public int getPower() {
        return power;
    }

    /***
     *
     * @return v
     */
    //public int getGold() {
    //    return gold;
    //}

    /***
     *
     * @return name of figure
     */
    public String getName() {
        return name;
    }

    /***
     *
     * @param need name of item
     * set name of item the figure needs, if empty, the figures need nothing
     */
    public void setNeed(String need) {
        this.need = need;
    }

    /***
     *
     * @param moveable true or false
     * set if the figure is moveable or not
     */
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    /***
     *
     * @param life lifepoints
     * set lifepoints of the figure
     */
    public void setLife(int life) {
        this.life = life;
    }

    /***
     *
     * @param imgs list of images
     * sets the images of the figure
     */
    public void setImgs(HashMap<String, BufferedImage> imgs) {
        this.imgs = imgs;
    }

    /***
     *
     * @param gold
     */
    //public void setGold(int gold) {
    //    this.gold = gold;
    //}

    /***
     *
     * @param power powerpoints
     * set the powerpoints of the figures
     */
    public void setPower(int power) {
        this.power = power;
    }

    /***
     *
     * @return name of item that the figure gives
     */
    public String getGive() {
        return give;
    }

    /***
     *
     * @return list of items in the backpack
     */
    public ArrayList<Item> getBackpack() {
        return backpack;
    }

    /***
     *
     * @return list of figures in the club
     */
    public ArrayList<Figure> getClubmembers() {
        return clubmembers;
    }

    /***
     *
     * @param x new x position of figure
     * @param y new y position of figure
     * moves figure to pos x-y
     */
    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /***
     *
     * @return true if figure can move
     */
    public boolean isMoveable() {
        return moveable;
    }

    /***
     *
     * @return true if figure is bad and will fight
     */
    public boolean isBad() {
        return bad;
    }

    /***
     *
     * @return true if figure is visible on board
     */
    public boolean isVisible() {
        return visible;
    }

    /***
     *
     * @return true is figure can join to club
     */
    public boolean isFollowing() {
        return following;
    }

    /***
     *
     * @param direction direction of figure
     */
    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    /***
     *
     * @param visible true if figure is visible on board
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

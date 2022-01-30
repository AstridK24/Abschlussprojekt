import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Level {

    private String levelPath;//pfad vom level
    private Data d;//gemeinsame daten für level und board
    private int plusX = 0;//verschiebung des gesammten angezeigten bildes x
    private int plusY = 0;//y
    private ArrayList<String> boardData = new ArrayList<>();//für level zum auslesen
    private ArrayList<Item> items = new ArrayList<>();//alle items in level
    private ArrayList<Figure> figures = new ArrayList<>();//alle items in level
    private Figure player;


    ////////////////////////////////////////////////////
    public Level(String levelPath, Data d) {
        this.levelPath = levelPath;
        this.d = d;
        Load();
    }
    ////////////////////////////////////////////////////

    public void Load() {//laden vom levelfile
        File file = new File(levelPath + "/data.txt");//levelpfad und inhalt
        boolean isBoardData = true;
        boardData.clear();//brett löschen
        items.clear();//alle alten items löschen
        figures.clear();
        //player.clear();
        Scanner scan = null;//zum einlesen vom file-level
        try {
            scan = new Scanner(file);
            while (scan.hasNext()) {//liest leveldaten ein
                String curLine = scan.nextLine();
                if (curLine.isEmpty()) {//wenn leerzeile ist
                    isBoardData = false;
                } else {
                    if (isBoardData) {//äpfel
                        boardData.add(curLine);
                    } else {//birnen
                        ParseLine(curLine);
                    }
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void ParseLine(String curLine) {//zeile zerlegen

        if (!curLine.isEmpty()) {//wenn etwas in der zeil steht bei = spliten
            String[] subStrings = curLine.split("=");
            if (subStrings.length == 2) {//kann nur 2 sein
                String key = subStrings[0].toLowerCase().trim();//kleinschreiben und leerzeichen entf
                String[] values = subStrings[1].split(",");//bei , zerlegen
                for (int i = 0; i < values.length; i++) {//werte durchlaufen
                    values[i] = values[i].toLowerCase().trim();//kleinschreiben und leerzeichen entf
                }
                switch (key) {
                    case "item":
                        int x = Integer.parseInt(values[0]);
                        int y = Integer.parseInt(values[1]);
                        String fileName = levelPath + "/" + values[2];
                        Item item = new Item(x, y, fileName);
                        items.add(item);
                        break;
                    case "figure":
                        int x2 = Integer.parseInt(values[0]);
                        int y2 = Integer.parseInt(values[1]);
                        String fileName2 = levelPath + "/" + values[2];
                        Figure curFig = new Figure(x2, y2, fileName2);
                        figures.add(curFig);
                        break;
                    case "player":
                        int x3 = Integer.parseInt(values[0]);
                        int y3 = Integer.parseInt(values[1]);

                        String fileName3 = "";
                        switch (d.playerSelected) {
                            case "Lisa":
                                fileName3 = "file/Level1/lisa.txt";
                                break;
                            case "Ken":
                                fileName3 = "file/Level1/ken.txt";
                                break;
                            case "Fred":
                                fileName3 = "file/Level1/fred.txt";
                                break;
                            default:
                                fileName3 = "file/Level1/jasmin.txt";
                                break;
                        }
                        player = new Figure(x3, y3, fileName3);
                        break;
                }
            }
        }

    }

    public BufferedImage CreateBoard() {
        BufferedImage bufImg = new BufferedImage(d.cellWidth * d.width, d.cellHeight * d.height, BufferedImage.TYPE_INT_ARGB);
        //bufimg = zellenweite x weite = gesamtbreite vom level
        //zellenhöhe x höhe = gesamthöhe vom level
        //type int argb = farbbild mit fähigkeit transparenten hintergrund
        Graphics g = bufImg.getGraphics();//für bild in bild
        int curX = player.getX();

        //verschiebe angezeigtes board wenn nötig
        while (player.getX() < (plusX + 1)) {//bild nach links verschieben
            plusX = plusX - 1;
        }
        while (player.getX() >= (d.width + plusX - 1)) {//bild nach rechts verschieben
            plusX = plusX + 1;
        }
        while (player.getY() < (plusY + 1)) {//bild nach oben verschieben
            plusY = plusY - 1;
        }
        while (player.getY() >= (d.height + plusY - 1)) {//bild nach unen verschieben
            plusY = plusY + 1;
        }
        //durchlaufe das gesamte spielbrett: - zeichne hintergrund
        for (int x = 0; x < d.width; x++) {//0 bis max breite
            for (int y = 0; y < d.height; y++) {//0 bis max höhe
                String curCell = GetLevelData(x + plusX, y + plusY);//curcell bekommt die id der kachel an der koorrdinate x,y

                BufferedImage cellImg = GetCellImage(curCell);//cellimg= bild der curcell
                g.drawImage(cellImg, x * d.cellWidth, y * d.cellHeight, null);
                //zeichnet cellimg an der aktuellen pos ein
            }
        }

        for (int i = 0; i < items.size(); i++) {
            Item cutItem = items.get(i);
            if (cutItem.isVisible()) {
                if ((cutItem.getX() >= plusX) && (cutItem.getY() >= plusY)) {
                    if ((cutItem.getX() < (d.width + plusX)) && (cutItem.getY()) < (d.height + plusY)) {
                        g.drawImage(ResizeImage(cutItem.getImage()), (cutItem.getX() - plusX) * d.cellWidth, (cutItem.getY() - plusY) * d.cellHeight, null);
                    }
                }
            }
        }

        for (int i = 0; i < figures.size(); i++) {
            Figure curFig = figures.get(i);
            if (curFig.isVisible()) {
                g.drawImage(ResizeImage(curFig.getImage()), (curFig.getX() - plusX) * d.cellWidth, (curFig.getY() - plusY) * d.cellHeight, null);
            }
        }

        g.drawImage(ResizeImage(player.getImage()), (player.getX() - plusX) * d.cellWidth, (player.getY() - plusY) * d.cellHeight, null);
        return bufImg;
    }

    public void MovePlayer(Directions d) {//spieler bewegung
        if (d != Directions.nothing) {//wenn der spieler sich bewegt
            int x = player.getX();
            int y = player.getY();
            switch (d) {
                case left://nach links -1
                    x = x - 1;
                    break;
                case right://nach rechts +1
                    x = x + 1;
                    break;
                case up://nach oben -1
                    y = y - 1;
                    break;
                case down://nach unten +1
                    y = y + 1;
                    break;
            }

            Checker(x, y, player);//prüft ob begehbar und wenn ja dann bewge fig nach xy
            player.setDirection(d);//drehung animation
            player.MakeStep();//schritte animation
        }
    }

    public void MoveFigures() {//sonstige figuren bewegen
        Random random = new Random();//zufall
        for (int i = 0; i < figures.size(); i++) {
            Figure curFigure = figures.get(i);
            if (curFigure.isMoveable()) {
                int cur = random.nextInt(6);
                int x = curFigure.getX();
                int y = curFigure.getY();
                Directions d = Directions.nothing;
                switch (cur) {
                    case 0://nach links -1
                        x = x - 1;
                        d = Directions.left;
                        break;
                    case 1://nach rechts +1
                        x = x + 1;
                        d = Directions.right;
                        break;
                    case 2://nach oben -1
                        y = y - 1;
                        d = Directions.up;
                        break;
                    case 3://nach unten +1
                        y = y + 1;
                        d = Directions.down;
                        break;
                }
                if (d != Directions.nothing) {
                    Checker(x, y, curFigure);//prüft ob begehbar und wenn ja dann bewge fig nach xy
                    curFigure.setDirection(d);//drehung animation
                    curFigure.MakeStep();//schritte animation
                }
            }

        }
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
        if ((img.getWidth(null) != d.cellWidth) || (img.getHeight(null) != d.cellHeight)) {
            BufferedImage resImg = new BufferedImage(d.cellWidth, d.cellHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resImg.createGraphics();
            g.drawImage(img, 0, 0, d.cellWidth, d.cellHeight, null);
            g.dispose();
            return resImg;
        }
        else{
            return (BufferedImage)img;
        }
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

    private void Checker(int x, int y, Figure curFig) {
        if (curFig.isMoveable()) {//wenn die figur beweglich ist
            if ((x >= 0) && (y >= 0)) {//linkes eck oben
                if (y < boardData.size()) {//höhe vom aktuellen level
                    if (x < boardData.get(y).length()) {//breite vom aktuellen level
                        if ((x >= curFig.getxLo()) && (y >= curFig.getyLo()) && (x < curFig.getxRu()) && (y < curFig.getyRu())) {
                            String key = "" + boardData.get(y).charAt(x);//char zu string umwandeln
                            Tile curTile = d.tiles.get(key);//kachel aus der hashmap holen
                            if (curTile.isWalkable()) {//wenn die kachel begehbar ist
                                curFig.setXY(x, y);//figur setzen
                            }
                        }
                    }
                }
            }
        }
    }
}

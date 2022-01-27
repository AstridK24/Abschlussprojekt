import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Item {
    private int width = 64;
    private int height = 64;
    private int x;//pos x
    private int y;// pos y
    private String fileName;//datei mit item daten
    private boolean isVisible = false;
    private String name = "";
    private Image image;


    ////////////////////////////////7
    public Item(int x, int y, String fileName) {
        this.x = x;
        this.y = y;
        this.fileName = fileName;
    }
////////////////////////////////

    private void Load() {
        File file = new File(fileName);// datei mit itemdaten öffnen
        Scanner scan = null;//zum einlesen vom z.b. apfel.txt
        try {
            scan = new Scanner(file);
            while (scan.hasNext()) {
                String curLine = scan.nextLine();
                if (!curLine.isEmpty()) {
                    String[] subStrings = curLine.split("=");
                    if (subStrings.length == 2) {
                        String key = subStrings[0].toLowerCase().trim();
                        String value = subStrings[1];

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
            g.fillRect(0, 0, width, height);//größe des rechtecks-zelle
        }
        return img;
    }
}

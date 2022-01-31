import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Inventory extends JTable {

    private ArrayList<Item> items = new ArrayList<>();
    private int maxItem = 5;
    private int cellWidth = 64;
    private int cellHeight =64;
    //////////////////////////////////
    public Inventory() {
        DefaultTableModel model = new DefaultTableModel();//spalte east

        int rows = maxItem/2;
        if ((maxItem % 2) > 0){
            rows++;
        }
        model.setRowCount(rows);//12 reihen
        model.setColumnCount(2);//2 spalten
        setFocusable(false);
        setTableHeader(null);//kein header
        setModel(model);
        setRowHeight(cellHeight);

    }
    /////////////////////////////////


    public void SetItems(ArrayList<Item> items){
        this.items = items;
        repaint();
    }

    public void paint(Graphics g) {//zeichne spielbrett

        int row = 0;
        int col = 0;

        for (int i = 0; i < items.size(); i++) { // gehe alle items in inventarliste durch
            if (row < this.getRowCount()) { // inventarliste noch lang genug zum anzeigen?
                Item curItem = items.get(i); // hole item aus inventarliste
                setValueAt(new ImageIcon(ResizeImage(curItem.getImage())), row, col); // zeichne item in jtable
                col++; // spalte + 1
                if (col > 1) { // spalte größer 1 (2 spalten, 0 und 1)
                    col = 0; // spalte = 0
                    row++; // erhöhe reihe
                }
            }
        }
        super.paint(g); // zeichne alles was jtable sonst noch so zeichnen würde (striche etc)
    }

    public Class getColumnClass(int column) {//zeigt nur icons an (kein text möglich)
        return Icon.class;
    }

    private BufferedImage ResizeImage(Image img) {//bildgröße anpassen
        if ((img.getWidth(null) != cellWidth) || (img.getHeight(null) != cellHeight)) { // ist bild nicht in der richtigen größe, passe es an
            BufferedImage resImg = new BufferedImage(cellWidth, cellHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resImg.createGraphics();
            g.drawImage(img, 0, 0, cellWidth, cellHeight, null);
            g.dispose(); // schließe grafikbearbeitung wieder
            return resImg;
        }
        else{ // bild war schon in der richtigen größe
            return (BufferedImage)img; // typeumwandlung von Image nach BufferedImage
        }
    }


}

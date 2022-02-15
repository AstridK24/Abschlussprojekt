import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Club extends JTable {

    private ArrayList<Figure> figures = new ArrayList<>();
    private int maxFigure = 4;//inhalt vom rucksack
    private int cellWidth = 64;//größe der anzeige
    private int cellHeight = 64;//größe der anzeige
    private DefaultTableModel model = new DefaultTableModel();

    //////////////////////////////////
    public Club() {

        ClearAll();

        setFocusable(false);
        setTableHeader(null);//kein header
        setModel(model);//spalte east anzeigen
        setRowHeight(cellHeight);

    }
    /////////////////////////////////


    /***
     *
     * @param figures sets figures
     */
    public void setFigures(ArrayList<Figure> figures) {//setter für figuren
        this.figures = figures;
        repaint();
    }

    /***
     *
     * @param g graficshandler
     */
    public void paint(Graphics g) {//zeichne spielbrett
        ClearAll();
        int row = 0;
        int col = 0;
        for (int i = 0; i < figures.size(); i++) { // gehe alle items in inventarliste durch
            if (row < this.getRowCount()) { // inventarliste noch lang genug zum anzeigen?
                Figure curFig = figures.get(i); // hole item aus inventarliste
                setValueAt(new ImageIcon(resizeImage(curFig.getImage())), row, col); // zeichne item in jtable
                col++; // spalte + 1
                if (col > 1) { // spalte größer 1 (2 spalten, 0 und 1)
                    col = 0; // spalte = 0
                    row++; // erhöhe reihe
                }
            }
        }
        super.paint(g); // zeichne alles was jtable sonst noch so zeichnen würde (striche etc)
    }

    /***
     *
     * @param column column of Table
     * @return Icon.class
     */
    @Override
    public Class getColumnClass(int column) {//zeigt nur icons an (kein text möglich), wird von jtable.setValueAt gebraucht
        return Icon.class;
    }

    private BufferedImage resizeImage(Image img) {//bildgröße anpassen
        if ((img.getWidth(null) != cellWidth) || (img.getHeight(null) != cellHeight)) { // ist bild nicht in der richtigen größe, passe es an
            BufferedImage resImg = new BufferedImage(cellWidth, cellHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resImg.createGraphics();
            g.drawImage(img, 0, 0, cellWidth, cellHeight, null);
            g.dispose(); // schließe grafikbearbeitung wieder
            return resImg;
        } else { // bild war schon in der richtigen größe
            return (BufferedImage) img; // typeumwandlung von Image nach BufferedImage
        }
    }

    private void ClearAll() {
        //löschen
        model.setColumnCount(0);
        model.setRowCount(0);

        //neu dimensionieren
        int rows = maxFigure / 2;//maximale doppelspalte
        if ((maxFigure % 2) > 0) {//wenn ungerade zahl
            rows++;
        }
        model.setRowCount(rows);//12 reihen
        model.setColumnCount(2);//2 spalten
    }
}

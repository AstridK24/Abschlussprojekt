import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class GameMain extends JFrame {

    JLabel startImg;//startbild
    ImageIcon ditem = new ImageIcon("img/item/ditem.png");//für jtable
    ImageIcon energie = new ImageIcon("img/item/energie2.png");
    ImageIcon herzv = new ImageIcon("img/item/herzvoll.png");
    ImageIcon herzl = new ImageIcon("img/item/herzleer.png");
    ImageIcon hero = new ImageIcon("img/item/superhelden.png");
    ImageIcon smax = new ImageIcon("img/figuren/supermax.txt/supermax.txt v2.png");
    ImageIcon bluki = new ImageIcon("img/figuren/batluki.txt/batluki.txt v2.png");
    ImageIcon figur1 = new ImageIcon("img/figur1.png");//bild für rb
    //ImageIcon figur2 = new ImageIcon("img/figur2.png");//bild für rb
    //ImageIcon figur3 = new ImageIcon("img/figur3.png");//bild für rb
    Board board;
    JPanel middle = new JPanel();
    //JLabel label = new JLabel("Deine Items: " + "aaaaaaaa" + "bbbbbbbbb"+ startImg + "img/biberMitAxt.png");

    /////////////////////////////
    public GameMain() {//konstruktor

        ImageIcon icon1;//icon festlegen
        icon1 = new ImageIcon("img/hallodri2.jpg");//icon pfad
        startImg = new JLabel(icon1);//startbild

        setTitle("Abschlussprojekt");//titel
        setSize(994, 750);//grösse vom  fenster
        setLocationRelativeTo(null);//mittig
        setDefaultCloseOperation(EXIT_ON_CLOSE);//schliessen

        middle.add(startImg);//starbild sichtbar im center
        //label.add("hhhhh");
        //this.add(startImg);

        JMenuBar menu = new JMenuBar();//menübar
        JMenu gameMenu = new JMenu("Spiel");//erster knopf - submit
        JMenuItem gameStart = new JMenuItem("Starten");//erster untertitel
        /*DefaultTableModel model = new DefaultTableModel();//spalte east
        JTable table = new JTable(model) {
            public Class getColumnClass(int column) {//zeigt nut icons an
                return Icon.class;
            }
        };
        model.setRowCount(12);//12 reihen
        model.setColumnCount(2);//2 spalten
        table.setFocusable(false);
        table.setTableHeader(null);//kein header*/

        gameStart.addActionListener(new ActionListener() {//aktion
            @Override
            public void actionPerformed(ActionEvent e) {

                String enterName = JOptionPane.showInputDialog("Bitte gib deinen Namen ein:");//eingabefenster
                if (enterName.isEmpty()) {//wenn kein name wieder zurück
                    //System.out.println("aaaaaaaaaaaaaaaa");
                } else {
                    // JOptionPane.showMessageDialog(null, "Hallo " + eingabeName + ". Lass uns starten");//kontrollfenster
                    RadioButton radioButton = new RadioButton();//rb erstellen
                    radioButton.setVisible(true);//rb sichtbar
                    radioButton.addWindowListener(new WindowAdapter() {
                        public void windowClosed(WindowEvent e) {
                            System.out.println(radioButton.selected);//spielfigur

                            board = new Board(radioButton.selected);

                            add(board);
                            revalidate();
                            pack();
                        }
                    });
                    gameStart.setEnabled(false);//startknopf auf falsch stellen
                    middle.setVisible(false);
                }
            }
        });

        JMenuItem gameEnd = new JMenuItem("Verlassen");//zweiter untertitel

        gameEnd.addActionListener(new ActionListener() {//aktion
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;//ja-nein fenster
                int dialogResult = JOptionPane.showConfirmDialog(gameEnd, "Bist du sicher?", "Spiel beenden", dialogButton);//beschriftung
                if (dialogResult == 0) {//wenn ja - beenden
                    System.exit(0);//beenden
                }
            }
        });
        gameMenu.add(gameStart);//submit
        gameMenu.addSeparator();//strich zwischen den untermenüs
        gameMenu.add(gameEnd);//noch immer submit


        JMenuItem gameInstructions = new JMenuItem("Spielanleitung");     //spielanleitungbutton
        gameInstructions.addActionListener(new ActionListener() {//aktion
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                URL url = null;
                try {
                    url = new URL("file:///C:/Users/bfi/Desktop/Spielanleitung/index.html"); //zur html
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }
                try {
                    desktop.browse(url.toURI());//desktop öffnet browser
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //spielfeld mitte
        setLayout(new BorderLayout());
        add(middle, BorderLayout.CENTER);

        //leister rechts mit item, energie,...
       /* table.setRowHeight(64);
        table.setValueAt(energie, 0, 0);
        table.setValueAt(herzv, 0, 1);
        table.setValueAt(herzv, 1, 0);
        table.setValueAt(herzl, 1, 1);
        table.setValueAt(ditem, 3, 0);
        table.setValueAt(figur1, 3, 1);
        table.setValueAt(hero, 8, 0);
        table.setValueAt(smax, 8, 1);
        table.setValueAt(bluki, 9, 0);
        add(table, BorderLayout.EAST);*/


        menu.add(gameMenu);//submit erscheinen lassen
        menu.add(gameInstructions);//anleitung erscheinen lassen

        setJMenuBar(menu);//Hinzufügen der Menüleiste zum Frame
    }
    /////////////////////////////

    public static void main(String[] args) {

        GameMain gameMain = new GameMain();
        gameMain.setVisible(true);//sichtbar machen

    }
}

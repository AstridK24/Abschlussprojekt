import javax.swing.*;
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
    //ImageIcon figur1 = new ImageIcon("img/figur1.png");//bild für rb
    //ImageIcon figur2 = new ImageIcon("img/figur2.png");//bild für rb
    //ImageIcon figur3 = new ImageIcon("img/figur3.png");//bild für rb
    JPanel board = new JPanel();
    /////////////////////////////
    public GameMain()  {//konstruktor

        Icon icon1;//icon festlegen
        icon1 = new ImageIcon("img/hallodri2.jpg");//icon pfad
        startImg = new JLabel(icon1);//startbild

        setTitle("Abschlussprojekt");//titel
        setSize(994,679);//grösse vom  fenster
        setLocationRelativeTo(null);//mittig
        setDefaultCloseOperation(EXIT_ON_CLOSE);//schliessen

        this.add(startImg);//starbild sichtbar

        JMenuBar menu = new JMenuBar();//menübar
        JMenu gameMenu = new JMenu("Spiel");//erster knopf - submit
        JMenuItem gameStart = new JMenuItem("Starten");//erster untertitel


        gameStart.addActionListener(new ActionListener() {//aktion
            @Override
            public void actionPerformed(ActionEvent e) {

                String enterName = JOptionPane.showInputDialog("Bitte gib deinen Namen ein:");//eingabefenster
                if (enterName.isEmpty()){//wenn kein name wieder zurück
                    //System.out.println("aaaaaaaaaaaaaaaa");
                }else {
                   // JOptionPane.showMessageDialog(null, "Hallo " + eingabeName + ". Lass uns starten");//kontrollfenster
                    RadioButton radioButton = new RadioButton();//rb erstellen
                    radioButton.setVisible(true);//rb sichtbar
                    radioButton.addWindowListener(new WindowAdapter(){
                        public void windowClosed(WindowEvent e){
                            System.out.println(radioButton.selected);

                            add(board);
                            revalidate();
                        }
                    });

                    gameStart.setEnabled(false);//startknopf auf falsch stellen


                }
            }
        });
        JMenuItem gameEnd = new JMenuItem("Verlassen");//zweiter untertitel

        gameEnd.addActionListener(new ActionListener() {//aktion
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;//ja-nein fenster
                int dialogResult = JOptionPane.showConfirmDialog(gameEnd, "Bist du sicher?", "Spiel beenden", dialogButton);//beschriftung
                if(dialogResult == 0) {//wenn ja - beenden
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

import javax.swing.*;
import java.awt.*;

public class Images1 extends JPanel {

    private ImageIcon startbild = new ImageIcon("img/hallodri2.jpg");
    private ImageIcon spieler = new ImageIcon("img/spieler.png");
    private ImageIcon figur1 = new ImageIcon("img/figur1.png");
    private ImageIcon figur2 = new ImageIcon("img/figur2.png");
    private ImageIcon biberMitAxt = new ImageIcon("img/biberMitAxt.png");
    private ImageIcon biberMitMotorsaege = new ImageIcon("img/biberMitMotorsäge.png");
    private ImageIcon biberMitMotorsaege2 = new ImageIcon("img/biberMitMotorsäge2.png");
    private ImageIcon biberMitMotorsaege3 = new ImageIcon("img/biberMitMotorsäge3.png");
    private ImageIcon biberMitSaege = new ImageIcon("img/biberMitSaege.png");
    private ImageIcon hecke = new ImageIcon("img/hecke.png");
    private ImageIcon helfer = new ImageIcon("img/helfer.png");
    private ImageIcon item = new ImageIcon("img/item.png");
    private ImageIcon wasser = new ImageIcon("img/wasser.png");

    private JLabel imageLabel;
    private JPanel imagePanel;

    public Images1() {
        imagePanel=new JPanel();
        imageLabel=new JLabel(startbild);
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.CENTER);
    }

}

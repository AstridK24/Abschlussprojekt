import javax.swing.*;
import java.awt.*;

public class Status extends JPanel {
/*    ImageIcon pointIcon = new ImageIcon("img/spieler.png");
    ImageIcon liveIcon = new ImageIcon("img/spieler.png");
    ImageIcon powerIcon = new ImageIcon("img/spieler.png");
    ImageIcon timeIcon = new ImageIcon("img/spieler.png");*/

    private JLabel pointLabel = new JLabel("point");
    private JLabel liveLabel = new JLabel("live");
    private JLabel powerLabel = new JLabel("power");
    private JLabel timeLabel = new JLabel("time");

   /* int point;
    int live;
    int power;
    int time;*/

    /////////////////////////////////
    public Status() {

        pointLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        liveLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        powerLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        //pointLabel.setFont();
        setLayout( new BoxLayout(this,BoxLayout.Y_AXIS ) );
        add(pointLabel);
        add(liveLabel);
        add(powerLabel);
        add(timeLabel);
    }
    /////////////////////////////////

    /***
     *
     * @param point value of points of player
     * @param live value of life of player
     * @param power value of power of player
     * @param time value of time of player
     * sets values to display
     */
    public void setStatus(int point, int live, int power, int time) {
       pointLabel.setText("Punkte x "+point);
        liveLabel.setText("Leben x "+live);
        powerLabel.setText("Kraft x "+power);
        timeLabel.setText("Zeit x "+time);
        repaint();

    }
}

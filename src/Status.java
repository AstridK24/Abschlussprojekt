import javax.swing.*;

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

    public Status() {

        //pointLabel.setFont();
        setLayout( new BoxLayout(this,BoxLayout.Y_AXIS ) );
        add(pointLabel);
        add(liveLabel);
        add(powerLabel);
        add(timeLabel);


    }

    public void SetStatus(int point, int live, int power, int time) {
       pointLabel.setText("point x "+point);
        liveLabel.setText("live x "+live);
        powerLabel.setText("power x "+power);
        timeLabel.setText("time x "+time);
        repaint();

    }

}

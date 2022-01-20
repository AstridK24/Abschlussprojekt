import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

/**
 * A Swing program that demonstrates how to use JRadioButton component.
 *
 * @author www.codejava.net
 *
 */
public class RadioButton extends JFrame {//rb figurenauswahl

    private JButton buttonOK = new JButton("OK");//ok

    private JRadioButton player1 = new JRadioButton("Mädchen");//text
    private JRadioButton player2 = new JRadioButton("Junge1");
    private JRadioButton player3 = new JRadioButton("Junge2");

    private JLabel labelImage = new JLabel();//fotolabel

    private ImageIcon iconFigur1= new ImageIcon("img/figur1.png");//pfad
    private ImageIcon iconFigur2 = new ImageIcon("img/figur2.png");
    private ImageIcon iconFigur3 = new ImageIcon("img/figur3.png");

    public RadioButton() {//konstrukter
        super("Figur auswählen");//überschrift

        ButtonGroup group = new ButtonGroup();//rb gruppieren bzw nur einer wählbar
        group.add(player1);
        group.add(player2);
        group.add(player3);

        player2.setSelected(true);//wenn junge1 gewählt ist - foto anzeigen
        labelImage.setIcon(iconFigur2);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        add(player1, constraints);
        constraints.gridx = 1;
        add(player2, constraints);
        constraints.gridx = 2;
        add(player3, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;

        add(labelImage, constraints);

        constraints.gridy = 2;
        add(buttonOK, constraints);

        RadioButtonActionListener actionListener = new RadioButtonActionListener();//aktion
        player1.addActionListener(actionListener);
        player2.addActionListener(actionListener);
        player3.addActionListener(actionListener);

        buttonOK.addActionListener(new ActionListener() {//aktion

            @Override
            public void actionPerformed(ActionEvent event) {
                String selectedOption = "";
                if (player1.isSelected()) {
                    selectedOption = "Lisa";
                } else if (player2.isSelected()) {
                    selectedOption = "Ken";
                } else if (player3.isSelected()) {
                    selectedOption = "Fred";
                }
                JOptionPane.showMessageDialog(RadioButton.this,
                        "Das ist: " + selectedOption);
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    class RadioButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            JRadioButton button = (JRadioButton) event.getSource();
            if (button == player1) {

                labelImage.setIcon(iconFigur1);

            } else if (button == player2) {

                labelImage.setIcon(iconFigur2);

            } else if (button == player3) {

                labelImage.setIcon(iconFigur3);
            }
        }
    }

   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new RadioButton().setVisible(true);
            }
        });
    }*/
}

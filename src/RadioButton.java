import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;


public class RadioButton extends JFrame {//rb figurenauswahl


    public static String selected = "";

    private JButton buttonOK = new JButton("Los geht`s");//ok

    private JRadioButton lisa = new JRadioButton("Lisa");//text
    private JRadioButton ken = new JRadioButton("Ken");
    private JRadioButton fred = new JRadioButton("Fred");
    private JRadioButton jasmin = new JRadioButton("Jasmin");

    private JLabel labelImage = new JLabel();//fotolabel

    private ImageIcon iconLisa = new ImageIcon("img/figuren/lisa.gif");//pfad
    private ImageIcon iconKen = new ImageIcon("img/figuren/ken.gif");
    private ImageIcon iconFred = new ImageIcon("img/figuren/fred.gif");
    private ImageIcon iconJasmin = new ImageIcon("img/figuren/jasmin.gif");

    ///////////////////////////
    public RadioButton() {//konstrukter
        super("Wähle deine Spielfigur aus");//fenstertitel

        ButtonGroup group = new ButtonGroup();//rb gruppieren bzw nur einer wählbar
        group.add(lisa);
        group.add(ken);
        group.add(fred);
        group.add(jasmin);

        ken.setSelected(true);//wenn junge1 gewählt ist - foto anzeigen
        labelImage.setIcon(iconKen);

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 40);

        add(lisa, constraints);
        constraints.gridx = 1;
        add(ken, constraints);
        constraints.gridx = 2;
        add(fred, constraints);
        constraints.gridx = 3;
        add(jasmin, constraints);


        constraints.gridx = 1;//figur pos geht leider nicht mittiger
        constraints.gridy = 1;
        constraints.gridwidth = 3;

        add(labelImage, constraints);

        constraints.gridy = 2;
        add(buttonOK, constraints);

        RadioButtonActionListener actionListener = new RadioButtonActionListener();//aktion
        lisa.addActionListener(actionListener);
        ken.addActionListener(actionListener);
        fred.addActionListener(actionListener);
        jasmin.addActionListener(actionListener);

        buttonOK.addActionListener(new ActionListener() {//aktion

            /***
             *
             * @param event
             */
            @Override
            public void actionPerformed(ActionEvent event) {
                String selectedOption = "";
                if (lisa.isSelected()) {
                    selectedOption = "Lisa";
                } else if (ken.isSelected()) {
                    selectedOption = "Ken";
                } else if (fred.isSelected()) {
                    selectedOption = "Fred";
                }else if (jasmin.isSelected()) {
                    selectedOption = "Jasmin";
                }
                //JOptionPane.showMessageDialog(RadioButton.this,
                       // "Du hast " + selectedOption + " gewählt. Los geht´s!");
                selected = selectedOption;
                dispose();
                //hier muss das feld gelöscht werden und neu aufgebaut-spielfigur übernehmen!
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    ///////////////////

    class RadioButtonActionListener implements ActionListener {
        /***
         *
         * @param event
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            JRadioButton button = (JRadioButton) event.getSource();
            if (button == lisa) {
                labelImage.setIcon(iconLisa);
            } else if (button == ken) {
                labelImage.setIcon(iconKen);
            } else if (button == fred) {
                labelImage.setIcon(iconFred);
            }else if (button == jasmin) {
                labelImage.setIcon(iconJasmin);
            }
        }
    }

}

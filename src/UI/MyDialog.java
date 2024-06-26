package UI;

import source.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MyDialog extends JDialog implements ActionListener
{
    JComboBox<Object> degree;
    JComboBox<Object> ratio;
    Mbutton sure;
    Mbutton cancel;
    public static double compactness;
    public static double orientation;
    
    MyDialog(final JFrame f, final String s) {
        super(f, s);
        this.setTitle(s);
        final Box box = Box.createVerticalBox();
        JLabel label = new JLabel("Choose the Compactness:");
        label.setFont(Source.dimensionFont);
        (this.ratio = new JComboBox<Object>(new Object[] { "1", "5", "10", "20", "30", "40" })).setName("Compactness");
        this.ratio.setEditable(true);
        this.ratio.setFont(Source.comboxFont);
        Box temp = Box.createHorizontalBox();
        temp.add(label);
        temp.add(Box.createHorizontalStrut(10));
        temp.add(this.ratio);
        box.add(temp);
        box.add(Box.createVerticalStrut(20));
        label = new JLabel("Choose the Angle of rotation:");
        label.setFont(Source.dimensionFont);
        (this.degree = new JComboBox<Object>(new Object[] { "0", "5", "10", "20", "30", "40" })).setName("Degress");
        this.degree.setEditable(true);
        this.degree.setFont(Source.comboxFont);
        temp = Box.createHorizontalBox();
        temp.add(label);
        temp.add(Box.createHorizontalStrut(10));
        temp.add(this.degree);
        box.add(temp);
        box.add(Box.createVerticalStrut(20));
        (this.sure = new Mbutton("Sure")).addActionListener(this);
        (this.cancel = new Mbutton("Cancel")).addActionListener(this);
        temp = Box.createHorizontalBox();
        temp.add(this.sure);
        temp.add(Box.createHorizontalStrut(20));
        temp.add(this.cancel);
        final JPanel temp2 = new JPanel(new BorderLayout());
        temp2.add(temp, "East");
        temp2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        final JPanel result = new JPanel(new BorderLayout());
        result.add(box, "Center");
        result.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new BorderLayout());
        this.add(result, "Center");
        this.add(temp2, "South");
        this.setBounds(800, 360, 500, 400);
        this.setDefaultCloseOperation(2);
        this.pack();
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.sure) {
            MyDialog.compactness = Double.valueOf((String)this.ratio.getSelectedItem());
            MyDialog.orientation = Double.valueOf((String)this.degree.getSelectedItem());
        }
        this.setVisible(false);
    }
}

package UI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class EndButton extends JButton implements ActionListener
{
    ImageIcon end;
    Dimension endButtonSize;
    
    public EndButton() {
        this.end = new ImageIcon("image/end.png");
        this.endButtonSize = new Dimension(this.end.getIconWidth() + 2, this.end.getIconHeight() + 2);
        this.setEnabled(false);
        this.setContentAreaFilled(false);
        this.setPreferredSize(this.endButtonSize);
        this.setIcon(this.end);
        this.addActionListener(this);
        this.setBorder(null);
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
    }
}

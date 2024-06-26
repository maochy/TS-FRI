package UI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SuspendButton extends JButton implements ActionListener
{
    boolean flag;
    ImageIcon restart;
    ImageIcon suspend;
    Dimension suButtonSize;
    
    public SuspendButton() {
        this.flag = false;
        this.restart = new ImageIcon("image/start.png");
        this.suspend = new ImageIcon("image/suspend.png");
        this.suButtonSize = new Dimension(this.suspend.getIconWidth() + 2, this.suspend.getIconHeight() + 2);
        this.setEnabled(false);
        this.setContentAreaFilled(false);
        this.setPreferredSize(this.suButtonSize);
        this.setIcon(this.suspend);
        this.addActionListener(this);
        this.setBorder(null);
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (!this.flag) {
            this.flag = true;
            this.setIcon(this.restart);
        }
        else {
            this.flag = false;
            this.setIcon(this.suspend);
        }
    }
}

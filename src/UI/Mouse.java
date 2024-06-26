package UI;

import java.awt.event.*;
import javax.swing.*;

class Mouse implements KeyListener
{
    SimulationJPanel parent;
    
    Mouse(final SimulationJPanel parent) {
        this.parent = parent;
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == 10) {
            final JTextField temp = (JTextField)e.getSource();
            final int number = Integer.parseInt(temp.getText().trim());
            System.out.println(number);
            final DimensionPanel dim = new DimensionPanel(number);
            if (this.parent.box.getComponentCount() != 0) {
                this.parent.box.removeAll();
            }
            this.parent.box.add(dim);
            this.parent.revalidate();
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
    }
}

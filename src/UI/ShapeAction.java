package UI;

import java.awt.event.*;
import javax.swing.*;

class ShapeAction implements ActionListener
{
    SimulationJPanel parent;
    
    public ShapeAction(final SimulationJPanel parent) {
        this.parent = parent;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        final JComboBox temp = (JComboBox)e.getSource();
        final String shape = (String)temp.getSelectedItem();
        if (temp.getSelectedIndex() > 1) {
            new MyDialog(this.parent.mainJFram, "Choose the Setting").show();
        }
    }
}

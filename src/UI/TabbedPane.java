package UI;

import source.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TabbedPane extends JTabbedPane implements ActionListener
{
    private ImageIcon closeXIcon;
    private Dimension closeButtonSize;
    
    public TabbedPane() {
        this.closeXIcon = new ImageIcon("image/close.png");
        this.closeButtonSize = new Dimension(this.closeXIcon.getIconWidth() + 2, this.closeXIcon.getIconHeight() + 2);
    }
    
    public JTextArea add() {
        final JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        final JScrollPane showpanel = new JScrollPane(area);
        area.setFont(Source.mainFont);
        final JPanel tab = new JPanel();
        tab.setOpaque(false);
        final JLabel tabLabel = new JLabel("Console");
        tabLabel.setFont(Source.mainFont);
        final JButton tabCloseButton = new JButton(this.closeXIcon);
        tabCloseButton.setPreferredSize(this.closeButtonSize);
        tabCloseButton.addActionListener(this);
        tab.add(tabLabel, "West");
        tab.add(tabCloseButton, "East");
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        final JPanel north = new JPanel(new BorderLayout());
        north.add(tab, "West");
        panel.add(north, "North");
        this.addTab(null, showpanel);
        this.setTabComponentAt(this.getTabCount() - 1, panel);
        return area;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        final int closeTabNumber = this.getSelectedIndex();
        this.removeTabAt(closeTabNumber);
        if (this.getTabCount() == 0) {
            SimulationJPanel.endbutton.setEnabled(false);
            SimulationJPanel.suspend.setEnabled(false);
        }
    }
}

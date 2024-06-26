package UI;

import java.util.*;

import common.Dimension;
import source.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DimensionPanel extends JPanel
{
    private ArrayList<JTextField> maxArraylist;
    private ArrayList<JTextField> minArraylist;
    JButton closelabel;
    
    public DimensionPanel(final int dimension) {
        this.maxArraylist = new ArrayList<JTextField>();
        this.minArraylist = new ArrayList<JTextField>();
        final Box box = Box.createVerticalBox();
        for (int i = 0; i < dimension; ++i) {
            final JLabel label = new JLabel(String.valueOf(i + 1) + "_Dimension:");
            label.setFont(Source.dimensionFont);
            final JLabel maxLabel = new JLabel("Max:");
            maxLabel.setFont(Source.dimensionFont);
            final JLabel minLabel = new JLabel("Min:");
            minLabel.setFont(Source.dimensionFont);
            final JTextField maxeditor = new JTextField(8);
            final JTextField mineditor = new JTextField(8);
            maxeditor.setLayout(new BorderLayout());
            mineditor.setLayout(new BorderLayout());
            maxeditor.addFocusListener(new focuslistener());
            mineditor.addFocusListener(new focuslistener());
            maxeditor.setText("1");
            maxeditor.setFont(Source.dimensionFont);
            mineditor.setText("0");
            mineditor.setFont(Source.dimensionFont);
            this.maxArraylist.add(maxeditor);
            this.minArraylist.add(mineditor);
            final Box box2 = Box.createHorizontalBox();
            box2.add(label);
            box2.add(Box.createHorizontalStrut(10));
            box2.add(maxLabel);
            box2.add(Box.createHorizontalStrut(10));
            box2.add(maxeditor);
            box2.add(Box.createHorizontalStrut(10));
            box2.add(minLabel);
            box2.add(Box.createHorizontalStrut(10));
            box2.add(mineditor);
            box.add(box2);
            box.add(Box.createVerticalStrut(10));
        }
        this.setLayout(new FlowLayout(0, 10, 10));
        this.add(box);
    }
    
    public DimensionPanel(final ArrayList<Dimension> list) {
        this.maxArraylist = new ArrayList<JTextField>();
        this.minArraylist = new ArrayList<JTextField>();
        final Box box = Box.createVerticalBox();
        for (int i = 0; i < list.size(); ++i) {
            final JLabel label = new JLabel(String.valueOf(i + 1) + "_Dimension:");
            label.setFont(Source.dimensionFont);
            final JLabel maxLabel = new JLabel("Max:");
            maxLabel.setFont(Source.dimensionFont);
            final JLabel minLabel = new JLabel("Min:");
            minLabel.setFont(Source.dimensionFont);
            final JTextField maxeditor = new JTextField(8);
            final JTextField mineditor = new JTextField(8);
            maxeditor.setLayout(new BorderLayout());
            mineditor.setLayout(new BorderLayout());
            maxeditor.addFocusListener(new focuslistener());
            mineditor.addFocusListener(new focuslistener());
            maxeditor.setText(String.valueOf(list.get(i).getMax()));
            maxeditor.setFont(Source.dimensionFont);
            mineditor.setText(String.valueOf(list.get(i).getMin()));
            mineditor.setFont(Source.dimensionFont);
            this.maxArraylist.add(maxeditor);
            this.minArraylist.add(mineditor);
            final Box box2 = Box.createHorizontalBox();
            box2.add(label);
            box2.add(Box.createHorizontalStrut(10));
            box2.add(maxLabel);
            box2.add(Box.createHorizontalStrut(10));
            box2.add(maxeditor);
            box2.add(Box.createHorizontalStrut(10));
            box2.add(minLabel);
            box2.add(Box.createHorizontalStrut(10));
            box2.add(mineditor);
            box.add(box2);
            box.add(Box.createVerticalStrut(10));
        }
        this.setLayout(new FlowLayout(0, 10, 10));
        this.add(box);
    }
    
    public ArrayList<JTextField> getmaxArraylist() {
        return this.maxArraylist;
    }
    
    public ArrayList<JTextField> getminArraylist() {
        return this.minArraylist;
    }
    
    class focuslistener implements FocusListener
    {
        @Override
        public void focusGained(final FocusEvent e) {
            final JTextField temp = (JTextField)e.getSource();
            (DimensionPanel.this.closelabel = new JButton(new ImageIcon("image/close.png"))).setBorder(null);
            DimensionPanel.this.closelabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(final MouseEvent e) {
                }
                
                @Override
                public void mousePressed(final MouseEvent e) {
                    final JButton button = (JButton)e.getSource();
                    final JTextField temp = (JTextField)button.getParent();
                    temp.setText("");
                }
                
                @Override
                public void mouseExited(final MouseEvent e) {
                }
                
                @Override
                public void mouseEntered(final MouseEvent e) {
                    final JButton b = (JButton)e.getSource();
                    b.setCursor(new Cursor(12));
                }
                
                @Override
                public void mouseClicked(final MouseEvent e) {
                }
            });
            if (temp.getComponentCount() == 0) {
                temp.add(DimensionPanel.this.closelabel, "East");
                temp.revalidate();
            }
        }
        
        @Override
        public void focusLost(final FocusEvent e) {
            final JTextField temp = (JTextField)e.getSource();
            temp.remove(DimensionPanel.this.closelabel);
            temp.updateUI();
            temp.repaint();
        }
    }
}

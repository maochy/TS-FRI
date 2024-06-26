package UI;

import source.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DoubleList extends JPanel
{
    private JList sourceList;
    private SortedListModel sourceListModel;
    private JList destList;
    private SortedListModel destListModel;
    private JButton addButton;
    private JButton removeButton;
    private String times;
    
    public DoubleList() {
        this.initScreen();
    }
    
    public void clearSourceListModel() {
        this.sourceListModel.clear();
    }
    
    public void clearDestinationListModel() {
        this.destListModel.clear();
    }
    
    public void addSourceElements(final ListModel newValue) {
        this.fillListModel(this.sourceListModel, newValue);
    }
    
    public void setSourceElements(final ListModel newValue) {
        this.clearSourceListModel();
        this.addSourceElements(newValue);
    }
    
    public void addDestinationElements(final ListModel newValue) {
        this.fillListModel(this.destListModel, newValue);
    }
    
    private void fillListModel(final SortedListModel model, final ListModel newValues) {
        for (int size = newValues.getSize(), i = 0; i < size; ++i) {
            model.add(newValues.getElementAt(i));
        }
    }
    
    public void addSourceElements(final Object[] newValue) {
        this.fillListModel(this.sourceListModel, newValue);
    }
    
    public void setSourceElements(final Object[] newValue) {
        this.clearSourceListModel();
        this.addSourceElements(newValue);
    }
    
    public void addDestinationElements(final Object[] newValue) {
        this.fillListModel(this.destListModel, newValue);
    }
    
    private void fillListModel(final SortedListModel model, final Object[] newValues) {
        model.addAll(newValues);
    }
    
    private void clearSourceSelected() {
        final Object[] selected = this.sourceList.getSelectedValues();
        for (int i = selected.length - 1; i >= 0; --i) {
            this.sourceListModel.removeElement(selected[i]);
        }
        this.sourceList.getSelectionModel().clearSelection();
    }
    
    private void clearDestinationSelected() {
        final Object[] selected = this.destList.getSelectedValues();
        for (int i = selected.length - 1; i >= 0; --i) {
            this.destListModel.removeElement(selected[i]);
        }
        this.destList.getSelectionModel().clearSelection();
    }
    
    private void initScreen() {
        this.setLayout(new GridLayout(0, 2));
        this.sourceListModel = new SortedListModel();
        (this.sourceList = new JList(this.sourceListModel)).setFont(Source.dimensionFont);
        (this.addButton = new JButton(">>")).addActionListener(new AddListener());
        (this.removeButton = new JButton("<<")).addActionListener(new RemoveListener());
        this.removeButton.setEnabled(false);
        this.destListModel = new SortedListModel();
        (this.destList = new JList(this.destListModel)).setFont(Source.dimensionFont);
        final JPanel leftPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Available Algorithms:");
        label.setFont(Source.dimensionFont);
        leftPanel.add(label, "North");
        leftPanel.add(new JScrollPane(this.sourceList), "Center");
        leftPanel.add(this.addButton, "South");
        final JPanel rightPanel = new JPanel(new BorderLayout());
        label = new JLabel("Selected Algorithms:");
        label.setFont(Source.dimensionFont);
        rightPanel.add(label, "North");
        rightPanel.add(new JScrollPane(this.destList), "Center");
        rightPanel.add(this.removeButton, "South");
        this.add(new JScrollPane(leftPanel));
        this.add(new JScrollPane(rightPanel));
    }
    
    public Object getSelect() {
        if (this.destList.getModel().getSize() == 0) {
            return null;
        }
        final Object selected = this.destList.getModel().getElementAt(0);
        return selected;
    }
    
    public void setLocl(final String method_name) {
        if (this.destList.getModel().getSize() != 0) {
            final Object[] selected = { this.destList.getModel().getElementAt(0) };
            this.addSourceElements(selected);
            this.destListModel.removeElement(this.destList.getModel().getElementAt(0));
            this.destList.getSelectionModel().clearSelection();
            this.removeButton.setEnabled(false);
            this.addButton.setEnabled(true);
        }
        String result = null;
        System.out.println(method_name);
        for (int i = 0; i < Source.algorithm.length; ++i) {
            if (Source.algorithm[i].equals(method_name)) {
                result = Source.algorithm[i];
            }
        }
        final Object[] selected2 = { result };
        this.addDestinationElements(selected2);
        this.sourceListModel.removeElement(result);
        this.sourceList.getSelectionModel().clearSelection();
        this.addButton.setEnabled(false);
        this.removeButton.setEnabled(true);
    }
    
    private class AddListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            final Object[] selected = DoubleList.this.sourceList.getSelectedValues();
            DoubleList.this.addDestinationElements(selected);
            DoubleList.this.clearSourceSelected();
            DoubleList.this.addButton.setEnabled(false);
            DoubleList.this.removeButton.setEnabled(true);
        }
    }
    
    private class RemoveListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            final Object[] selected = DoubleList.this.destList.getSelectedValues();
            DoubleList.this.addSourceElements(selected);
            DoubleList.this.clearDestinationSelected();
            DoubleList.this.removeButton.setEnabled(false);
            DoubleList.this.addButton.setEnabled(true);
        }
    }
}

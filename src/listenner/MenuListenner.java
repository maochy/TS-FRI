package listenner;

import UI.*;
import common.ComparableTestCase;
import program.Shape;
import source.Source;
import thread.FSCSThread;
import thread.GetMessage;
import thread.GetRestart;
import thread.Identifity;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MenuListenner implements ActionListener
{
    MainJFram mainJFram;
    CardLayout card;
    static JPanel current;
    JTextArea area;
    JPanel parent;


    public MenuListenner(MainJFram mainJFram) {
        this.mainJFram = mainJFram;
        this.parent = mainJFram.Central;
        this.card = (CardLayout)parent.getLayout();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JMenuItem temp = (JMenuItem)e.getSource();
        if (temp.getText().equals("Simulation Open")) {
            this.card.show(this.parent, "Simulation");
            current = (JPanel) this.parent.getComponent(0);
        }
        else if (temp.getText().equals("Real Open")) {
            this.card.show(this.parent, "Real Program");
            current = (JPanel) this.parent.getComponent(1);
        }
        else if (temp.getText().equals("Run")) {
            if (current instanceof SimulationJPanel){

                final DoubleList list = SimulationJPanel.EAST;
                final String obj = (String)list.getSelect();
                final String information = this.ShowInformation(obj, this.parent);
                System.out.println("information " + information);
                if (information != null && obj != null) {
                    final TabbedPane tab = SimulationJPanel.tab;
                    System.out.println(tab.getTabCount());
                    if (tab.getTabCount() == 0) {
                        SimulationJPanel.endbutton.setEnabled(true);
                        SimulationJPanel.suspend.setEnabled(true);
                    }
                    this.area = ((SimulationJPanel) current).firstCausingTest;
                    String[] point = this.area.getText().split("\r\n");
                    final JTextArea area = tab.add();
                    final ComparableTestCase firstcase = new ComparableTestCase();
                    for (int i = 0; i < point.length; ++i) {
                        firstcase.list.add(Double.valueOf(point[i]));
                    }
                    System.out.println(FSCSThread.FSCS.getShape().dimList);
                    System.out.println("FSCS" + FSCSThread.FSCS.getShape().dimList.get(0).getMax() + " " + FSCSThread.FSCS.getShape().dimList.get(0).getMin());
                    try {
                        new Identifity(obj, FSCSThread.FSCS.getShape(), new ArrayList<ComparableTestCase>(), firstcase, Integer.valueOf(information), area).execute();
                    }
                    catch (Exception e2) {
                        JOptionPane.showMessageDialog(this.parent, "Error Operation");
                    }
                }
            }
            else if (current instanceof RealJPanel) {
                RealJPanel realJPanel = (RealJPanel) current;
                String obj = (String) realJPanel.alg.getSelectedItem();
                String information = this.ShowInformation(obj, this.parent);
                System.out.println("information " + information);
                if (information != null && obj != null) {
                    TabbedPane tab = realJPanel.tab;
                    System.out.println(tab.getTabCount());
                    if (tab.getTabCount() == 0) {
                        realJPanel.endbutton.setEnabled(true);
                        realJPanel.suspend.setEnabled(true);
                    }
                    this.area = realJPanel.firstCausingTest;
                    final JTextArea area = tab.add();
                    final String[] point = this.area.getText().split("\r\n");
                    final ComparableTestCase firstcase = new ComparableTestCase();
                    for (int i = 0; i < point.length; ++i) {
                        firstcase.list.add(Double.valueOf(point[i]));
                    }
                    String progName = (String) realJPanel.shapeType.getSelectedItem();
                    Shape shape = null;
                    try {
                        Class clazz = Class.forName("program.Real"+progName);
                        shape = (Shape) clazz.newInstance();
                    } catch (Exception ex) {
                       ex.printStackTrace();

                    }
                    try {
                        new Identifity(obj, shape, new ArrayList<ComparableTestCase>(), firstcase, Integer.valueOf(information), area).execute();
                    }
                    catch (Exception e2) {
                        JOptionPane.showMessageDialog(this.parent, "Error Operation");
                    }
                }
            }

        }
        else if (temp.getText().equals("Restart")) {
            final JFileChooser fd = new JFileChooser(Source.savaPath);
            fd.setFileFilter(new JAVAFileFilter("xml"));
            fd.showDialog(new JLabel(), "\u9009\u62e9");
            final String filePath = fd.getSelectedFile().getAbsolutePath();
            SimulationJPanel.tab.removeAll();
            new GetRestart(filePath, this.mainJFram).execute();
        }
        else if (temp.getText().equals("Show")) {
            (this.card = (CardLayout)this.mainJFram.Central.getLayout()).show(this.parent, "Show Figure");
            final JFileChooser fd = new JFileChooser(Source.savaPath);
            fd.setFileFilter(new JAVAFileFilter("xml"));
            fd.showDialog(new JLabel(), "\u9009\u62e9");
            if (fd.getSelectedFile() != null) {
                final String filePath = fd.getSelectedFile().getAbsolutePath();
                new GetMessage(filePath, this.mainJFram.showFigure).execute();
            }
            current = (JPanel) this.parent.getComponent(2);
        }
    }

    private String ShowInformation(final String name, final JPanel parent) {
        String times = null;
        if (name == null) {
            return null;
        }
        for (int i = 0; i < Source.algorithm.length; ++i) {
            if (name.equals(Source.algorithm[i])) {
                times = JOptionPane.showInputDialog(parent, "Input iteration times:");
            }
        }
        return times;
    }

    class JAVAFileFilter extends FileFilter
    {
        String ext;

        public JAVAFileFilter(final String ext) {
            this.ext = ext;
        }

        @Override
        public boolean accept(final File file) {
            if (file.isDirectory()) {
                return false;
            }
            final String fileName = file.getName();
            final int index = fileName.lastIndexOf(46);
            if (index > 0 && index < fileName.length() - 1) {
                final String extension = fileName.substring(index + 1).toLowerCase();
                if (extension.equals(this.ext)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getDescription() {
            if (this.ext.equals("xml")) {
                return "XML\u6587\u4ef6";
            }
            return "";
        }
    }
}

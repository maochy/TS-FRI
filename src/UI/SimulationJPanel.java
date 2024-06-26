/*
 * Decompiled with CFR 0.150.
 */
package UI;

import listenner.RunAction;
import source.Source;
import utils.AlgorithmParseXML;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SimulationJPanel
extends JPanel {
    public JComboBox shapeType;
    public JTextField dimensonBox;
    public Box box = Box.createHorizontalBox();
    public JTextField failField;
    public JTextArea firstCausingTest;
    public static TabbedPane tab;
    MainJFram mainJFram;
    Mbutton run;
    public static EndButton endbutton;
    public static SuspendButton suspend;
    public static DoubleList EAST;

    public SimulationJPanel(MainJFram mainJFram) throws InterruptedException {
        this.mainJFram = mainJFram;
        JLabel dimension = new JLabel("Dimension:");
        dimension.setFont(Source.mainFont);
        this.dimensonBox = new JTextField(14);
        this.dimensonBox.setFont(Source.comboxFont);
        this.dimensonBox.addKeyListener(new Mouse(this));
        JLabel dimension_tip = new JLabel("  Press Enter");
        dimension_tip.setFont(new Font("Times New Roman",0,16));
        JPanel Input_domain = new JPanel();
        Input_domain.setLayout(new BorderLayout());
        Input_domain.setPreferredSize(new Dimension(500, 310));
        Input_domain.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Input Domain", 1, 2, Source.JPanelTitle));
        Box box1 = Box.createHorizontalBox();
        box1.add(dimension);
        box1.add(Box.createHorizontalStrut(20));
        box1.add(this.dimensonBox);
        JPanel box_Panel = new JPanel();
        box_Panel.setLayout(new BorderLayout());
        box_Panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        box_Panel.add((Component)box1, "West");
        box_Panel.add(dimension_tip);
        Input_domain.add((Component)box_Panel, "North");
        JScrollPane scroll = new JScrollPane(this.box);
        scroll.setBorder(BorderFactory.createEtchedBorder());
        Input_domain.add((Component)scroll, "Center");
        JPanel fail_region = new JPanel();
        fail_region.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fail Region", 1, 2, Source.JPanelTitle));
        fail_region.setPreferredSize(new Dimension(500, 310));
        fail_region.setLayout(new BorderLayout());

        Box box2 = Box.createVerticalBox();
        JLabel type = new JLabel("Shape:");
        type.setFont(Source.dimensionFont);
        this.shapeType = new JComboBox<Object>(new Object[]{"Square", "circular", "Rectangle (only 2,3 dimension)", "ellipse (only 2,3 dimension)"});
        this.shapeType.setFont(Source.comboxFont);
        this.shapeType.setPreferredSize(new Dimension(250, 10));
        this.shapeType.setName("Shape");
        this.shapeType.addActionListener(new ShapeAction(this));
        box1 = Box.createHorizontalBox();
        box1.add(type);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(this.shapeType);
        box2.add(box1);
        box2.add(Box.createVerticalStrut(40));
        box1 = Box.createHorizontalBox();
        type = new JLabel("failRate:");
        type.setFont(Source.dimensionFont);
        this.failField = new JTextField(14);
        this.failField.setPreferredSize(new Dimension(100, 10));
        this.failField.setBounds(50, 50, 150, 20);
        this.failField.setFont(Source.dimensionFont);
        box1.add(type);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(this.failField);
        box2.add(box1);
        box2.add(Box.createVerticalStrut(40));
        JPanel panel = new JPanel();
        panel.add(box2);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        fail_region.add((Component)panel, "West");
        JPanel failCausing = new JPanel(new BorderLayout());
        failCausing.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fail Causing", 1, 2, Source.JPanelTitle));
        JLabel failCausingLable = new JLabel("The way to get the fist failure:");
        failCausingLable.setFont(Source.dimensionFont);
        JPanel causing_label = new JPanel(new FlowLayout(0, 0, 5));
        causing_label.add((Component)failCausingLable, 0);
        causing_label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        JRadioButton firstRadioButton = new JRadioButton("Automatic");
        firstRadioButton.setFont(Source.comboxFont);
        final JRadioButton secondRadioButton = new JRadioButton("Manual");
        secondRadioButton.setFont(Source.comboxFont);
        ButtonGroup Group = new ButtonGroup();
        Group.add(firstRadioButton);
        Group.add(secondRadioButton);
        box1 = Box.createHorizontalBox();
        box1.add(firstRadioButton);
        box1.add(Box.createHorizontalStrut(100));
        box1.add(secondRadioButton);
        box2 = Box.createVerticalBox();
        box2.add(box1);
        box2.add(Box.createVerticalStrut(40));
        box1 = Box.createHorizontalBox();
        failCausingLable = new JLabel("Coordinate:");
        failCausingLable.setFont(Source.dimensionFont);
        this.firstCausingTest = new JTextArea();
        this.firstCausingTest.setFont(Source.dimensionFont);
        this.run = new Mbutton("Run");
        this.run.setFont(Source.buttonFont);
        JPanel runPanel = new JPanel();
        runPanel.setLayout(new BorderLayout());
        runPanel.add((Component)this.run, "East");
        JScrollPane sc = new JScrollPane(this.firstCausingTest);
        sc.setPreferredSize(new Dimension(300, 80));
        box1.add(failCausingLable);
        box1.add(Box.createHorizontalStrut(20));
        box1.add(sc);
        box1.add(Box.createHorizontalStrut(30));
        box2.add(box1);
        box2.add(Box.createVerticalStrut(30));
        box2.add(runPanel);
        JPanel fail_panel = new JPanel();
        fail_panel.add(box2);
        failCausing.add((Component)causing_label, "North");
        failCausing.add((Component)fail_panel, "Center");
        secondRadioButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondRadioButton.isSelected()) {
                    SimulationJPanel.this.run.setEnabled(false);
                }
            }
        });
        firstRadioButton.setSelected(true);
        firstRadioButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationJPanel.this.run.setEnabled(true);
            }
        });
        JPanel NorthPanel = new JPanel();
        NorthPanel.setLayout(new GridLayout(1, 3, 40, 5));
        NorthPanel.add(Input_domain);
        NorthPanel.add(fail_region);
        NorthPanel.add(failCausing);
        EAST = new DoubleList();
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                AlgorithmParseXML px = new AlgorithmParseXML();
                Source.algorithm = px.parseXML();
            }
        });
        thread.start();
        thread.join();

        EAST.addSourceElements(Source.algorithm);
        EAST.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Algorithm", 1, 2, Source.JPanelTitle));
        EAST.setPreferredSize(new Dimension(800, (int)EAST.getPreferredSize().getHeight()));
        JPanel con = new JPanel(new BorderLayout());

        tab = new TabbedPane();
        con.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Console", 1, 2, Source.JPanelTitle));
        tab.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel buttongroup = new JPanel(new FlowLayout(2, 10, 2));
        endbutton = new EndButton();
        buttongroup.add(endbutton);
        suspend = new SuspendButton();
        buttongroup.add(suspend);
        JSeparator sep = new JSeparator(0);
        box1 = Box.createVerticalBox();
        box1.add(buttongroup);
        box1.add(sep);
        con.add((Component)tab, "Center");
        con.add((Component)box1, "North");
        this.run.addActionListener(new RunAction(this));
        this.setLayout(new BorderLayout());
        this.add((Component)NorthPanel, "North");
        this.add((Component)EAST, "West");
        this.add((Component)con, "Center");
    }
}


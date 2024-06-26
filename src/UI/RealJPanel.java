package UI;

import listenner.RunAction;
import source.Source;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RealJPanel extends JPanel
{
    public TabbedPane tab;
    public EndButton endbutton;
    public SuspendButton suspend;
    public JComboBox<Object> shapeType;
    public JComboBox<Object> alg;
    Mbutton run;
    public JTextArea firstCausingTest;

    public RealJPanel() {
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
        Box box1 = Box.createVerticalBox();
        box1.add(buttongroup);
        box1.add(sep);
        con.add((Component)tab, "Center");
        con.add((Component)box1, "North");

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
        Box box2 = Box.createVerticalBox();
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
                    RealJPanel.this.run.setEnabled(false);
                }
            }
        });
        firstRadioButton.setSelected(true);
        firstRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                RealJPanel.this.run.setEnabled(true);
            }
        });
        JPanel NorthPanel = new JPanel();
        NorthPanel.setLayout(new GridLayout(1, 3, 40, 5));

        Box vBox = Box.createVerticalBox();
        JPanel realProgram = new JPanel();
        realProgram.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Real Program", 1, 2, Source.JPanelTitle));
        realProgram.setPreferredSize(new Dimension(500, 310));
        realProgram.setLayout(new BorderLayout());
        JLabel type = new JLabel("Program:");
        type.setFont(Source.dimensionFont);
        this.shapeType = new JComboBox<Object>(new Object[]{"Bessj", "Expint", "Gammq","Triangle"});
        this.shapeType.setFont(Source.comboxFont);
        this.shapeType.setPreferredSize(new Dimension(250, 10));
        this.shapeType.setName("Program Name");
        box1 = Box.createHorizontalBox();
        box1.add(type);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(this.shapeType);
        box2 = Box.createVerticalBox();
        box2.add(Box.createVerticalStrut(40));
        box2.add(box1);
        vBox.add(box2);

        JLabel algLabel = new JLabel("Algorithm:");
        algLabel.setFont(Source.dimensionFont);
        this.alg = new JComboBox<Object>(new Object[]{"DSB", "FSB1", "FSB2", "TSFRI"});
        this.alg.setFont(Source.comboxFont);
        this.alg.setPreferredSize(new Dimension(250, 10));
        this.alg.setName("Algorithm Name");
        box1 = Box.createHorizontalBox();
        box1.add(algLabel);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(this.alg);
        box2 = Box.createVerticalBox();
        box2.add(Box.createVerticalStrut(40));
        box2.add(box1);
        vBox.add(box2);

        JPanel panel = new JPanel();
        panel.add(vBox);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        realProgram.add((Component)panel, "West");
        this.run.addActionListener(new RunAction(this));

        NorthPanel.add(realProgram);
        NorthPanel.add(failCausing);
        this.setLayout(new BorderLayout());
        this.add((Component)con, "Center");
        this.add((Component)NorthPanel, "North");

    }
}

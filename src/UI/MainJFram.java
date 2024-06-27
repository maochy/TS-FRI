package UI;

import listenner.MenuListenner;
import source.Source;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainJFram extends JFrame
{
    private final JMenuItem realRun;
    public RealJPanel realJpane;
    JToolBar toolbar;
    public JPanel Central;
    public JPanel showFigure;
    JMenuItem simulationShow;
    JMenuItem realShow;
    JMenuItem simulationRun;
    JMenuItem simulationRestart;
    JMenuItem show;
    public SimulationJPanel simulation;
    
    public MainJFram() throws InterruptedException {
        this.showFigure = new JPanel();
        this.setTitle("Failure Region Identification Tool");
        Source.savaPath = "./graph/";
        File saveFile = new File(Source.savaPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        final JMenuBar mainBar = new JMenuBar();
        final JMenu simulation = new JMenu("Simulation(S)");
        simulation.setFont(Source.mainFont);
        simulation.setHorizontalAlignment(0);
        (this.simulationShow = new JMenuItem("Simulation Open")).setFont(Source.mainFont);
        this.simulationShow.setPreferredSize(new Dimension(200, this.simulationShow.getPreferredSize().height));
        (this.simulationRun = new JMenuItem("Run")).setFont(Source.mainFont);
        this.simulationRun.setPreferredSize(new Dimension(200, this.simulationRun.getPreferredSize().height));
        (this.simulationRestart = new JMenuItem("Restart")).setFont(Source.mainFont);
        this.simulationRestart.setPreferredSize(new Dimension(200, this.simulationRestart.getPreferredSize().height));
        (this.show = new JMenuItem("Show")).setFont(Source.mainFont);
        this.show.setPreferredSize(new Dimension(200, this.show.getPreferredSize().height));
        simulation.add(this.simulationShow);
        simulation.add(this.simulationRun);
        simulation.add(this.simulationRestart);
        simulation.add(this.show);
        final JMenu real = new JMenu("Real Program(R)");
        real.setFont(Source.mainFont);
        real.setHorizontalAlignment(0);
        (this.realShow = new JMenuItem("Real Open")).setFont(Source.mainFont);
        this.realShow.setPreferredSize(new Dimension(200, this.realShow.getPreferredSize().height));
        (this.realRun = new JMenuItem("Run")).setFont(Source.mainFont);
        this.realRun.setPreferredSize(new Dimension(200, this.realRun.getPreferredSize().height));
        real.add(this.realShow);
        real.add(this.realRun);
        mainBar.add(simulation);
        mainBar.add(real);
        (this.Central = new JPanel()).setLayout(new CardLayout());
        this.showFigure.setLayout(new BorderLayout());
        this.showFigure.setPreferredSize(new Dimension(400, 400));
        this.simulation = new SimulationJPanel(this);
        this.realJpane = new RealJPanel();
        this.Central.add(this.simulation, "Simulation");
        this.Central.add(realJpane, "Real Program");
        this.Central.add(this.showFigure, "Show Figure");
        this.simulationShow.addActionListener(new MenuListenner(this));
        this.realShow.addActionListener(new MenuListenner(this));
        this.simulationRun.addActionListener(new MenuListenner(this));
        this.simulationRestart.addActionListener(new MenuListenner(this));
        this.show.addActionListener(new MenuListenner(this));
        this.realRun.addActionListener(new MenuListenner(this));
        this.setJMenuBar(mainBar);
        this.getContentPane().add(this.Central, "Center");
        final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int)screensize.getWidth();
        final int height = (int)screensize.getHeight();
        this.setDefaultCloseOperation(3);
        this.setSize(1700, 1000);
        this.setLocation(width / 2 - this.getWidth() / 2, 0);
        this.setVisible(true);
    }
    
    public static void main(final String[] args) throws InterruptedException {
        new MainJFram();
    }
}

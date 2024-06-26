package thread;

import model.*;
import utils.*;
import UI.*;
import source.*;
import java.util.concurrent.*;
import javax.swing.*;

public class GetRestart extends SwingWorker<SaveObject, String>
{
    String filePath;
    MainJFram mainJFram;
    
    public GetRestart(final String filePath, final MainJFram mainJFram) {
        this.filePath = filePath;
        this.mainJFram = mainJFram;
    }
    
    @Override
    protected SaveObject doInBackground() throws Exception {
        final GraphParseXML xml = new GraphParseXML();
        final SaveObject obj = xml.parseXML(this.filePath);
        return obj;
    }
    
    @Override
    protected void done() {
        if (this.mainJFram.simulation.box.getComponentCount() != 0) {
            this.mainJFram.simulation.box.removeAll();
        }
        try {
            final SaveObject obj = (SaveObject)this.get();
            this.mainJFram.simulation.dimensonBox.setText(String.valueOf(obj.getShape().dimList.size()));
            final DimensionPanel dim = new DimensionPanel(obj.getShape().getDimList());
            this.mainJFram.simulation.box.add(dim);
            this.mainJFram.simulation.revalidate();
            final String name;
            switch (name = obj.getShape().getClass().getSimpleName()) {
                case "product_circle": {
                    this.mainJFram.simulation.shapeType.setSelectedIndex(1);
                    break;
                }
                case "product_squear": {
                    this.mainJFram.simulation.shapeType.setSelectedIndex(0);
                    break;
                }
                case "product_ellipse": {
                    this.mainJFram.simulation.shapeType.setSelectedIndex(3);
                    break;
                }
                case "product_rectangle": {
                    this.mainJFram.simulation.shapeType.setSelectedIndex(2);
                    break;
                }
                default:
                    break;
            }
            this.mainJFram.simulation.failField.setText(String.valueOf(obj.getShape().getFailRate()));
            final StringBuffer buff = new StringBuffer();
            for (final double a : obj.getFirstcase().list) {
                buff.append(String.valueOf(a) + "\r\n");
            }
            this.mainJFram.simulation.firstCausingTest.setText(buff.toString());
            final SimulationJPanel simulation = this.mainJFram.simulation;
            SimulationJPanel.EAST.setLocl(obj.getMethodName());
            double totalsize = 1.0;
            for (int i = 0; i < obj.getShape().getDimList().size(); ++i) {
                totalsize *= obj.getShape().getDimList().get(i).getRange();
            }
            final SimulationJPanel simulation2 = this.mainJFram.simulation;
            final JTextArea area = SimulationJPanel.tab.add();
            area.setFont(Source.mainFont);
            area.setText("Restart program successful!\r\n");
            area.append("The last round of results:" + obj.getTotalSize() + "\r\n");
            area.append("The last round of ratio:" + obj.getTotalSize() / (obj.getShape().getFailRate() * totalsize) + "\r\n");
            area.append("____________________________________________________________________\r\n");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e2) {
            e2.printStackTrace();
        }
    }
    
    private String ShowInformation(final String name, final MainJFram parent) {
        String times = null;
        for (int i = 0; i < Source.algorithm.length; ++i) {
            if (name.equals(Source.algorithm[i])) {
                times = JOptionPane.showInputDialog(parent, "Input iteration times:");
            }
        }
        return times;
    }
}

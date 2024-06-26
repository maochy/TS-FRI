package listenner;

import java.awt.event.*;
import java.util.*;
import common.*;
import javax.swing.*;
import UI.*;
import experiment.RealProgramExperiment;
import thread.*;

public class RunAction implements ActionListener
{
    JPanel panel;
    String failShape;
    double failRate;
    double compactness;
    double orientation;
    
    public RunAction(final JPanel panel) {
        this.panel = panel;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (panel instanceof SimulationJPanel) {
            SimulationJPanel sPanel = (SimulationJPanel) panel;
            final DimensionPanel dimensionPanel = (DimensionPanel)sPanel.box.getComponent(0);
            final ArrayList<JTextField> Max = dimensionPanel.getmaxArraylist();
            final ArrayList<JTextField> Min = dimensionPanel.getminArraylist();
            final ArrayList<Dimension> dimList = new ArrayList<Dimension>();
            for (int i = 0; i < Max.size(); ++i) {
                final Dimension dim = new Dimension(Double.valueOf(Min.get(i).getText()), Double.valueOf(Max.get(i).getText()));
                dimList.add(dim);
            }
            if (sPanel.shapeType.getSelectedIndex() > 1) {
                if (((String)sPanel.shapeType.getSelectedItem()).contains("Rectangle")) {
                    this.failShape = "Rectangle";
                }
                else if (((String)sPanel.shapeType.getSelectedItem()).contains("ellipse")) {
                    this.failShape = "ellipse";
                }
            }
            else {
                this.failShape = (String)sPanel.shapeType.getSelectedItem();
            }
            this.failRate = Double.valueOf(sPanel.failField.getText());
            this.compactness = MyDialog.compactness;
            this.orientation = MyDialog.orientation;
            new FSCSThread(dimList, this.failRate, this.compactness, this.orientation, this.failShape, sPanel.firstCausingTest).execute();
        }
        else if (panel instanceof RealJPanel) {
            //TODO coding
            RealJPanel rPanel = (RealJPanel) panel;
            List<ComparableTestCase> list = null;
            String progName = (String) rPanel.shapeType.getSelectedItem();
            if (progName.equals("Bessj")) {
                list = RealProgramExperiment.bessjFirstcases;
            }
            else if (progName.equals("Expint")) {
                list = RealProgramExperiment.expintFirstcases;
            }
            else if (progName.equals("Gammq")) {
                list = RealProgramExperiment.gammqFirstcases;
            }
            else if (progName.equals("Triangle")) {
                list = RealProgramExperiment.triangleFirstcases;
            }
            rPanel.firstCausingTest.setText("");
            ComparableTestCase teseCase = list.get(new Random().nextInt(list.size()));
            for(Double value: teseCase.list){
                rPanel.firstCausingTest.append(Double.toString(value)+"\r\n");
            }
        }

    }
}

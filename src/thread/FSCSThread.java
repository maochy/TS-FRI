package thread;

import common.*;
import javax.swing.*;
import fscs.*;
import java.util.*;
import java.util.concurrent.*;

public class FSCSThread extends SwingWorker<Integer, String>
{
    private ArrayList<Dimension> list;
    private Double failrate;
    private Double Compactness;
    private Double orientation;
    private String type;
    ComparableTestCase temp;
    JTextArea firstCausingTest;
    public static FSCSARTSequare FSCS;
    
    public FSCSThread(final ArrayList<Dimension> list, final Double failRate, final Double Compactness, final Double orientation, final String type, final JTextArea firstCausingTest) {
        this.list = list;
        this.failrate = failRate;
        this.Compactness = Compactness;
        this.orientation = orientation;
        this.type = type;
        this.firstCausingTest = firstCausingTest;
    }
    
    @Override
    protected Integer doInBackground() throws Exception {
        System.out.println(this.Compactness + " " + this.orientation + " " + this.type);
        FSCSThread.FSCS = new FSCSARTSequare(this.list, this.failrate, this.Compactness, this.orientation, this.type);
        this.temp = FSCSThread.FSCS.run();
        return 1;
    }
    
    @Override
    protected void process(final List<String> list) {
    }
    
    @Override
    protected void done() {
        try {
            this.firstCausingTest.setText("");
            if (((Integer)this.get()).equals(1)) {
                for (int i = 0; i < this.temp.list.size(); ++i) {
                    this.firstCausingTest.append(this.temp.list.get(i) + "\r\n");
                }
            }
            super.done();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e2) {
            e2.printStackTrace();
        }
    }
}

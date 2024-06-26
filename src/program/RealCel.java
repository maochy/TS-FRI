package program;

import com.sun.jna.Library;
import com.sun.jna.Native;
import common.Dimension;
import common.ComparableTestCase;
import experiment.RealProgramFaultDetect;

import java.util.ArrayList;

public class RealCel extends Shape {

    public RealCel(){
        dimList=new ArrayList<>();
        dimList.add(new Dimension(0.001,1));
        dimList.add(new Dimension(0.001,1));
        dimList.add(new Dimension(0.001,1));
        dimList.add(new Dimension(0.001,1));
        failTotalSize = 0.09871;
    }

    public interface Cel extends Library {
        Cel celINSTANCE = (Cel) Native.loadLibrary(path+"\\cel", Cel.class);
        boolean Produces_Error(double x,double y,double z,double w);
    }


    @Override
    public void product() {
        ArrayList<Dimension> d = new ArrayList<Dimension>();
        d.add(new Dimension(0.001,1.0));
        d.add(new Dimension(0.001,300.0));
        d.add(new Dimension(0.001,1.0));
        d.add(new Dimension(0.001,1.0));
        this.dimList = d;
    }

    @Override
    public boolean isCorrect(ComparableTestCase p) {
        this.time++;
        this.cost++;
        long temp_time = System.nanoTime();
        boolean result = !RealProgramFaultDetect.celError(p.list.get(0),p.list.get(1),p.list.get(2),p.list.get(3));
        this.realtime+=System.nanoTime()-temp_time;
        return result;
    }
}

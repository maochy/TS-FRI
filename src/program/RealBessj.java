package program;

import com.sun.jna.Library;
import com.sun.jna.Native;
import common.Dimension;
import common.ComparableTestCase;
import experiment.RealProgramFaultDetect;

import java.util.ArrayList;

public class RealBessj extends Shape {


    public RealBessj() {
        dimList = new ArrayList<>();
        dimList.add(new Dimension(2,300));
        dimList.add(new Dimension(0,1500));
        failTotalSize = 3118.8694;
    }

    public interface Bessj extends Library {
        Bessj bessjINSTANCE = (Bessj) Native.loadLibrary(path+"\\bessj", Bessj.class);
        boolean Produces_Error(double x,double y);
    }

    @Override
    public void product() {
        ArrayList<Dimension> d = new ArrayList<Dimension>();
        d.add(new Dimension(2,300));
        d.add(new Dimension(0,1500));
        this.dimList = d;
    }

    @Override
    public boolean isCorrect(ComparableTestCase p) {
        this.time++;
        this.cost++;
        long temp_time = System.nanoTime();
        boolean result = !RealProgramFaultDetect.bessjError(p.list.get(0),p.list.get(1));
        this.realtime+=System.nanoTime()-temp_time;
        return result;
    }
}

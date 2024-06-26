package program;

import com.sun.jna.Library;
import com.sun.jna.Native;
import common.Dimension;
import common.ComparableTestCase;
import experiment.RealProgramFaultDetect;

import java.util.ArrayList;

public class RealAiry extends Shape{

    public RealAiry(){
        dimList = new ArrayList<>();
        dimList.add(new Dimension(-5000,5000));
        failTotalSize = 7;
    }

    public interface Airy extends Library {
        Airy airINSTANCE = (Airy) Native.loadLibrary(path+"\\airy", Airy.class);
        boolean Produces_Error(double x);
    }

    @Override
    public void product() {
        ArrayList<Dimension> d = new ArrayList<Dimension>();
        d.add(new Dimension(-5000,5000));
        this.dimList = d;
    }

    @Override
    public boolean isCorrect(ComparableTestCase p) {
        this.time++;
        this.cost++;
        long tempTime = System.nanoTime();
        boolean result = !RealProgramFaultDetect.airyError(p.list.get(0));
        this.realtime+=System.nanoTime()-tempTime;
        return result;
    }
}

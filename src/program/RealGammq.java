package program;

import com.sun.jna.Library;
import com.sun.jna.Native;
import common.Dimension;
import common.ComparableTestCase;
import experiment.RealProgramFaultDetect;

import java.util.ArrayList;

public class RealGammq extends Shape{


    public RealGammq(){
        dimList = new ArrayList<>();
        dimList.add(new Dimension(0,1700));
        dimList.add(new Dimension(0,40));
        failTotalSize = 57.375;
    }

    public interface Gammq extends Library {
        Gammq gammqINSTANCE = (Gammq) Native.loadLibrary(path+"\\gammq", Gammq.class);
        boolean Produces_Error(double x,double y);
    }


    @Override
    public void product() {
        ArrayList<Dimension> d = new ArrayList<Dimension>();
        d.add(new Dimension(0,1700));
        d.add(new Dimension(0,40));
        this.dimList = d;
    }

    @Override
    public boolean isCorrect(ComparableTestCase p) {
        this.time++;
        this.cost++;
        long temp_time = System.nanoTime();
        boolean result = !RealProgramFaultDetect.gammqError(p.list.get(0),p.list.get(1));
        this.realtime+=System.nanoTime()-temp_time;
        return result;
    }
}

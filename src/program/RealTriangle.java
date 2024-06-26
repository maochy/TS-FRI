package program;

import common.Dimension;
import common.ComparableTestCase;
import experiment.RealProgramFaultDetect;

import java.util.ArrayList;

public class RealTriangle extends Shape {

    public RealTriangle() {
        dimList = new ArrayList<>();
        dimList.add(new Dimension(0, 255));
        dimList.add(new Dimension(0, 255));
        dimList.add(new Dimension(0, 255));
        failTotalSize = 8290687.5;
    }

    public int exe(double a, double b, double c)
    {
        if (a > b)
        { double tmp = a; a = b; b = tmp; }

        if (a > c)
        { double tmp = a; a = c; c = tmp; }

        if (b > c)
        { double tmp = b; b = c; c = tmp; }

        if(c >= a+b)
            return 1;
        else
        {
            if(a == b && b == c)
                return 4;
            else if(a == b  || b == c)
                return 3;
            else
                return 2;
        }
    }


    public int exeMutant( double a, double b, double c )
    {
        if (a > b) {
            double tmp = a;
            a = b;
            b = tmp;
        }
        if (a > c) {
            double tmp = a;
            a = c;
            c = tmp;
        }
        if (b > c) {
            double tmp = b;
            b = c;
            c = tmp;
        }
        if (c >= a + b) {
            return 1;
        } else {
            if (a == b && b == c) {
                return 4;
            } else {
                if (!(a == b) || b == c) {
                    return 3;
                } else {
                    return 2;
                }
            }
        }
    }

    @Override
    public void product() {
        ArrayList<Dimension> d = new ArrayList<Dimension>();
        d.add(new Dimension(0,255));
        d.add(new Dimension(0,255));
        d.add(new Dimension(0,255));
        this.dimList = d;
    }

    @Override
    public boolean isCorrect(ComparableTestCase p) {
        this.time++;
        this.cost++;
        long temp_time = System.nanoTime();
        boolean result = !RealProgramFaultDetect.triangleError(p.list.get(0),p.list.get(1),p.list.get(2));
        this.realtime+=System.nanoTime()-temp_time;
        return result;
    }
}

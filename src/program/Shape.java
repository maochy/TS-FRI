package program;

import java.util.*;
import common.*;

public abstract class Shape
{
    double orientation;
    double Compactness;
    public ArrayList<Dimension> dimList;
    public ArrayList<ArrayList<Double>> mutiple;
    double failRate;
    public int cost = 0;
    public int time = 0;
    public long realtime = 0;
    public ArrayList<Integer> allTime;
    double failTotalSize;
    static String path = System.getProperty("sun.arch.data.model").equals("64")?
            System.getProperty("user.dir")+"\\dllProgram\\x64":System.getProperty("user.dir")+"\\dllProgram";
    public Shape() {
        this.orientation = 0.0;
        this.Compactness = 0.0;
        this.mutiple = new ArrayList<ArrayList<Double>>();
        this.allTime = new ArrayList<Integer>();
    }
    
    public abstract void product();
    
    public abstract boolean isCorrect(final ComparableTestCase p0);
    
    public double getorientation() {
        return this.orientation;
    }
    
    public void setOrientation(final double orientation) {
        this.orientation = orientation;
    }
    
    public double getCompactness() {
        return this.Compactness;
    }
    
    public void setCompactness(final double compactness) {
        this.Compactness = compactness;
    }
    
    public double getFailRate() {
        return this.failRate;
    }
    
    public void setFailRate(final double failRate) {
        this.failRate = failRate;
    }
    
    public ArrayList<Dimension> getDimList() {
        return this.dimList;
    }
    
    public void setDimList(final ArrayList<Dimension> dimList) {
        this.dimList = dimList;
    }
    
    public ArrayList<ArrayList<Double>> getMutiple() {
        return this.mutiple;
    }

    public double getFailTotalSize() {
        return failTotalSize;
    }

    public void setFailTotalSize(double failTotalSize) {
        this.failTotalSize = failTotalSize;
    }

    public void setMutiple(final ArrayList<ArrayList<Double>> mutiple) {
        this.mutiple = mutiple;
    }
}

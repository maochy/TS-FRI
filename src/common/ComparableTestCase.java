package common;

import java.util.*;

public class ComparableTestCase implements Comparable<ComparableTestCase>
{
    public ArrayList<Double> list;
    public boolean rebound;
    public double increment;
    public int way;
    public int times;
    public double degrees;
    
    public ComparableTestCase(final double increment) {
        this.list = new ArrayList<Double>();
        this.times = 0;
        this.increment = increment;
        this.rebound = false;
    }
    
    public ComparableTestCase() {
        this.list = new ArrayList<Double>();
        this.times = 0;
        this.increment = 0;
        this.rebound = false;
    }
    
    public ArrayList<Double> getList() {
        return this.list;
    }
    
    public void setList(final ArrayList<Double> list) {
        this.list = list;
    }
    
    public boolean isRebound() {
        return this.rebound;
    }
    
    public void setRebound(final boolean rebound) {
        this.rebound = rebound;
    }
    
    public double getIncrement() {
        return this.increment;
    }
    
    public void setIncrement(final double increment) {
        this.increment = increment;
    }
    
    public int getWay() {
        return this.way;
    }
    
    public void setWay(final int way) {
        this.way = way;
    }
    
    public int getTimes() {
        return this.times;
    }
    
    public void setTimes(final int times) {
        this.times = times;
    }
    
    @Override
    public String toString() {
        final String a = "\u5750\u6807" + this.list;
        return a;
    }
    
    @Override
    public boolean equals(final Object obj) {
        boolean flag = true;
        if (obj instanceof ComparableTestCase) {
            for (int i = 0; i < this.list.size(); ++i) {
                if (!this.list.get(i).equals(((ComparableTestCase)obj).getList().get(i))) {
                    flag = false;
                    break;
                }
            }
        }
        else {
            flag = false;
        }
        return flag;
    }

    public ComparableTestCase add(final ComparableTestCase other) {
        final ComparableTestCase temp = new ComparableTestCase(0.0);
        temp.list.add(this.list.get(0) + other.list.get(0));
        temp.list.add(this.list.get(1) + other.list.get(1));
        temp.list.add(this.list.get(2) + other.list.get(2));
        return temp;
    }
    
    public TestCase transmit() {
        final TestCase result = new TestCase(-1.0);
        result.list.addAll(this.list);
        return result;
    }

    @Override
    public int compareTo(ComparableTestCase other) {
        if(Math.abs(other.degrees-degrees)<1e-12)
            return 0;
        else if(other.degrees>degrees)
            return -1;
        else
            return 1;
    }

}

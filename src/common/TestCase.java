package common;

import java.util.*;

public class TestCase
{
    public ArrayList<Double> list;
    public boolean rebound;
    public double increment;
    public String way;
    public int times;
    
    public TestCase(final double increment) {
        this.list = new ArrayList<Double>();
        this.times = 0;
        this.increment = increment;
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
    
    public String getWay() {
        return this.way;
    }
    
    public void setWay(final String way) {
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
        final String a = "\u5750\u6807" + this.list + ",\u662f\u5426\u56de\u7f29+" + this.rebound + ",\u957f\u5ea6+" + this.increment + "\u6b21\u6570" + this.times + "\u65b9\u5411:" + this.way;
        return a;
    }
    
    @Override
    public boolean equals(final Object obj) {
        boolean flag = true;
        if (obj instanceof TestCase) {
            for (int i = 0; i < this.list.size(); ++i) {
                if (!this.list.get(i).equals(((TestCase)obj).getList().get(i))) {
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
    

    public TestCase add(final TestCase other) {
        final TestCase temp = new TestCase(0.0);
        temp.list.add(this.list.get(0) + other.list.get(0));
        temp.list.add(this.list.get(1) + other.list.get(1));
        temp.list.add(this.list.get(2) + other.list.get(2));
        return temp;
    }
    
    public ComparableTestCase transmit() {
        final ComparableTestCase result = new ComparableTestCase();
        result.list.addAll(this.list);
        return result;
    }
}

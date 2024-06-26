package program;

import java.util.*;
import common.*;

public class ProductSquear extends Shape
{
    double totalSize;
    double failLength;
    int failNumber;
    double failTotalSize;
    
    public ProductSquear(final ArrayList<Dimension> dimList, final double failrate) {
        this.totalSize = dimList.get(0).getRange();
        for (int i = 1; i < dimList.size(); ++i) {
            this.totalSize *= dimList.get(i).getRange();
        }
        this.dimList = dimList;
        this.failRate = failrate;
        this.failTotalSize = failrate * this.totalSize;
        this.failNumber = 1;
        this.failLength = this.sqrt(this.failTotalSize / this.failNumber, dimList.size());
    }
    
    @Override
    public void product() {
        this.mutiple = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> templist = new ArrayList<Double>();
        for (int j = 0; j < this.dimList.size(); ++j) {
            final double temp = this.dimList.get(j).getMin() + (this.dimList.get(j).getRange() - this.failLength) * new Random().nextDouble();
            templist.add(temp);
        }
        this.mutiple.add(templist);
        for (int i = 0; i < this.failNumber - 1; ++i) {
            templist.clear();
            for (int k = 0; k < this.dimList.size(); ++k) {
                final double temp2 = this.dimList.get(k).getMin() + (this.dimList.get(k).getRange() - this.failLength) * new Random().nextDouble();
                templist.add(temp2);
            }
            while (this.fail1(templist)) {
                templist = new ArrayList<Double>();
                for (int k = 0; k < this.dimList.size(); ++k) {
                    final double temp2 = this.dimList.get(k).getMin() + (this.dimList.get(k).getRange() - this.failLength) * new Random().nextDouble();
                    templist.add(temp2);
                }
            }
            this.mutiple.add(templist);
        }
    }
    
    double sqrt(final double d, double i) {
        i = 1.0 / i;
        return Math.pow(d, i);
    }
    
    boolean fail1(final ArrayList<Double> templist) {
        for (int i = 0; i < this.mutiple.size(); ++i) {
            if (this.ex(this.mutiple.get(i), templist, this.failLength)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean ex(final ArrayList<Double> list1, final ArrayList<Double> list2, final Double r) {
        for (int i = 0; i < list1.size(); ++i) {
            if (Math.abs(list1.get(i) - list2.get(i)) > r) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isCorrect(final ComparableTestCase testcase) {
        this.time++;
        final boolean jutice = true;
        for (int i = 0; i < this.mutiple.size(); ++i) {
            final ArrayList<Double> temp = this.mutiple.get(i);
            boolean flag = false;
            for (int j = 0; j < temp.size(); ++j) {
                if (testcase.list.get(j) < temp.get(j) || testcase.list.get(j) > temp.get(j) + this.failLength) {
                    flag = true;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return jutice;
    }
}

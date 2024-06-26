package fscs;

import common.*;
import program.*;
import java.util.*;

public class FSCSARTSequare
{
    ArrayList<Dimension> dimList;
    ArrayList<ComparableTestCase> candidate;
    ArrayList<ComparableTestCase> Selected;
    Shape shape;
    
    public Shape getShape() {
        return this.shape;
    }
    
    public void setShape(final Shape shape) {
        this.shape = shape;
    }
    
    public FSCSARTSequare(final ArrayList<Dimension> dimList, final double failrate, final double Compactness, final double orientation, final String type) {
        this.candidate = new ArrayList<ComparableTestCase>();
        if (type.equals("Square")) {
            (this.shape = new ProductSquear(dimList, failrate)).product();
        }
        else if (type.equals("circular")) {
            (this.shape = new ProductCircle(dimList, failrate)).product();
        }
        else if (type.equals("Rectangle")) {
            (this.shape = new ProductRectangle(dimList, failrate, Compactness, orientation)).product();
            //System.out.println("\u5df2\u7ecf\u751f\u6210\u4e86Rectangle");
        }
        else if (type.equals("ellipse")) {
            (this.shape = new ProductEllipse(dimList, failrate, Compactness, orientation)).product();
        }
        this.dimList = dimList;
    }
    
    public ComparableTestCase run() {
        int count = 1;
        ComparableTestCase testcase = new ComparableTestCase();
        for (int i = 0; i < this.dimList.size(); ++i) {
            testcase.list.add(this.dimList.get(i).getMin() + this.dimList.get(i).getRange() * new Random().nextDouble());
        }
        this.Selected = new ArrayList<ComparableTestCase>();
        while (this.shape.isCorrect(testcase)) {
            this.Selected.add(testcase);
            ++count;
            this.candidate.clear();
            for (int i = 0; i < 10; ++i) {
                final ComparableTestCase temp = new ComparableTestCase();
                for (int j = 0; j < this.dimList.size(); ++j) {
                    temp.list.add(this.dimList.get(j).getMin() + this.dimList.get(j).getRange() * new Random().nextDouble());
                }
                this.candidate.add(temp);
            }
            testcase = this.Best_candidate(this.Selected, this.candidate);
        }
        return testcase;
    }
    
    private double Euclidean_Distance(final ArrayList<Double> arrayList, final ArrayList<Double> templist) {
        double sum = 0.0;
        for (int i = 0; i < arrayList.size(); ++i) {
            sum += Math.pow(arrayList.get(i) - templist.get(i), 2.0);
        }
        return Math.sqrt(sum);
    }
    
    private double Euclidean_Distance(final ComparableTestCase testcase, final ComparableTestCase testcase2) {
        double sum = 0.0;
        for (int i = 0; i < testcase.list.size(); ++i) {
            sum += Math.pow(testcase.list.get(i) - testcase2.list.get(i), 2.0);
        }
        return Math.sqrt(sum);
    }
    
    static double sqrt(final double d, double i) {
        i = 1.0 / i;
        return Math.pow(d, i);
    }
    
    public ComparableTestCase Best_candidate(final ArrayList<ComparableTestCase> e, final ArrayList<ComparableTestCase> c) {
        final ComparableTestCase p = null;
        double maxmin = 0.0;
        int cixu = -1;
        for (int i = 0; i < c.size(); ++i) {
            double mindist = 12000.0;
            for (int j = 0; j < e.size(); ++j) {
                final double dist = this.Euclidean_Distance(c.get(i), e.get(j));
                if (dist < mindist) {
                    mindist = dist;
                }
            }
            if (maxmin < mindist) {
                maxmin = mindist;
                cixu = i;
            }
        }
        return c.get(cixu);
    }
}

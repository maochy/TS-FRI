package program;

import common.*;
import java.util.*;

public class ProductEllipse extends Shape
{
    double totalsize;
    public ArrayList<Double> fail_point;
    public double c;
    double a_and_b;
    public Double R;
    
    public ProductEllipse(final ArrayList<Dimension> dimList, final double failrate, final double Compactness, final double orientation) {
        this.fail_point = new ArrayList<Double>();
        this.totalsize = dimList.get(0).getRange();
        this.dimList = dimList;
        for (int i = 1; i < dimList.size(); ++i) {
            this.totalsize *= dimList.get(i).getRange();
        }
        this.failRate = failrate;
        this.failTotalSize = this.totalsize * failrate;
        this.orientation = orientation;
        this.Compactness = Compactness;
        switch (dimList.size()) {
            case 2: {
                final double ab = this.failTotalSize / 3.141592653589793;
                this.c = sqrt(ab / Math.pow(Compactness, 1.0), dimList.size());
                this.a_and_b = Compactness * this.c;
                break;
            }
            case 3: {
                final double ab = 3.0 * this.failTotalSize / 12.566370614359172;
                this.c = sqrt(ab / Math.pow(Compactness, 2.0), dimList.size());
                this.a_and_b = Compactness * this.c;
                break;
            }
            case 4: {
                final double ab = 2.0 * this.failTotalSize / 9.869604401089358;
                this.c = sqrt(ab / Math.pow(Compactness, 3.0), dimList.size());
                this.a_and_b = Compactness * this.c;
                break;
            }
        }
    }
    
    static double sqrt(final double d, double i) {
        i = 1.0 / i;
        return Math.pow(d, i);
    }
    
    @Override
    public void product() {
        this.R = this.a_and_b;
        for (int i = 0; i < this.dimList.size(); ++i) {
            if (i == 0) {
                final double temp = this.dimList.get(i).getMin() + this.R + (this.dimList.get(i).getRange() - 2.0 * this.R) * new Random().nextDouble();
                this.fail_point.add(temp);
            }
            else {
                final double temp = this.dimList.get(i).getMin() + this.R + (this.dimList.get(i).getRange() - 2.0 * this.R) * new Random().nextDouble();
                this.fail_point.add(temp);
            }
        }
    }
    
    @Override
    public boolean isCorrect(final ComparableTestCase testcase) {
        this.cost++;
        this.time++;
        final ComparableTestCase temp = this.return_rotate(testcase);
        final ComparableTestCase first1 = new ComparableTestCase(-1.0);
        first1.list.addAll(this.fail_point);
        final ComparableTestCase first2 = this.return_rotate(first1);
        for (int i = 0; i < this.dimList.size(); ++i) {
            temp.list.set(i, temp.list.get(i) - first2.list.get(i));
        }
        double result = 0.0;
        for (int j = 0; j < this.dimList.size(); ++j) {
            if (j == this.dimList.size() - 1) {
                result += Math.pow(temp.list.get(j), 2.0) / Math.pow(this.c, 2.0);
            }
            else {
                result += Math.pow(temp.list.get(j), 2.0) / Math.pow(this.a_and_b, 2.0);
            }
        }
        return result > 1.0;
    }
    
    private ComparableTestCase return_rotate(final ComparableTestCase firstcase2) {
        final Double angle = -this.orientation * 3.141592653589793 / 180.0;
        ComparableTestCase temp = null;
        switch (this.dimList.size()) {
            case 2: {
                temp = new ComparableTestCase(0.0);
                final double x1 = firstcase2.list.get(0) * Math.cos(angle) - firstcase2.list.get(1) * Math.sin(angle);
                final double y1 = firstcase2.list.get(0) * Math.sin(angle) + firstcase2.list.get(1) * Math.cos(angle);
                temp.list.add(x1);
                temp.list.add(y1);
                break;
            }
            case 3: {
                final double x1 = firstcase2.list.get(2) * Math.sin(angle) + firstcase2.list.get(0) * Math.cos(angle);
                final double y1 = firstcase2.list.get(1);
                final double z1 = firstcase2.list.get(2) * Math.cos(angle) - firstcase2.list.get(0) * Math.sin(angle);
                temp = new ComparableTestCase(0.0);
                temp.list.add(x1);
                temp.list.add(y1);
                temp.list.add(z1);
                break;
            }
            case 4: {
                final double x1 = firstcase2.list.get(0) * Math.cos(angle) + firstcase2.list.get(3) * Math.sin(angle);
                final double y1 = firstcase2.list.get(1);
                final double z1 = firstcase2.list.get(2);
                final double w1 = firstcase2.list.get(3) * Math.cos(angle) - firstcase2.list.get(0) * Math.sin(angle);
                temp = new ComparableTestCase(0.0);
                temp.list.add(x1);
                temp.list.add(y1);
                temp.list.add(z1);
                temp.list.add(w1);
                break;
            }
        }
        return temp;
    }
}

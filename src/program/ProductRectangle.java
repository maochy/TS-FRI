package program;

import common.*;
import java.util.*;

public class ProductRectangle extends Shape
{
    double totalsize;
    double failTotalSize;
    public ArrayList<Double> fail_point;
    public double length;
    public double height_and_width;
    Double R;
    
    public ProductRectangle(final ArrayList<Dimension> dimList, final double failrate, final double Compactness, final double orientation) {
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
        this.length = sqrt(this.failTotalSize / Math.pow(Compactness, dimList.size() - 1), dimList.size());
        this.height_and_width = Compactness * this.length;
    }
    
    static double sqrt(final double d, double i) {
        i = 1.0 / i;
        return Math.pow(d, i);
    }
    
    @Override
    public void product() {
        for (int i = 0; i < this.dimList.size(); ++i) {
            if (i == 0) {
                final double temp = this.dimList.get(i).getMin() + this.length / 2.0 + (this.dimList.get(i).getRange() - this.length) * new Random().nextDouble();
                this.fail_point.add(temp);
            }
            else {
                final double temp = this.dimList.get(i).getMin() + this.height_and_width / 2.0 + (this.dimList.get(i).getRange() - this.height_and_width) * new Random().nextDouble();
                this.fail_point.add(temp);
            }
        }
    }
    
    @Override
    public boolean isCorrect(final ComparableTestCase testcase) {
        this.cost++;
        this.time++;
        boolean jutice = false;
        final ComparableTestCase temp = this.return_rotate(testcase);
        final ComparableTestCase first1 = new ComparableTestCase(-1.0);
        first1.list.addAll(this.fail_point);
        final ComparableTestCase first2 = this.return_rotate(first1);
        for (int i = 0; i < temp.list.size(); ++i) {
            if (i == 0) {
                if (temp.list.get(i) < first2.list.get(i) - this.length / 2.0 || temp.list.get(i) > first2.list.get(i) + this.length / 2.0) {
                    jutice = true;
                    return jutice;
                }
            }
            else if (temp.list.get(i) < first2.list.get(i) - this.height_and_width / 2.0 || temp.list.get(i) > first2.list.get(i) + this.height_and_width / 2.0) {
                jutice = true;
                return jutice;
            }
        }
        return jutice;
    }
    
    private ComparableTestCase return_rotate(final ComparableTestCase firstcase2) {
        //System.out.println("Size\uff1a" + firstcase2.list.size());
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

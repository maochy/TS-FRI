package program;

import java.util.*;
import common.*;

public class ProductCircle extends Shape
{
    double totalSize;
    int failNumber;
    double radius;
    
    public ProductCircle(final ArrayList<Dimension> dimList, final double failrate) {
        this.totalSize = dimList.get(0).getRange();
        for (int i = 1; i < dimList.size(); ++i) {
            this.totalSize *= dimList.get(i).getRange();
        }
        this.dimList = dimList;
        this.failRate = failrate;
        this.failTotalSize = failrate * this.totalSize;
        this.failNumber = 1;
        this.radius = this.pro_Radius(this.failTotalSize, this.failNumber, dimList.size());
    }
    
    @Override
    public void product() {
        this.mutiple = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> templist = new ArrayList<Double>();
        for (int j = 0; j < this.dimList.size(); ++j) {
            final double temp = this.dimList.get(j).getMin() + this.radius + (this.dimList.get(j).getRange() - this.radius) * new Random().nextDouble();
            templist.add(temp);
        }
        this.mutiple.add(templist);
        for (int i = 0; i < this.failNumber - 1; ++i) {
            templist.clear();
            for (int k = 0; k < this.dimList.size(); ++k) {
                final double temp2 = this.dimList.get(k).getMin() + this.radius + (this.dimList.get(k).getRange() - this.radius) * new Random().nextDouble();
                templist.add(temp2);
            }
            while (this.fail1(templist)) {
                templist = new ArrayList<Double>();
                for (int k = 0; k < this.dimList.size(); ++k) {
                    final double temp2 = this.dimList.get(k).getMin() + this.radius + (this.dimList.get(k).getRange() - this.radius) * new Random().nextDouble();
                    templist.add(temp2);
                }
            }
            this.mutiple.add(templist);
        }
    }
    
    private double pro_Radius(final double R, final double m, final double dimension) {
        final double single_area = R / m;
        final double gam_valut = this.gamma(dimension / 2.0 + 1.0);
        final double fenzi = Math.pow(3.141592653589793, dimension / 2.0);
        return this.sqrt(single_area * gam_valut / fenzi, dimension);
    }
    
    double gamma(final double x) {
        return Math.exp(this.logGamma(x));
    }
    
    double logGamma(final double x) {
        final double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
        final double ser = 1.0 + 76.18009173 / (x + 0.0) - 86.50532033 / (x + 1.0) + 24.01409822 / (x + 2.0) - 1.231739516 / (x + 3.0) + 0.00120858003 / (x + 4.0) - 5.36382E-6 / (x + 5.0);
        return tmp + Math.log(ser * Math.sqrt(6.28));
    }
    
    double sqrt(final double d, double i) {
        i = 1.0 / i;
        return Math.pow(d, i);
    }
    
    boolean fail1(final ArrayList<Double> templist) {
        for (int i = 0; i < this.mutiple.size(); ++i) {
            if (this.Euclidean_Distance(this.mutiple.get(i), templist) < this.radius) {
                return true;
            }
        }
        return false;
    }
    
    private double Euclidean_Distance(final ArrayList<Double> arrayList, final ArrayList<Double> templist) {
        double sum = 0.0;
        for (int i = 0; i < arrayList.size(); ++i) {
            sum += Math.pow(arrayList.get(i) - templist.get(i), 2.0);
        }
        return Math.sqrt(sum);
    }
    
    @Override
    public boolean isCorrect(final ComparableTestCase testcase) {
        this.time++;
        final boolean jutice = true;
        for (int i = 0; i < this.mutiple.size(); ++i) {
            if (this.Euclidean_Distance(this.mutiple.get(i), testcase.list) < this.radius) {
                return false;
            }
        }
        return jutice;
    }
}

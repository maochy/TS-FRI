package method;

import program.*;
import model.*;
import common.*;
import utils.Calc;

import java.util.*;

public class DSB implements Method
{
    public Shape shape;
    ComparableTestCase firstcase;
    ArrayList<ComparableTestCase> subfirstcase;
    public double l;
    int count;
    int times;
    int dimSize;
    ArrayList<ComparableTestCase> bestCollection;
    ArrayList<ComparableTestCase> oldBest;
    ArrayList<ComparableTestCase> list;
    ArrayList<ArrayList<Double>> Selected;
    ArrayList<ArrayList<Double>> Candidate;
    ArrayList<ArrayList<Double>> dimValue;
    ArrayList<ArrayList<Double>> resultValue;
    boolean terminate;
    
    public DSB() {
        this.l = 0.0;
        this.count = 0;
        this.bestCollection = new ArrayList<ComparableTestCase>();
        this.oldBest = new ArrayList<ComparableTestCase>();
        this.list = new ArrayList<ComparableTestCase>();
        this.Selected = new ArrayList<ArrayList<Double>>();
        this.Candidate = new ArrayList<ArrayList<Double>>();
        this.dimValue = new ArrayList<ArrayList<Double>>();
        this.resultValue = new ArrayList<ArrayList<Double>>();
        this.terminate = true;
    }
    
    @Override
    public SaveObject run(final Shape shape, final ArrayList<ComparableTestCase> subfirstcase, final ComparableTestCase firstcase, final int times) throws Exception {
        long nanoTime = System.nanoTime();
        this.shape = shape;
        this.firstcase = firstcase;
        this.subfirstcase = subfirstcase;
        this.times = times;
        this.dimSize = shape.dimList.size();
        for (int i = 0; i < shape.dimList.size(); ++i) {
            final ArrayList<Double> templist = new ArrayList<Double>();
            templist.add(1.0);
            templist.add(-1.0);
            this.dimValue.add(templist);
        }
        recursive(this.dimValue, this.resultValue, 0, new ArrayList<Double>());
        //System.out.println("\u5927\u5c0f\uff1a" + this.subfirstcase.size());
        this.firstcase.setIncrement(this.l);
        for (int i = 0; i < shape.dimList.size(); ++i) {
            final double temp = Math.max(shape.dimList.get(i).getMax() - this.firstcase.list.get(i), this.firstcase.list.get(i) - shape.dimList.get(i).getMin());
            //System.out.println(temp);
            if (temp > this.l) {
                this.l = temp;
            }
        }
        while (this.terminate) {
            final ArrayList<ComparableTestCase> total = new ArrayList<ComparableTestCase>();
            this.Candidate = new ArrayList<ArrayList<Double>>();
            for (int j = 0; j < 10; ++j) {
                final ArrayList<Double> templist2 = new ArrayList<Double>();
                double sum = 0.0;
                for (int k = 0; k < shape.dimList.size(); ++k) {
                    templist2.add(Math.random());
                    sum += templist2.get(k) * templist2.get(k);
                }
                for (int k = 0; k < shape.dimList.size(); ++k) {
                    templist2.set(k, templist2.get(k) / Math.sqrt(sum) * this.l);
                }
                this.Candidate.add(templist2);
            }
            final ArrayList<Double> best = this.BestCandidate(this.Candidate, this.Selected);
            this.Selected.add(best);
            final ArrayList<Double> absdistance = new ArrayList<Double>();
            for (int l = 0; l < shape.dimList.size(); ++l) {
                absdistance.add(best.get(l));
            }
            for (int m = 0; m < this.resultValue.size(); ++m) {
                final ComparableTestCase test = new ComparableTestCase(this.l);
                for (int k = 0; k < shape.dimList.size(); ++k) {
                    test.list.add(firstcase.list.get(k) + absdistance.get(k) * this.resultValue.get(m).get(k));
                }
                total.add(test);
            }
            for (int m = 0; m < total.size(); ++m) {
                final ComparableTestCase temp2 = this.method(total.get(m), firstcase);
                if (!this.list.contains(temp2)) {
                    this.list.add(temp2);
                }
                ++this.count;
                if (this.count == times) {
                    this.terminate = false;
                    break;
                }
            }
        }
        for (int i = 0; subfirstcase != null && i < subfirstcase.size(); ++i) {
            if (!this.list.contains(subfirstcase.get(i))) {
                this.list.add(subfirstcase.get(i));
            }
        }

        final SaveObject save = new SaveObject();
        save.setTime(System.nanoTime()-nanoTime);
        save.setShape(shape);
        save.setList(this.list);
        save.setFirstcase(firstcase);
        save.totalSize = Calc.calcauate(list,shape.dimList.size());
        save.setMethodName("DSB");
        return save;
    }
    
    public ComparableTestCase method(final ComparableTestCase point, final ComparableTestCase point1) {
        shape.time = 0;
        boolean flag = true;
        ComparableTestCase best = point1;
        ComparableTestCase test = point;
        final ArrayList<Double> absdistance = new ArrayList<Double>();
        for (int i = 0; i < this.shape.dimList.size(); ++i) {
            absdistance.add(Math.abs(point1.list.get(i) - point.list.get(i)));
        }
        //System.out.println("\n\n");
        while (flag) {
            //System.out.println(test.list+" "+test.increment+" "+this.shape.isCorrect(test));
            if (!this.shape.isCorrect(test)) {
                best = test;
                if (test.isRebound()) {
                    final ComparableTestCase temp = new ComparableTestCase(test.getIncrement() / 2.0);
                    temp.setRebound(true);
                    temp.setWay(test.getWay());
                    for (int j = 0; j < this.shape.dimList.size(); ++j) {
                        if (test.list.get(j) - point1.list.get(j) >= 0.0) {
                            temp.list.add(test.list.get(j) + absdistance.get(j) * (temp.getIncrement() / this.l));
                        }
                        else {
                            temp.list.add(test.list.get(j) - absdistance.get(j) * (temp.getIncrement() / this.l));
                        }
                    }
                    test = temp;
                }
                else {
                    final ComparableTestCase temp = new ComparableTestCase(test.getIncrement());
                    temp.setWay(test.getWay());
                    for (int j = 0; j < this.shape.dimList.size(); ++j) {
                        if (test.list.get(j) - point1.list.get(j) >= 0.0) {
                            temp.list.add(test.list.get(j) + absdistance.get(j) * (temp.getIncrement() / this.l));
                        }
                        else {
                            temp.list.add(test.list.get(j) - absdistance.get(j) * (temp.getIncrement() / this.l));
                        }
                    }
                    test = temp;
                }
                if (test.equals(best)) {
                    return best;
                }
                continue;
            }
            else {
                //System.out.println(test.times+"%%%");
                final ComparableTestCase temp = new ComparableTestCase(test.getIncrement() / 2.0);
                temp.setRebound(true);
                temp.setWay(test.getWay());
                temp.setTimes(test.getTimes() + 1);
                for (int j = 0; j < this.shape.dimList.size(); ++j) {
                    if (test.list.get(j) - point1.list.get(j) >= 0.0) {
                        temp.list.add(test.list.get(j) - absdistance.get(j) * (temp.getIncrement() / this.l));
                    }
                    else {
                        temp.list.add(test.list.get(j) + absdistance.get(j) * (temp.getIncrement() / this.l));
                    }
                }
                if (temp.getTimes() == 20) {
                    flag = false;
                    if (best.equals(point1)) {
                        best.setWay(point.getWay());
                    }
                    return best;
                }
                test = temp;
            }
        }
        shape.allTime.add(shape.time);
        return best;
    }
    
    public static void recursive(final ArrayList<ArrayList<Double>> dimValue, final ArrayList<ArrayList<Double>> resultValue, final int layer, final ArrayList<Double> curList) {
        if (layer < dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                recursive(dimValue, resultValue, layer + 1, curList);
            }
            else {
                for (int i = 0; i < dimValue.get(layer).size(); ++i) {
                    final ArrayList<Double> list = new ArrayList<Double>(curList);
                    list.add(dimValue.get(layer).get(i));
                    recursive(dimValue, resultValue, layer + 1, list);
                }
            }
        }
        else if (layer == dimValue.size() - 1) {
            if (dimValue.get(layer).size() == 0) {
                resultValue.add(curList);
            }
            else {
                for (int i = 0; i < dimValue.get(layer).size(); ++i) {
                    final ArrayList<Double> list = new ArrayList<Double>(curList);
                    list.add(dimValue.get(layer).get(i));
                    resultValue.add(list);
                }
            }
        }
    }
    
    private ArrayList<Double> BestCandidate(final ArrayList<ArrayList<Double>> candidate2, final ArrayList<ArrayList<Double>> selected2) {
        final Double p = null;
        double minmax = Double.MAX_VALUE;
        int cixu = -1;
        if (selected2.size() == 0) {
            return candidate2.get(new Random().nextInt(candidate2.size()));
        }
        for (int i = 0; i < candidate2.size(); ++i) {
            double mindist = 0.0;
            for (int j = 0; j < selected2.size(); ++j) {
                final double dist = this.Euclidean_Distance(candidate2.get(i), selected2.get(j));
                if (dist > mindist) {
                    mindist = dist;
                }
            }
            if (minmax > mindist) {
                minmax = mindist;
                cixu = i;
            }
        }
        return candidate2.get(cixu);
    }
    
    private double Euclidean_Distance(final ArrayList<Double> arrayList, final ArrayList<Double> templist) {
        double sum1 = 0.0;
        double sum2 = 0.0;
        double sum3 = 0.0;
        for (int i = 0; i < this.shape.dimList.size(); ++i) {
            sum1 += arrayList.get(i) * templist.get(i);
            sum2 += arrayList.get(i) * arrayList.get(i);
            sum3 += templist.get(i) * templist.get(i);
        }
        return sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3));
    }
    
    private class method1
    {
    }
}

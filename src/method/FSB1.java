package method;

import program.*;
import model.*;
import common.*;
import utils.Calc;

import java.util.*;

public class FSB1 implements Method
{
    Shape shape;
    ComparableTestCase firstcase;
    ArrayList<ComparableTestCase> subfirstcase;
    double l;
    int count;
    int times;
    int dimSize;
    ArrayList<ComparableTestCase> bestCollection;
    ArrayList<ComparableTestCase> oldBest;
    ArrayList<ComparableTestCase> list;
    boolean flag;
    boolean terminate;
    
    public FSB1() {
        this.l = 0.0;
        this.count = 0;
        this.bestCollection = new ArrayList<ComparableTestCase>();
        this.oldBest = new ArrayList<ComparableTestCase>();
        this.list = new ArrayList<ComparableTestCase>();
        this.flag = true;
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
            if (this.flag) {
                for (int j = 0; j < shape.dimList.size(); ++j) {
                    if (this.subfirstcase.size() != 0) {
                        for (int k = 0; k < subfirstcase.size(); ++k) {
                            ComparableTestCase temp2 = this.method(this.sure(j + 1, subfirstcase.get(k)), subfirstcase.get(k));
                            this.bestCollection.add(temp2);
                            if (!this.list.contains(temp2)) {
                                this.list.add(temp2);
                            }
                            ++this.count;
                            if (this.count == times) {
                                this.terminate = false;
                                break;
                            }
                            temp2 = this.method(this.sure(-(j + 1), subfirstcase.get(k)), subfirstcase.get(k));
                            this.bestCollection.add(temp2);
                            if (!this.list.contains(temp2)) {
                                this.list.add(temp2);
                            }
                            ++this.count;
                            if (this.count == times) {
                                this.terminate = false;
                                break;
                            }
                            this.flag = false;
                        }
                    }
                    else {
                        ComparableTestCase temp3 = this.method(this.sure(j + 1, firstcase), firstcase);
                        this.bestCollection.add(temp3);
                        if (!this.list.contains(temp3)) {
                            this.list.add(temp3);
                        }
                        ++this.count;
                        if (this.count == times) {
                            this.terminate = false;
                            break;
                        }
                        temp3 = this.method(this.sure(-(j + 1), firstcase), firstcase);
                        this.bestCollection.add(temp3);
                        if (!this.list.contains(temp3)) {
                            this.list.add(temp3);
                        }
                        ++this.count;
                        if (this.count == times) {
                            this.terminate = false;
                            break;
                        }
                        this.flag = false;
                    }
                }
            }
            else {
                for (int j = 0; j < this.bestCollection.size(); ++j) {
                    this.product_all(this.bestCollection.get(j));
                    if (!this.terminate) {
                        break;
                    }
                }
                this.bestCollection.clear();
                this.bestCollection.addAll(this.oldBest);
                this.oldBest.clear();
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
        save.setMethodName("FSB1");
        return save;
    }
    
    private ComparableTestCase sure(final int way, final ComparableTestCase last) {
        final ComparableTestCase temp = new ComparableTestCase(this.l);
        temp.setWay(way);
        for (int i = 0; i < this.shape.dimList.size(); ++i) {
            if (i + 1 == Math.abs(way)) {
                if (way < 0) {
                    temp.list.add(last.list.get(i) - temp.getIncrement());
                }
                else {
                    temp.list.add(last.list.get(i) + temp.getIncrement());
                }
            }
            else {
                temp.list.add(last.list.get(i));
            }
        }
        return temp;
    }
    
    private ComparableTestCase method(final ComparableTestCase point, final ComparableTestCase point1) {
        shape.time = 0;
        boolean flag = true;
        ComparableTestCase best = point1;
        ComparableTestCase test = point;
        while (flag) {
            if (!this.shape.isCorrect(test)) {
                best = test;
                if (test.isRebound()) {
                    final ComparableTestCase temp = test = this.sure(test.getWay(), true, test);
                }
                else {
                    final ComparableTestCase temp = test = this.sure(test.getWay(), false, test);
                }
                if (test.equals(best)) {
                    return best;
                }
                continue;
            }
            else {
                final ComparableTestCase temp = this.flashBack(test.getWay(), test);
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
    
    private void product_all(final ComparableTestCase last) {
        for (int i = 0; i < this.shape.dimList.size(); ++i) {
            if (i + 1 == Math.abs(last.way)) {
                final ComparableTestCase temp = this.method(this.sure(last.getWay(), last), last);
                this.oldBest.add(temp);
                this.list.add(temp);
                ++this.count;
                if (this.count == this.times) {
                    this.terminate = false;
                    return;
                }
            }
            else {
                ComparableTestCase temp = this.method(this.sure(i + 1, last), last);
                this.oldBest.add(temp);
                this.list.add(temp);
                ++this.count;
                if (this.count == this.times) {
                    this.terminate = false;
                    return;
                }
                temp = this.method(this.sure(-(i + 1), last), last);
                this.oldBest.add(temp);
                this.list.add(temp);
                ++this.count;
                if (this.count == this.times) {
                    this.terminate = false;
                    return;
                }
            }
        }
    }
    
    private ComparableTestCase sure(final int way, final boolean b, final ComparableTestCase last) {
        final ComparableTestCase temp = new ComparableTestCase(0.0);
        if (b) {
            temp.setRebound(true);
            temp.setWay(last.getWay());
            temp.setIncrement(last.getIncrement() / 2.0);
            for (int i = 0; i < last.list.size(); ++i) {
                if (i + 1 == Math.abs(way)) {
                    if (way < 0) {
                        temp.list.add(last.list.get(i) - temp.getIncrement());
                    }
                    else {
                        temp.list.add(last.list.get(i) + temp.getIncrement());
                    }
                }
                else {
                    temp.list.add(last.list.get(i));
                }
            }
        }
        else {
            temp.setWay(last.getWay());
            temp.setIncrement(last.getIncrement());
            for (int i = 0; i < last.list.size(); ++i) {
                if (i + 1 == Math.abs(way)) {
                    if (way < 0) {
                        temp.list.add(last.list.get(i) - temp.getIncrement());
                    }
                    else {
                        temp.list.add(last.list.get(i) + temp.getIncrement());
                    }
                }
                else {
                    temp.list.add(last.list.get(i));
                }
            }
        }
        return temp;
    }
    
    private ComparableTestCase flashBack(final int way, final ComparableTestCase last) {
        final ComparableTestCase temp = new ComparableTestCase(0.0);
        temp.setIncrement(last.getIncrement() / 2.0);
        temp.setRebound(true);
        temp.setWay(way);
        temp.setTimes(last.getTimes() + 1);
        for (int i = 0; i < last.list.size(); ++i) {
            if (i + 1 == Math.abs(way)) {
                if (way < 0) {
                    temp.list.add(last.list.get(i) + temp.getIncrement());
                }
                else {
                    temp.list.add(last.list.get(i) - temp.getIncrement());
                }
            }
            else {
                temp.list.add(last.list.get(i));
            }
        }
        return temp;
    }
    
}

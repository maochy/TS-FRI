package method;

import common.ComparableTestCase;
import common.TestCase;
import program.Shape;
import model.SaveObject;
import utils.Calc;

import java.util.ArrayList;

public class FSB2 implements Method
{
    Shape shape;
    TestCase firstcase;
    ArrayList<ComparableTestCase> subfirstcase;
    double l;
    double minl;
    ArrayList<ArrayList<Integer>> dimValue;
    ArrayList<ArrayList<Integer>> resultValue;
    int count;
    int times;
    int dimSize;
    ArrayList<TestCase> bestCollection;
    ArrayList<TestCase> oldBest;
    ArrayList<TestCase> list;
    boolean flag;
    boolean terminate;
    
    public FSB2() {
        this.l = 0.0;
        this.dimValue = new ArrayList<ArrayList<Integer>>();
        this.resultValue = new ArrayList<ArrayList<Integer>>();
        this.count = 0;
        this.bestCollection = new ArrayList<TestCase>();
        this.oldBest = new ArrayList<TestCase>();
        this.list = new ArrayList<TestCase>();
        this.flag = true;
        this.terminate = true;
    }

    @Override
    public SaveObject run(final Shape shape, final ArrayList<ComparableTestCase> subfirstcase, final ComparableTestCase firstcase, final int times) throws Exception {
        //System.out.println("\u8fdb\u5165\u7684\u7b97\u6cd5\u662fFMBA2");
        long nanoTime = System.nanoTime();
        this.shape = shape;
        this.firstcase = new TestCase(this.l);
        this.firstcase.list.addAll(firstcase.list);
        this.subfirstcase = subfirstcase;
        this.times = times;
        this.dimSize = shape.dimList.size();
        for (int i = 0; i < shape.dimList.size(); ++i) {
            final ArrayList<Integer> templist = new ArrayList<Integer>();
            templist.add(1);
            templist.add(-1);
            this.dimValue.add(templist);
        }
        recursive(this.dimValue, this.resultValue, 0, new ArrayList<Integer>());
        //System.out.println("\u5927\u5c0f\uff1a" + this.subfirstcase.size());
        this.firstcase.setIncrement(this.l);
        for (int i = 0; i < shape.dimList.size(); ++i) {
            final double temp = Math.max(shape.dimList.get(i).getMax() - this.firstcase.list.get(i), this.firstcase.list.get(i) - shape.dimList.get(i).getMin());
            //ystem.out.println(temp);
            if (temp > this.l) {
                this.l = temp;
            }
        }
        this.minl = Math.sqrt(3.0) / 3.0 * this.l;
        while (this.terminate) {
            if (this.flag) {
                for (int j = 0; j < shape.dimList.size(); ++j) {
                    if (this.subfirstcase.size() != 0) {
                        for (int k = 0; k < subfirstcase.size(); ++k) {
                            TestCase temp2 = this.method(this.sure(String.valueOf(j + 1), subfirstcase.get(k).transmit()), subfirstcase.get(k).transmit());
                            this.bestCollection.add(temp2);
                            if (!this.list.contains(temp2)) {
                                this.list.add(temp2);
                            }
                            ++this.count;
                            if (this.count == times) {
                                this.terminate = false;
                                break;
                            }
                            temp2 = this.method(this.sure(String.valueOf(-(j + 1)), subfirstcase.get(k).transmit()), subfirstcase.get(k).transmit());
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
                        TestCase temp3 = this.method(this.sure(String.valueOf(j + 1), this.firstcase), this.firstcase);
                        this.bestCollection.add(temp3);
                        if (!this.list.contains(temp3)) {
                            this.list.add(temp3);
                        }
                        ++this.count;
                        if (this.count == times) {
                            this.terminate = false;
                            break;
                        }
                        temp3 = this.method(this.sure(String.valueOf(-(j + 1)), this.firstcase), this.firstcase);
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
                this.list.add(subfirstcase.get(i).transmit());
            }
        }
        final SaveObject save = new SaveObject();
        save.setTime(System.nanoTime()-nanoTime);
        save.setShape(shape);
        save.setFirstcase(firstcase);
        save.totalSize = Calc.calcauate(list,shape.dimList.size());
        save.setMethodName("FSB2");
        return save;
    }
    
    private TestCase sure(final String way, final TestCase last) {
        final TestCase temp = new TestCase(this.l);
        temp.setWay(way);
        for (int i = 0; i < this.shape.dimList.size(); ++i) {
            if (i + 1 == Math.abs(Integer.valueOf(way))) {
                if (Integer.valueOf(way) < 0) {
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
    
    private TestCase sure(final ArrayList<Integer> arrayList, final TestCase firstcase) {
        final TestCase temp = new TestCase(this.l);
        String way = "";
        for (int i = 0; i < arrayList.size(); ++i) {
            temp.list.add(firstcase.list.get(i) + this.minl * arrayList.get(i));
            if (i == arrayList.size() - 1) {
                way = String.valueOf(way) + arrayList.get(i);
            }
            else {
                way = String.valueOf(way) + arrayList.get(i) + ",";
            }
        }
        temp.setWay(way);
        return temp;
    }
    
    private TestCase method(final TestCase point, final TestCase point1) {
        shape.time = 0;
        boolean flag = true;
        TestCase best = point1;
        TestCase test = point;
        final ArrayList<Double> absdistance = new ArrayList<Double>();
        for (int i = 0; i < this.shape.dimList.size(); ++i) {
            absdistance.add(Math.abs(point1.list.get(i) - point.list.get(i)));
        }
        while (flag) {
            final ComparableTestCase temptest = test.transmit();
            if (!this.shape.isCorrect(temptest)) {
                best = test;
                if (test.isRebound()) {
                    final TestCase temp = new TestCase(test.getIncrement() / 2.0);
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
                    final TestCase temp = new TestCase(test.getIncrement());
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
                final TestCase temp = new TestCase(test.getIncrement() / 2.0);
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
                        best.setWay(temp.getWay());
                    }
                    return best;
                }
                test = temp;
            }
        }
        shape.allTime.add(shape.time);
        return best;
    }
    
    private void product_all(final TestCase last) {
        String a = "";
        for (int i = 0; i < this.shape.dimList.size(); ++i) {
            if (String.valueOf(i + 1).equals(last.getWay()) || String.valueOf(-(i + 1)).equals(last.getWay())) {
                final TestCase temp = this.method(this.sure(last.getWay(), last), last);
                this.oldBest.add(temp);
                this.list.add(temp);
                ++this.count;
                if (this.count == this.times) {
                    this.terminate = false;
                    return;
                }
            }
            else {
                TestCase temp = this.method(this.sure(String.valueOf(i + 1), last), last);
                this.oldBest.add(temp);
                this.list.add(temp);
                ++this.count;
                if (this.count == this.times) {
                    this.terminate = false;
                    return;
                }
                temp = this.method(this.sure(String.valueOf(-(i + 1)), last), last);
                this.oldBest.add(temp);
                this.list.add(temp);
                ++this.count;
                if (this.count == this.times) {
                    this.terminate = false;
                    return;
                }
            }
        }
        final String[] re = last.getWay().split(",");
        for (int j = 0; j < re.length; ++j) {
            a = String.valueOf(a) + -Integer.valueOf(re[j]);
        }
        for (int j = 0; j < this.resultValue.size(); ++j) {
            String way = "";
            for (int k = 0; k < this.resultValue.get(j).size(); ++k) {
                way = String.valueOf(way) + this.resultValue.get(j).get(k);
            }
            if (!way.equals(a)) {
                final TestCase temp2 = this.method(this.sure(this.resultValue.get(j), last), last);
                this.oldBest.add(temp2);
                this.list.add(temp2);
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
    
    private ComparableTestCase flash_back(final int way, final ComparableTestCase last) {
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
    
    public static void recursive(final ArrayList<ArrayList<Integer>> dimValue2, final ArrayList<ArrayList<Integer>> resultValue2, final int layer, final ArrayList<Integer> curList) {
        if (layer < dimValue2.size() - 1) {
            if (dimValue2.get(layer).size() == 0) {
                recursive(dimValue2, resultValue2, layer + 1, curList);
            }
            else {
                for (int i = 0; i < dimValue2.get(layer).size(); ++i) {
                    final ArrayList<Integer> list = new ArrayList<Integer>(curList);
                    list.add(dimValue2.get(layer).get(i));
                    recursive(dimValue2, resultValue2, layer + 1, list);
                }
            }
        }
        else if (layer == dimValue2.size() - 1) {
            if (dimValue2.get(layer).size() == 0) {
                resultValue2.add(curList);
            }
            else {
                for (int i = 0; i < dimValue2.get(layer).size(); ++i) {
                    final ArrayList<Integer> list = new ArrayList<Integer>(curList);
                    list.add(dimValue2.get(layer).get(i));
                    resultValue2.add(list);
                }
            }
        }
    }
    
    class method3
    {
    }
}

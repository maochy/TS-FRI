package common;

import java.util.ArrayList;

public class SubSpace {
    private ArrayList<ComparableTestCase> points;

    public SubSpace(ArrayList<ComparableTestCase> points) {
        this.points = points;
    }

    public ComparableTestCase getMid(){
        ComparableTestCase mid = new ComparableTestCase();
        int dim = this.points.size();
        for (int i = 0; i < dim; i++) {
            double sum = 0;
            for (int j = 0; j < dim; j++)
                sum+=this.points.get(j).list.get(i);
            mid.list.add(sum/dim);
        }
        return mid;
    }

    public ArrayList<ComparableTestCase> getPoints() {
        return points;
    }

}

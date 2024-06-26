package common;

public class ProjectPoint implements Comparable<ProjectPoint>{
    public double value;
    public int x;
    public ProjectPoint(double value, int x) {
        this.value = value;
        this.x = x;
    }

    @Override
    public int compareTo(ProjectPoint other) {
        if (Math.abs(other.value-value)<10e-12)
            return 0;
        else if(other.value>value)
            return -1;
        else
            return 1;
    }
}

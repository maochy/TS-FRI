package common;

public class Dimension
{
    double min;
    double max;
    double range;
    
    public Dimension(final double min, final double max) {
        this.min = min;
        this.max = max;
        this.range = max - min;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public void setMin(final double min) {
        this.min = min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public void setMax(final double max) {
        this.max = max;
    }
    
    public double getRange() {
        return this.range;
    }
    
    @Override
    public String toString() {
        return "Max:" + this.max + ",Min:" + this.min;
    }
}

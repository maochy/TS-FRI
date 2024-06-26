package model;

import common.ComparableTestCase;
import program.Shape;

import java.util.ArrayList;

public class SaveObject
{
    public long time;
    public Shape shape;
    public ArrayList<ComparableTestCase> list;
    public double totalSize;
    private ComparableTestCase Firstcase;
    private String methodName;
    
    public SaveObject() {
        this.list = new ArrayList<ComparableTestCase>();
    }
    
    public String getMethodName() {
        return this.methodName;
    }
    
    public void setMethodName(final String methodName) {
        this.methodName = methodName;
    }
    
    public ComparableTestCase getFirstcase() {
        return this.Firstcase;
    }
    
    public void setFirstcase(final ComparableTestCase firstcase) {
        this.Firstcase = firstcase;
    }
    
    public double getTotalSize() {
        return this.totalSize;
    }
    
    public void setTotalSize(final double totalSize) {
        this.totalSize = totalSize;
    }
    
    public Shape getShape() {
        return this.shape;
    }
    
    public void setShape(final Shape shape) {
        this.shape = shape;
    }
    
    public ArrayList<ComparableTestCase> getList() {
        return this.list;
    }
    
    public void setList(final ArrayList<ComparableTestCase> list) {
        this.list = list;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

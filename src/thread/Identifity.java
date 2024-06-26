/*
 * Decompiled with CFR 0.150.
 */
package thread;

import common.ComparableTestCase;
import method.Method;
import program.Shape;
import model.SaveObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Identifity extends SwingWorker<SaveObject, String> {
    private Method method;
    Shape shape;
    ArrayList<ComparableTestCase> subfirstcase;
    ComparableTestCase firstcase;
    int times;
    private JTextArea area;

    public Identifity(String methodname, Shape shape, ArrayList<ComparableTestCase> subfirstcase, ComparableTestCase firstcase, int times, JTextArea area) {
        this.shape = shape;
        this.subfirstcase = subfirstcase;
        this.firstcase = firstcase;
        this.times = times;
        this.area = area;
        try {
            System.out.println("class: "+methodname);
            Class<?> cl = Class.forName("method." + methodname);
            this.method = (Method)cl.newInstance();
        }
        catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected SaveObject doInBackground() throws Exception {
        SaveObject temp = this.method.run(this.shape, this.subfirstcase, this.firstcase, this.times);
        System.out.println("total size: "+temp.totalSize);
        return temp;
    }

    @Override
    protected void process(List<String> list) {
    }

    @Override
    protected void done() {
        super.done();
        double totalsize = 1.0;
        for (int i = 0; i < this.shape.getDimList().size(); ++i) {
            totalsize *= this.shape.getDimList().get(i).getRange();
        }
        try {
            System.out.println("totalSize: " + this.get().totalSize + "  " + totalsize + "  " + this.shape.getFailTotalSize());
            if (this.subfirstcase != null) {
                this.area.append(String.valueOf(String.valueOf("Fail_pattern's size: " + this.get().totalSize + ",Occupation ratio: ")) + this.get().totalSize / this.shape.getFailTotalSize());
            } else {
                this.area.setText(String.valueOf(String.valueOf("Fail_pattern's size: " + this.get().totalSize + ",Occupation ratio: ")) + this.get().totalSize / this.shape.getFailTotalSize());
            }
            new SaveThread((SaveObject)this.get()).start();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}


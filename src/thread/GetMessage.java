/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jfree.chart.ChartFactory
 *  org.jfree.chart.ChartPanel
 *  org.jfree.chart.JFreeChart
 *  org.jfree.chart.axis.NumberAxis
 *  org.jfree.chart.axis.ValueAxis
 *  org.jfree.chart.plot.Plotorientation
 *  org.jfree.chart.plot.XYPlot
 *  org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
 *  org.jfree.data.xy.DefaultXYDataset
 *  org.jfree.data.xy.XYDataset
 *  org.jzy3d.chart.Chart
 *  org.jzy3d.chart.controllers.camera.AbstractCameraController
 *  org.jzy3d.chart.controllers.mouse.camera.CameraMouseController
 *  org.jzy3d.colors.Color
 *  org.jzy3d.maths.Coord3d
 *  org.jzy3d.plot3d.primitives.AbstractDrawable
 *  org.jzy3d.plot3d.primitives.Scatter
 *  org.jzy3d.plot3d.rendering.canvas.Quality
 */
package thread;

import utils.GraphParseXML;
import model.SaveObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.camera.AbstractCameraController;
import org.jzy3d.chart.controllers.mouse.camera.CameraMouseController;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

public class GetMessage
extends SwingWorker<SaveObject, String> {
    String filePath;
    JPanel mainJFram;

    public GetMessage(String filePath, JPanel mainJFram) {
        this.filePath = filePath;
        this.mainJFram = mainJFram;
    }

    @Override
    protected SaveObject doInBackground() throws Exception {
        GraphParseXML xml = new GraphParseXML();
        SaveObject obj = xml.parseXML(this.filePath);
        return obj;
    }

    @Override
    protected void done() {
        try {
            this.showChart(this.mainJFram, (SaveObject)this.get());
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showChart(JPanel mainJFram, SaveObject saveObject) throws Exception {
        int dimension = saveObject.getShape().dimList.size();
        double C_numbers = -1.0;
        C_numbers = dimension > 2 ? (double)GetMessage.nchoosek(dimension, 2) : (double)dimension;
        if (mainJFram.getComponentCount() != 0) {
            mainJFram.removeAll();
        }
        if (dimension == 2) {
            mainJFram.setLayout(new BorderLayout());
            JPanel par = new JPanel();
            mainJFram.add((Component)par, "Center");
            par.add(this.Get2DPanel(mainJFram, 0, 1, saveObject, mainJFram.getSize().getWidth(), mainJFram.getSize().getHeight()));
        } else if (dimension == 3) {
            mainJFram.setLayout(new BorderLayout());
            JPanel par = new JPanel();
            mainJFram.add((Component)par, "Center");
            par.add(this.Get3Dpanel(mainJFram, 0, 1, 2, saveObject, mainJFram.getSize().getWidth(), mainJFram.getSize().getHeight()));
        } else if (dimension >= 4) {
            double linnumber = 3.0;
            int Line = (int)Math.ceil(C_numbers / linnumber);
            mainJFram.setLayout(new GridLayout(Line, (int)linnumber));
            int[] shu = new int[dimension];
            for (int i = 0; i < dimension; ++i) {
                shu[i] = i;
            }
            Stack<Integer> stack = new Stack<Integer>();
            ArrayList<Stack<Integer>> result = new ArrayList<Stack<Integer>>();
            GetMessage.f(shu, (int)linnumber, 0, 0, stack, result);
            Dimension p_dimen = mainJFram.getSize();
            double width = p_dimen.getWidth() / linnumber;
            double height = p_dimen.getHeight() / (double)Line;
            int i = 0;
            while ((double)i < (double)Line * linnumber) {
                if (i < result.size()) {
                    JPanel par = new JPanel();
                    mainJFram.add((Component)par, "Center");
                    par.add(this.Get3Dpanel(mainJFram, (Integer)result.get(i).get(0), (Integer)result.get(i).get(1), (Integer)result.get(i).get(2), saveObject, width, height));
                }
                ++i;
            }
        }
        mainJFram.revalidate();
    }

    private static void f(int[] shu, int targ, int has, int cur, Stack<Integer> stack, ArrayList<Stack<Integer>> result) {
        if (has == targ) {
            result.add(stack);
            return;
        }
        for (int i = cur; i < shu.length; ++i) {
            if (stack.contains(shu[i])) continue;
            stack.add(shu[i]);
            Stack<Integer> stack1 = new Stack<Integer>();
            stack1.addAll(stack);
            GetMessage.f(shu, targ, has + 1, i, stack1, result);
            stack.pop();
        }
    }

    private JPanel Get3Dpanel(JPanel mainJFram, int index1, int index2, int index3, SaveObject saveObject, double width, double height) {
        int size = saveObject.getList().size();
        Coord3d[] points = new Coord3d[size];
        org.jzy3d.colors.Color[] colors = new org.jzy3d.colors.Color[size];
        for (int i = 0; i < size; ++i) {
            double x = saveObject.getList().get((int)i).list.get(index1);
            double y = saveObject.getList().get((int)i).list.get(index2);
            double z = saveObject.getList().get((int)i).list.get(index3);
            points[i] = new Coord3d(x, y, z);
        }
        Scatter scatter = new Scatter(points, org.jzy3d.colors.Color.BLUE);
        scatter.setWidth(4.0f);
        Chart chart = new Chart(Quality.Advanced, "awt");
        chart.getAxeLayout().setMainColor(org.jzy3d.colors.Color.BLACK);
        chart.getScene().add((AbstractDrawable)scatter);
        chart.addController((AbstractCameraController)new CameraMouseController());
        JPanel panel3d = new JPanel();
        panel3d.setPreferredSize(new Dimension((int)width, (int)height));
        panel3d.setLayout(new BorderLayout());
        panel3d.add((Component)chart.getCanvas(), "Center");
        return panel3d;
    }

    private JPanel Get2DPanel(JPanel mainJFram, int index1, int index2, SaveObject saveObject, double width, double height) {
        DefaultXYDataset xydataset = new DefaultXYDataset();
        double[][] data = new double[2][saveObject.getList().size()];
        for (int i = 0; i < saveObject.getList().size(); ++i) {
            data[0][i] = saveObject.getList().get((int)i).list.get(index1);
            data[1][i] = saveObject.getList().get((int)i).list.get(index2);
        }
        xydataset.addSeries((Comparable)((Object)"points"), data);
        JFreeChart chart = ChartFactory.createScatterPlot((String)"", (String)(String.valueOf(index1 + 1) + "_axis"), (String)(String.valueOf(index2 + 1) + "_axis"), (XYDataset)xydataset, PlotOrientation.VERTICAL, (boolean)true, (boolean)false, (boolean)false);
        ChartPanel frame = new ChartPanel(chart);
        chart.setBackgroundPaint((Paint)Color.white);
        chart.setBorderPaint((Paint)Color.GREEN);
        chart.setBorderStroke((Stroke)new BasicStroke(1.5f));
        XYPlot xyplot = (XYPlot)chart.getPlot();
        xyplot.setBackgroundPaint((Paint)new Color(255, 253, 246));
        ValueAxis vaaxis = xyplot.getDomainAxis();
        vaaxis.setAxisLineStroke((Stroke)new BasicStroke(1.5f));
        ValueAxis va = xyplot.getDomainAxis(0);
        va.setAxisLineStroke((Stroke)new BasicStroke(1.5f));
        va.setAxisLineStroke((Stroke)new BasicStroke(1.5f));
        va.setAxisLinePaint((Paint)new Color(215, 215, 215));
        xyplot.setOutlineStroke((Stroke)new BasicStroke(1.5f));
        va.setLabelPaint((Paint)new Color(10, 10, 10));
        va.setTickLabelPaint((Paint)new Color(102, 102, 102));
        ValueAxis axis = xyplot.getRangeAxis();
        axis.setAxisLineStroke((Stroke)new BasicStroke(1.5f));
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
        xylineandshaperenderer.setSeriesOutlinePaint(0, (Paint)Color.WHITE);
        xylineandshaperenderer.setUseOutlinePaint(true);
        NumberAxis numberaxis = (NumberAxis)xyplot.getDomainAxis();
        numberaxis.setAutoRangeIncludesZero(false);
        numberaxis.setTickMarkInsideLength(2.0f);
        numberaxis.setTickMarkOutsideLength(0.0f);
        numberaxis.setAxisLineStroke((Stroke)new BasicStroke(1.5f));
        frame.setPreferredSize(new Dimension((int)width, (int)height - 20));
        JPanel panel = new JPanel();
        panel.add((Component)frame);
        return panel;
    }

    public static void checknk(int n, int k) {
        if (k < 0 || k > n) {
            throw new IllegalArgumentException("K must be an integer between 0 and N.");
        }
    }

    public static int nchoosek(int n, int k) {
        if (n > 16 || n == 16 && k == 8) {
            throw new IllegalArgumentException("N(" + n + ") and k(" + k + ") don't meet the requirements.");
        }
        GetMessage.checknk(n, k);
        int n2 = k = k > n - k ? n - k : k;
        if (k <= 1) {
            return k == 0 ? 1 : n;
        }
        int divisor = 1;
        for (int i = n - k + 1; i <= n; ++i) {
            divisor *= i;
        }
        int dividend = 1;
        for (int i = 2; i <= k; ++i) {
            dividend *= i;
        }
        return (int)((double)divisor / (double)dividend);
    }
}


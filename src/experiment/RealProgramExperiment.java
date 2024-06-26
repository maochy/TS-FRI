package experiment;

import common.ComparableTestCase;
import method.Method;
import method.*;
import method.TSFRI;
import program.Shape;
import program.*;
import program.RealGammq;
import model.SaveObject;
import utils.RealExcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RealProgramExperiment {
    public static List<ComparableTestCase> airyFirstcases = new ArrayList<>();
    public static List<ComparableTestCase> bessjFirstcases = new ArrayList<>();
    public static List<ComparableTestCase> gammqFirstcases = new ArrayList<>();
    public static List<ComparableTestCase> celFirstcases = new ArrayList<>();
    public static List<ComparableTestCase> expintFirstcases = new ArrayList<>();
    public static List<ComparableTestCase> triangleFirstcases = new ArrayList<>();

    static {
        File airyf = new File("firstCase\\airyfirstcases.txt");
        File bessjf = new File("firstCase\\bessjfirstcases.txt");
        File celf = new File("firstCase\\celfirstcases.txt");
        File expintf = new File("firstCase\\expintfirstcases.txt");
        File gammqf = new File("firstCase\\gammqfirstcases.txt");
        File trianglef = new File("firstCase\\trianglefirstcases.txt");
        try {
            Scanner fin = new Scanner(airyf);
            while (fin.hasNext()){
                String[] s = fin.nextLine().split(" ");
                ComparableTestCase t = new ComparableTestCase();
                for (int i = 0; i < s.length; i++) {
                    t.list.add(Double.parseDouble(s[i]));
                }
                airyFirstcases.add(t);
            }

            fin = new Scanner(bessjf);
            while (fin.hasNext()){
                String[] s = fin.nextLine().split(" ");
                ComparableTestCase t = new ComparableTestCase();
                for (int i = 0; i < s.length; i++) {
                    t.list.add(Double.parseDouble(s[i]));
                }
                bessjFirstcases.add(t);
            }

            fin = new Scanner(celf);
            while (fin.hasNext()){
                String[] s = fin.nextLine().split(" ");
                ComparableTestCase t = new ComparableTestCase();
                for (int i = 0; i < s.length; i++) {
                    t.list.add(Double.parseDouble(s[i]));
                }
                celFirstcases.add(t);
            }

            fin = new Scanner(expintf);
            while (fin.hasNext()){
                String[] s = fin.nextLine().split(" ");
                ComparableTestCase t = new ComparableTestCase();
                for (int i = 0; i < s.length; i++) {
                    t.list.add(Double.parseDouble(s[i]));
                }
                expintFirstcases.add(t);
            }

            fin = new Scanner(gammqf);
            while (fin.hasNext()){
                String[] s = fin.nextLine().split(" ");
                ComparableTestCase t = new ComparableTestCase();
                for (int i = 0; i < s.length; i++) {
                    t.list.add(Double.parseDouble(s[i]));
                }
                gammqFirstcases.add(t);
            }

            fin = new Scanner(trianglef);
            while (fin.hasNext()){
                String[] s = fin.nextLine().split(" ");
                ComparableTestCase t = new ComparableTestCase();
                for (int i = 0; i < s.length; i++) {
                    t.list.add(Double.parseDouble(s[i]));
                }
                triangleFirstcases.add(t);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        String[] progNames = {"gammq","expint","bessj","triangle","cel","airy"};
        String[] inputs = {"((0,1700),(0,40))","((0,4095),(0,4095))","((2,300),(0,1500))","((0,255),(0,255),(0,255))","((0.001,1),(0.001,300),(0.001,1),(0.001,1))","((-5000,5000))"};
        int[] dimensions = {2,2,2,3,4,1};
        double[] real_sizes = {57.375,6668534.8214,3118.8694,8290687.5,0.09871,7};
        Class[] classes = {RealGammq.class, RealExpint.class, RealBessj.class, RealTriangle.class, RealCel.class, RealAiry.class};
        List<ComparableTestCase>[] lists = new List[]{gammqFirstcases,expintFirstcases,bessjFirstcases,triangleFirstcases,celFirstcases,airyFirstcases};
        DecimalFormat df = new DecimalFormat("0.0000");

        int[] steps = {50,100,150,200,250,300,350,400,450,500,600,700,800,900,1000,1200,1400,1600,1800,2000};
        final int repeat = 3000;
        Class[] methods = {TSFRI.class, FSB1.class, FSB2.class, DSB.class};
        for (int j = 0; j < methods.length; j++) {
            for (int p = 0; p < progNames.length; p++) {
                for (int i = 0; i < steps.length; i++) {
                    getTime(repeat,steps[i],classes[p],lists[p],methods[j],inputs[p],dimensions[p],progNames[p],real_sizes[p]);
                    getCostNum(repeat,steps[i],classes[p],lists[p],methods[j],inputs[p],dimensions[p],progNames[p],real_sizes[p]);
                    getPrecision(repeat,steps[i],classes[p],lists[p],methods[j],inputs[p],dimensions[p],progNames[p],real_sizes[p]);
                }
            }
        }
        System.exit(0);
    }

    public static double getCostNum(int repeat, int n, Class shape, List<ComparableTestCase>firstcases, Class clazz, String input, int dimention, String name, double real_size) throws Exception {
        ArrayList<SaveObject> saves = new ArrayList<>();
        ArrayList<ComparableTestCase> starts = new ArrayList<>();
        double avg = 0;
        for (int i = 0; i < repeat; i++) {
            Shape p = (Shape) shape.newInstance();
            p.product();
            ComparableTestCase firstcase = firstcases.get((int)(Math.random()*firstcases.size()));
            Method f = (Method)clazz.newInstance();
            SaveObject result = f.run(p, new ArrayList<ComparableTestCase>(), firstcase, n);
            saves.add(result);
            starts.add(firstcase);
            avg+=p.cost;
            //System.out.println(name+" points:"+n+" repeat:"+i);
            //System.out.println(result.totalSize);
        }
        RealExcel.recordNum("results\\real_CostNum\\"+name+"pointNum-"+n+"_repeat-"+repeat+".xlsx",dimention,input,n,saves,starts);
        saves = null;
        starts = null;
        return avg/repeat/n;
    }

    public static double[] getTime(int repeat, int n, Class shape, List<ComparableTestCase> firstcases, Class clazz, String input, int dimention, String name, double real_size) throws Exception {
        ArrayList<SaveObject> saves = new ArrayList<>();
        ArrayList<ComparableTestCase> starts = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<>();
        double avg = 0;
        double real_avg = 0;
        for (int i = 0; i < repeat; i++) {
            Shape p = (Shape) shape.newInstance();
            p.product();
            ComparableTestCase firstcase = firstcases.get((int)(Math.random()*firstcases.size()));
            Method f = (Method)clazz.newInstance();
            SaveObject result = f.run(p, new ArrayList<ComparableTestCase>(), firstcase, n);
            saves.add(result);
            starts.add(firstcase);
            avg+=(result.time);
            real_avg+=p.realtime;
            times.add(result.time-p.realtime);
            //System.out.println(name+" points:"+n+" repeat:"+i);
            //System.out.println(result.totalSize);
        }
        RealExcel.recordTime("results\\real_time\\"+name+"pointNum-"+n+"_repeat-"+repeat+".xlsx",dimention,input,n,saves,starts,times);
        saves = null;
        starts = null;
        double[] a = {(avg/repeat)*1.0/1000000,(real_avg/repeat)*1.0/1000000};
        return a;
    }

    public static double getPrecision(int repeat, int n, Class shape, List<ComparableTestCase>firstcases, Class clazz, String input, int dimention, String name, double real_size) throws Exception {
        ArrayList<Double> prec = new ArrayList<>();
        ArrayList<SaveObject> saves = new ArrayList<>();
        ArrayList<ComparableTestCase> starts = new ArrayList<>();
        double avg = 0;
        for (int i = 0; i < repeat; i++) {
            Shape p = (Shape) shape.newInstance();
            p.product();
            ComparableTestCase firstcase = firstcases.get((int)(Math.random()*firstcases.size()));
            Method f = (Method)clazz.newInstance();
            SaveObject result = f.run(p, new ArrayList<ComparableTestCase>(), firstcase, n);
            saves.add(result);
            starts.add(firstcase);
            prec.add(result.totalSize/real_size);
            avg+=(result.totalSize/real_size);
            System.out.println(name+" points:"+n+" repeat:"+i+" totalSize:"+result.totalSize);
            System.out.println(result.totalSize);
        }
        RealExcel.recordPrec("results\\real_time\\"+name+"pointNum-"+n+"_repeat-"+repeat+".xlsx",dimention,input,n,saves,starts,prec);
        saves = null;
        starts = null;
        prec = null;
        return avg/repeat;
    }

}

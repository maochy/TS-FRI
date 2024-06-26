package experiment;

import common.ComparableTestCase;
import common.Dimension;
import method.*;
import model.SaveObject;
import program.ProductEllipse;
import program.ProductRectangle;
import utils.Excel;

import java.io.IOException;
import java.util.ArrayList;

public class Experiment {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException, InterruptedException {
        final int repeat  = 3000;
        Class[] methods = new Class[]{FSB1.class, FSB2.class,DSB.class, TSFRI.class};
        for (int d = 2;d<=4;d++){
            for(int n = 100;n<=1000;n*=10){
                for (int c = 1; c<=100; c*=10) {
                    for (int r = 0; r <= 90; r+=30){
                        for (int i = 0; i < methods.length; i++) {
                            getTime(methods[i],"C",n,repeat,d,0.005,c,r);
                            getPrecision(methods[i],"C",n,repeat,d,0.005,c,r);
                            getCostNum(methods[i],"C",n,repeat,d,0.005,c,r);
                            getTime(methods[i],"E",n,repeat,d,0.005,c,r);
                            getPrecision(methods[i],"E",n,repeat,d,0.005,c,r);
                            getCostNum(methods[i],"E",n,repeat,d,0.005,c,r);
                        }

                    }
                }
            }
        }
        System.exit(0);
    }

    public static double getPrecision(Class method, String shape, int pointNum, int repeat,
                                      int dim, double failr, int com, int ori) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException{
        double sum = 0;
        ArrayList<SaveObject> saves = new ArrayList<>();
        ArrayList<ComparableTestCase> starts = new ArrayList<>();
        ArrayList<String> fail = new ArrayList<>();
        double area = 1;
        ArrayList<Dimension> dimensions = new ArrayList<>();
        ArrayList<Integer[]> dims = new ArrayList<>();
        for (int j = 0; j < dim; j++) {
            dimensions.add(new Dimension(-5000,5000));
            dims.add(new Integer[]{-5000,5000});
            area*=10000;
        }
        for (int i = 0;i<repeat;i++){
            if (shape.equals("R")){
                ProductRectangle p = new ProductRectangle(dimensions,failr,com,ori);
                    p.product();
                    ComparableTestCase firstcase;
                    do{
                        firstcase = new ComparableTestCase();
                        for (int j = 0; j < dim; j++) {
                            if (j==0)
                                firstcase.list.add(p.fail_point.get(j)-p.length/2+(p.length)*Math.random());
                            else
                                firstcase.list.add(p.fail_point.get(j)-p.height_and_width/2+(p.height_and_width)*Math.random());
                        }
                    }while (p.isCorrect(firstcase));
                    ArrayList<ComparableTestCase> subfirstcase = new ArrayList<ComparableTestCase>();
                    SaveObject obj = null;
                    while(obj==null){
                        Method f = (Method) method.newInstance();
                        try {
                            do{
                                firstcase = new ComparableTestCase();
                                for (int j = 0; j < dim; j++) {
                                    if (j==0)
                                        firstcase.list.add(p.fail_point.get(j)-p.length/2+(p.length)*Math.random());
                                    else
                                        firstcase.list.add(p.fail_point.get(j)-p.height_and_width/2+(p.height_and_width)*Math.random());
                                }
                            }while (p.isCorrect(firstcase));
                            SaveObject result = f.run(p, subfirstcase, firstcase, pointNum);
                            obj = result;
                        }catch (Exception e){
                            p = new ProductRectangle(dimensions,failr,com,ori);
                            p.product();
                            //e.printStackTrace();
                        }
                }
                sum+=obj.getTotalSize()/(area*failr);
                saves.add(obj);
                starts.add(firstcase);
                fail.add(p.fail_point.toString());
                fail.add(p.length+"/"+p.height_and_width);
            }
            else{
                ProductEllipse p = new ProductEllipse(dimensions,failr,com,ori);
                p.product();
                ComparableTestCase firstcase;
                do{
                    firstcase = new ComparableTestCase();
                    for (int j = 0; j < dim; j++) {
                        firstcase.list.add(p.fail_point.get(j)-p.R+(2*p.R)*Math.random());
                    }
                }while (p.isCorrect(firstcase));
                ArrayList<ComparableTestCase> subfirstcase = new ArrayList<ComparableTestCase>();
                SaveObject obj = null;
                while(obj==null){
                    Method f = (Method) method.newInstance();
                    try {
                        do{
                            firstcase = new ComparableTestCase();
                            for (int j = 0; j < dim; j++) {
                                firstcase.list.add(p.fail_point.get(j)-p.R+(2*p.R)*Math.random());
                            }
                        }while (p.isCorrect(firstcase));
                        obj = f.run(p, subfirstcase, firstcase, pointNum);
                    }catch (Exception e){
                        //e.printStackTrace();
                    }
                }
                sum+=obj.getTotalSize()/(area*failr);
                saves.add(obj);
                starts.add(firstcase);
                fail.add(p.fail_point.toString());
                fail.add(p.c+"/"+p.R);
            }
        }
        Excel.recordPrec("result//precision//"+method.getSimpleName()+shape+"_FR-"+failr+"_pointNum-"
                        +pointNum+"-compactness"+com+"_rotate-"+ori+".xlsx",
                dims,failr,dim,pointNum,com,ori,saves,starts,shape,repeat,fail);
       return sum/repeat;
    }

    public static double getCostNum(Class method, String shape, int pointNum, int repeat, int dim, double failr, int com, int ori) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ArrayList<SaveObject> saves = new ArrayList<>();
        ArrayList<ComparableTestCase> starts = new ArrayList<>();
        ArrayList<String> fail = new ArrayList<>();
        double sum = 0;
        double area = 1;
        ArrayList<Dimension> dimensions = new ArrayList<>();
        ArrayList<Integer[]> dims = new ArrayList<>();
        for (int j = 0; j < dim; j++) {
            dimensions.add(new Dimension(-5000,5000));
            dims.add(new Integer[]{-5000,5000});
            area*=10000;
        }
        for (int i = 0;i<repeat;i++){
            if (shape.equals("R")){
                ProductRectangle p = new ProductRectangle(dimensions,failr,com,ori);
                p.product();
                ComparableTestCase firstcase;
                do{
                    firstcase = new ComparableTestCase();
                    for (int j = 0; j < dim; j++) {
                        if (j==0)
                            firstcase.list.add(p.fail_point.get(j)-p.length/2+(p.length)*Math.random());
                        else
                            firstcase.list.add(p.fail_point.get(j)-p.height_and_width/2+(p.height_and_width)*Math.random());
                    }
                }while (p.isCorrect(firstcase));
                ArrayList<ComparableTestCase> subfirstcase = new ArrayList<ComparableTestCase>();
                SaveObject obj = null;
                while(obj==null){
                    Method f = (Method) method.newInstance();
                    try {
                        do{
                            firstcase = new ComparableTestCase();
                            for (int j = 0; j < dim; j++) {
                                if (j==0)
                                    firstcase.list.add(p.fail_point.get(j)-p.length/2+(p.length)*Math.random());
                                else
                                    firstcase.list.add(p.fail_point.get(j)-p.height_and_width/2+(p.height_and_width)*Math.random());
                            }
                        }while (p.isCorrect(firstcase));
                        obj = f.run(p, subfirstcase, firstcase, pointNum);
                    }catch (Exception e){
                        //e.printStackTrace();
                    }
                }
                sum+=p.cost;
                saves.add(obj);
                starts.add(firstcase);
                fail.add(p.fail_point.toString());
                fail.add(p.length+"/"+p.height_and_width);
            }
            else{
                ProductEllipse p = new ProductEllipse(dimensions,failr,com,ori);
                p.product();
                ComparableTestCase firstcase;
                do{
                    firstcase = new ComparableTestCase();
                    for (int j = 0; j < dim; j++) {
                        firstcase.list.add(p.fail_point.get(j)-p.R+(2*p.R)*Math.random());
                    }
                }while (p.isCorrect(firstcase));
                ArrayList<ComparableTestCase> subfirstcase = new ArrayList<ComparableTestCase>();
                SaveObject obj = null;
                while(obj==null){
                    Method f = (Method) method.newInstance();
                    try {
                        do{
                            firstcase = new ComparableTestCase();
                            for (int j = 0; j < dim; j++) {
                                firstcase.list.add(p.fail_point.get(j)-p.R+(2*p.R)*Math.random());
                            }
                        }while (p.isCorrect(firstcase));
                        obj = f.run(p, subfirstcase, firstcase, pointNum);
                    }catch (Exception e){
                        p = new ProductEllipse(dimensions,failr,com,ori);
                        p.product();
                    }
                }
                sum+=p.cost;
                saves.add(obj);
                starts.add(firstcase);
                fail.add(p.fail_point.toString());
                fail.add(p.c+"/"+p.R);
            }
        }
        Excel.recordNum("result//CostNum//"+method.getSimpleName()+shape+"_FR-"+failr+"_pointNum-"
                        +pointNum+"-compactness"+com+"_rotate-"+ori+".xlsx",
                dims,failr,dim,pointNum,com,ori,saves,starts,shape,repeat,fail);
        return sum/repeat/pointNum;
    }

    public static long getTime(Class method,String shape,int pointNum,int repeat,int dim,double failr,int com,int ori) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ArrayList<SaveObject> saves = new ArrayList<>();
        ArrayList<ComparableTestCase> starts = new ArrayList<>();
        ArrayList<String> fail = new ArrayList<>();
        long sum = 0;
        ArrayList<Dimension> dimensions = new ArrayList<>();
        ArrayList<Integer[]> dims = new ArrayList<>();
        for (int j = 0; j < dim; j++) {
            dimensions.add(new Dimension(-5000,5000));
            dims.add(new Integer[]{-5000,5000});
        }
        for (int i = 0;i<repeat;i++){
            if (shape.equals("R")){
                ProductRectangle p = new ProductRectangle(dimensions,failr,com,ori);
                p.product();
                ComparableTestCase firstcase;
                do{
                    firstcase = new ComparableTestCase();
                    for (int j = 0; j < dim; j++) {
                        if (j==0)
                            firstcase.list.add(p.fail_point.get(j)-p.length/2+(p.length)*Math.random());
                        else
                            firstcase.list.add(p.fail_point.get(j)-p.height_and_width/2+(p.height_and_width)*Math.random());
                    }
                }while (p.isCorrect(firstcase));
                ArrayList<ComparableTestCase> subfirstcase = new ArrayList<ComparableTestCase>();
                SaveObject obj = null;
                while(obj==null){
                    Method f = (Method) method.newInstance();
                    try {
                        do{
                            firstcase = new ComparableTestCase();
                            for (int j = 0; j < dim; j++) {
                                if (j==0)
                                    firstcase.list.add(p.fail_point.get(j)-p.length/2+(p.length)*Math.random());
                                else
                                    firstcase.list.add(p.fail_point.get(j)-p.height_and_width/2+(p.height_and_width)*Math.random());
                            }
                        }while (p.isCorrect(firstcase));
                        obj = f.run(p, subfirstcase, firstcase, pointNum);
                    }catch (Exception e){
                        p = new ProductRectangle(dimensions,failr,com,ori);
                        p.product();
                        //e.printStackTrace();
                    }
                }
                sum+=obj.getTime();
                saves.add(obj);
                starts.add(firstcase);
                fail.add(p.fail_point.toString());
                fail.add(p.length+"/"+p.height_and_width);
            }
            else{
                ProductEllipse p = new ProductEllipse(dimensions,failr,com,ori);
                p.product();
                ComparableTestCase firstcase;
                do{
                    firstcase = new ComparableTestCase();
                    for (int j = 0; j < dim; j++) {
                        firstcase.list.add(p.fail_point.get(j)-p.R+(2*p.R)*Math.random());
                    }
                }while (p.isCorrect(firstcase));
                ArrayList<ComparableTestCase> subfirstcase = new ArrayList<ComparableTestCase>();
                SaveObject obj = null;
                while(obj==null){
                    Method f = (Method) method.newInstance();
                    try {
                        do{
                            firstcase = new ComparableTestCase();
                            for (int j = 0; j < dim; j++) {
                                firstcase.list.add(p.fail_point.get(j)-p.R+(2*p.R)*Math.random());
                            }
                        }while (p.isCorrect(firstcase));
                        obj = f.run(p, subfirstcase, firstcase, pointNum);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                sum+=obj.getTime();
                saves.add(obj);
                starts.add(firstcase);
                fail.add(p.fail_point.toString());
                fail.add(p.c+"/"+p.R);
            }
        }
        Excel.recordTime("result//time//"+method.getSimpleName()+shape+"_FR-"+failr+"_pointNum-"
                        +pointNum+"-compactness"+com+"_rotate-"+ori+".xlsx",
                dims,failr,dim,pointNum,com,ori,saves,starts,shape,repeat,fail);
        return sum/repeat;
    }
}

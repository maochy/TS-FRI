package utils;

import common.ComparableTestCase;
import common.TestCase;

import java.io.*;
import java.text.DecimalFormat;
import java.util.List;

public class Calc {
    //The program of teacher Huang rubing's paper is used to calculate the area of failure area
    public static double calcauate(List<?> finded,int dimension) throws Exception {
        File file3 = new File("qhull-2015.2\\bin\\venus.txt");
        try {
            final FileWriter stream2 = new FileWriter(file3);
            stream2.write(String.valueOf(finded.size()) + "\r\n");
            stream2.write(String.valueOf(dimension) + "\r\n");
            for (int j2 = 0; j2 < finded.size(); ++j2) {
                Object temp5 = finded.get(j2);
                if (temp5 instanceof ComparableTestCase) {
                    ComparableTestCase temp6 = (ComparableTestCase) temp5;
                    for (int i2 = 0; i2 < temp6.list.size(); ++i2) {
                        stream2.write(temp6.list.get(i2) + " ");
                    }
                }
                else {
                    TestCase temp6 = (TestCase) temp5;
                    for (int i2 = 0; i2 < temp6.list.size(); ++i2) {
                        stream2.write(temp6.list.get(i2) + " ");
                    }
                }

                stream2.write("\r\n");
            }
            stream2.close();
        }
        catch (Exception ex) {

        }
        String path = System.getProperty("user.dir");
        final String cmd = "cmd /c cd "+path+"\\qhull-2015.2\\bin && qconvex.exe FA < venus.txt TO result.txt";
        final Runtime run = Runtime.getRuntime();
        try {
            final Process p = run.exec(cmd);
            final InputStream read = p.getInputStream();
            read.read();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }

        File file1  = new File("qhull-2015.2\\bin\\result.txt");
        String line="";
        FileReader stream = new FileReader(file1);
        BufferedReader reader=new BufferedReader(stream);
        boolean file_flag=true;
        while(file_flag&&(line=reader.readLine())!=null){
            if(line.contains("volume")){

                String linetemp[]=line.split("\\s");

                line=linetemp[linetemp.length-1].trim();
                file_flag=false;
            }
        }
        stream.close();
        reader.close();
        double temp6 = 0.0;
        final DecimalFormat df = new DecimalFormat("#0.00000000");
        temp6 = Double.valueOf(line);
        final String temp_string = df.format(temp6);
        temp6 = Double.valueOf(temp_string);
        return temp6;
    }


}

package utils;

import model.*;
import java.io.*;
import java.util.*;
import common.*;
import program.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.lang.reflect.*;

public class GraphParseXML
{
    public SaveObject parseXML(final String file_name) {
        final SaveObject save = new SaveObject();
        try {
            final File f = new File(file_name);
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(f);
            NodeList nl = doc.getElementsByTagName("Dimension");
            final ArrayList<Dimension> dimList = new ArrayList<Dimension>();
            for (int i = 0; i < nl.getLength(); ++i) {
                final String Max = doc.getElementsByTagName("Max").item(i).getFirstChild().getNodeValue();
                final String Min = doc.getElementsByTagName("Min").item(i).getFirstChild().getNodeValue();
                final String range = doc.getElementsByTagName("Range").item(i).getFirstChild().getNodeValue();
                System.out.println(String.valueOf(Max) + " " + Min + " " + range);
                final Dimension dimension = new Dimension(Double.valueOf(Min), Double.valueOf(Max));
                dimList.add(dimension);
            }
            final double failRate = Double.valueOf(doc.getElementsByTagName("FailRate").item(0).getFirstChild().getNodeValue());
            System.out.println(failRate);
            final double Compactness = Double.valueOf(doc.getElementsByTagName("Compactness").item(0).getFirstChild().getNodeValue());
            System.out.println(Compactness);
            final double orientation = Double.valueOf(doc.getElementsByTagName("Orientation").item(0).getFirstChild().getNodeValue());
            System.out.println(orientation);
            final ArrayList<ArrayList<Double>> muti = new ArrayList<ArrayList<Double>>();
            nl = doc.getElementsByTagName("Fail_point");
            final int fail_number = nl.getLength();
            System.out.println(fail_number);
            for (int j = 0; j < fail_number; ++j) {
                final String[] points = nl.item(j).getFirstChild().getNodeValue().split(" ");
                final ArrayList<Double> templist = new ArrayList<Double>();
                String[] array;
                for (int length = (array = points).length, l = 0; l < length; ++l) {
                    final String p = array[l];
                    templist.add(Double.valueOf(p));
                }
                muti.add(templist);
            }
            final ComparableTestCase firstcase = new ComparableTestCase();
            String[] points = doc.getElementsByTagName("First_causing").item(0).getFirstChild().getNodeValue().split(" ");
            String[] array2;
            for (int length2 = (array2 = points).length, n = 0; n < length2; ++n) {
                final String a = array2[n];
                firstcase.list.add(Double.valueOf(a));
            }
            final String method_name = doc.getElementsByTagName("Method_name").item(0).getFirstChild().getNodeValue();
            final Double totalSize = Double.valueOf(doc.getElementsByTagName("totalSize").item(0).getFirstChild().getNodeValue());
            nl = doc.getElementsByTagName("Testcase");
            final ArrayList<ComparableTestCase> subtestcase = new ArrayList<ComparableTestCase>();
            System.out.println("Points's size:" + nl.getLength());
            for (int k = 0; k < nl.getLength(); ++k) {
                final ComparableTestCase test = new ComparableTestCase();
                points = nl.item(k).getFirstChild().getNodeValue().split(" ");
                String[] array3;
                for (int length3 = (array3 = points).length, n2 = 0; n2 < length3; ++n2) {
                    final String a2 = array3[n2];
                    test.list.add(Double.valueOf(a2));
                }
                subtestcase.add(test);
            }
            System.out.println("Testcase" + subtestcase.size());
            String shapeName = doc.getElementsByTagName("ShapeName").item(0).getFirstChild().getNodeValue();
            final Class<?> cls = Class.forName("program." + shapeName);
            Constructor c = null;
            Shape p2 = null;
            if (shapeName.equals("ProductRectangle")) {
                c = cls.getConstructor(ArrayList.class, Double.TYPE, Double.TYPE, Double.TYPE);
                p2 = (Shape) c.newInstance(dimList, failRate, Compactness, orientation);
            }
            else if (shapeName.equals("ProductEllipse")) {
                c = cls.getConstructor(ArrayList.class, Double.TYPE, Double.TYPE, Double.TYPE);
                p2 = (Shape) c.newInstance(dimList, failRate, Compactness, orientation);
            }
            else if(shapeName.equals("ProductCircle")|| shapeName.equals("ProductSquear")){
                c = cls.getConstructor(ArrayList.class, Double.TYPE);
                p2 = (Shape) c.newInstance(dimList, failRate);
            }
            else {
                c = cls.getConstructor();
                p2 = (Shape) c.newInstance();
            }
            p2.mutiple = muti;
            save.setShape(p2);
            save.setFirstcase(firstcase);
            save.setMethodName(method_name);
            save.setTotalSize(totalSize);
            save.setList(subtestcase);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return save;
    }
    
}

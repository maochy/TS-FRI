package utils;

import common.ComparableTestCase;
import model.SaveObject;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Excel {
    public static void recordPrec(String path, ArrayList<Integer[]> inputRegion, double failrate, int dimension, int n, int wh, int angle, ArrayList<SaveObject> saves, ArrayList<ComparableTestCase> starts, String xing, int repeat, ArrayList<String> failRegion){
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        Sheet sheet = workbook.createSheet("sheet");
        Row hssfRow = sheet.createRow(0);
        Cell cell = hssfRow.createCell(0);
        cell.setCellValue("repeat times");
        cell = hssfRow.createCell(1);
        cell.setCellValue("input dimension");
        cell = hssfRow.createCell(2);
        cell.setCellValue("input domain");
        cell = hssfRow.createCell(3);
        cell.setCellValue("shape");
        cell = hssfRow.createCell(4);
        cell.setCellValue("center coordinate");
        cell = hssfRow.createCell(5);
        cell.setCellValue("short side/long side");
        cell = hssfRow.createCell(6);
        cell.setCellValue("initial coordinates");
        cell = hssfRow.createCell(7);
        cell.setCellValue("the number of points");
        cell = hssfRow.createCell(8);
        cell.setCellValue("parameter ψ");
        cell = hssfRow.createCell(9);
        cell.setCellValue("parameter τ");
        for (int i = 1; i <= n; i++) {
            cell = hssfRow.createCell(9+i);
            cell.setCellValue("the"+i+"th coordinates");
        }
        cell = hssfRow.createCell(9+n+1);
        cell.setCellValue("S_ratio");
        double[] bases = {0,0,1e8,1e12,1e16};
        for (int i = 1; i <= repeat; i++) {
            hssfRow = sheet.createRow(i);
            cell = hssfRow.createCell(0);
            cell.setCellValue(String.valueOf(i));
            cell = hssfRow.createCell(1);
            cell.setCellValue(String.valueOf(dimension));
            cell = hssfRow.createCell(2);
            cell.setCellValue(Arrays.deepToString(inputRegion.toArray()));
            cell = hssfRow.createCell(3);
            if (xing.equals("R"))
                cell.setCellValue("rectangle");
            else
                cell.setCellValue("ellipse");
            cell = hssfRow.createCell(4);
            cell.setCellValue(failRegion.get((i-1)*2));
            cell = hssfRow.createCell(5);
            cell.setCellValue(failRegion.get((i-1)*2+1));
            cell = hssfRow.createCell(6);
            cell.setCellValue(starts.get(i-1).list.toString());
            cell = hssfRow.createCell(7);
            cell.setCellValue(String.valueOf(n));
            cell = hssfRow.createCell(8);
            cell.setCellValue(String.valueOf(1000));
            cell = hssfRow.createCell(9);
            cell.setCellValue(String.valueOf(20));
            for (int j = 1; j <= n; j++) {
                cell = hssfRow.createCell(9+j);
                cell.setCellValue(saves.get(i-1).list.get(j-1).list.toString());
            }
            cell = hssfRow.createCell(9+n+1);
            cell.setCellValue(String.valueOf(saves.get(i-1).totalSize/(bases[dimension]*failrate)));
        }

        // save excel
        try {
            OutputStream outputStream;
            outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
    }

    public static void recordNum(String path, ArrayList<Integer[]> inputRegion,double failrate, int dimension, int n, int wh, int angle, ArrayList<SaveObject> saves, ArrayList<ComparableTestCase> starts, String xing, int repeat,ArrayList<String> failRegion){
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        Sheet sheet = workbook.createSheet("sheet");
        Row hssfRow = sheet.createRow(0);
        Cell cell = hssfRow.createCell(0);
        cell.setCellValue("repeat times");
        cell = hssfRow.createCell(1);
        cell.setCellValue("input dimension");
        cell = hssfRow.createCell(2);
        cell.setCellValue("input domain");
        cell = hssfRow.createCell(3);
        cell.setCellValue("shape");
        cell = hssfRow.createCell(4);
        cell.setCellValue("center coordinate");
        cell = hssfRow.createCell(5);
        cell.setCellValue("short side/long side");
        cell = hssfRow.createCell(6);
        cell.setCellValue("initial coordinates");
        cell = hssfRow.createCell(7);
        cell.setCellValue("the number of points");
        cell = hssfRow.createCell(8);
        cell.setCellValue("parameter ψ");
        cell = hssfRow.createCell(9);
        cell.setCellValue("parameter τ");
        for (int i = 1; i <= n; i++) {
            cell = hssfRow.createCell(9+i);
            cell.setCellValue("the"+i+"th coordinates");
        }
        cell = hssfRow.createCell(9+n+1);
        cell.setCellValue("average times");
        for (int i = 1; i <= repeat; i++) {
            hssfRow = sheet.createRow(i);
            cell = hssfRow.createCell(0);
            cell.setCellValue(String.valueOf(i));
            cell = hssfRow.createCell(1);
            cell.setCellValue(String.valueOf(dimension));
            cell = hssfRow.createCell(2);
            cell.setCellValue(Arrays.deepToString(inputRegion.toArray()));
            cell = hssfRow.createCell(3);
            if (xing.equals("R"))
                cell.setCellValue("rectangle");
            else
                cell.setCellValue("ellipse");
            cell = hssfRow.createCell(4);
            cell.setCellValue(failRegion.get((i-1)*2));
            cell = hssfRow.createCell(5);
            cell.setCellValue(failRegion.get((i-1)*2+1));
            cell = hssfRow.createCell(6);
            cell.setCellValue(starts.get(i-1).list.toString());
            cell = hssfRow.createCell(7);
            cell.setCellValue(String.valueOf(n));
            cell = hssfRow.createCell(8);
            cell.setCellValue(String.valueOf(1000));
            cell = hssfRow.createCell(9);
            cell.setCellValue(String.valueOf(20));
            long sum = 0;
            for (int j = 1; j <= n; j++) {
                cell = hssfRow.createCell(9+j);
                cell.setCellValue(saves.get(i-1).shape.allTime.get(j-1));
                sum+=saves.get(i-1).shape.allTime.get(j-1);
            }
            cell = hssfRow.createCell(9+n+1);
            cell.setCellValue(String.valueOf(sum*1.0/n));
        }

        // 保存Excel文件
        try {
            OutputStream outputStream;
            outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
    }

    public static void recordTime(String path, ArrayList<Integer[]> inputRegion,double failrate, int dimension, int n, int wh, int angle, ArrayList<SaveObject> saves, ArrayList<ComparableTestCase> starts, String shape, int repeat,ArrayList<String> failRegion){
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        Sheet sheet = workbook.createSheet("sheet");
        Row hssfRow = sheet.createRow(0);
        Cell cell = hssfRow.createCell(0);
        cell.setCellValue("repeat times");
        cell = hssfRow.createCell(1);
        cell.setCellValue("input dimension");
        cell = hssfRow.createCell(2);
        cell.setCellValue("input domain");
        cell = hssfRow.createCell(3);
        cell.setCellValue("shape");
        cell = hssfRow.createCell(4);
        cell.setCellValue("center coordinate");
        cell = hssfRow.createCell(5);
        cell.setCellValue("short side/long side");
        cell = hssfRow.createCell(6);
        cell.setCellValue("initial coordinates");
        cell = hssfRow.createCell(7);
        cell.setCellValue("the number of points");
        cell = hssfRow.createCell(8);
        cell.setCellValue("parameter ψ");
        cell = hssfRow.createCell(9);
        cell.setCellValue("parameter τ");
        cell = hssfRow.createCell(10);
        cell.setCellValue("cost times(ms)");
        for (int i = 1; i <= repeat; i++) {
            hssfRow = sheet.createRow(i);
            cell = hssfRow.createCell(0);
            cell.setCellValue(String.valueOf(i));
            cell = hssfRow.createCell(1);
            cell.setCellValue(String.valueOf(dimension));
            cell = hssfRow.createCell(2);
            cell.setCellValue(Arrays.deepToString(inputRegion.toArray()));
            cell = hssfRow.createCell(3);
            if (shape.equals("R"))
                cell.setCellValue("rectangle");
            else
                cell.setCellValue("ellipse");
            cell = hssfRow.createCell(4);
            cell.setCellValue(failRegion.get((i-1)*2));
            cell = hssfRow.createCell(5);
            cell.setCellValue(failRegion.get((i-1)*2+1));
            cell = hssfRow.createCell(6);
            cell.setCellValue(starts.get(i-1).list.toString());
            cell = hssfRow.createCell(7);
            cell.setCellValue(String.valueOf(n));
            cell = hssfRow.createCell(8);
            cell.setCellValue(String.valueOf(1000));
            cell = hssfRow.createCell(9);
            cell.setCellValue(String.valueOf(20));
            cell = hssfRow.createCell(10);
            cell.setCellValue(String.valueOf(saves.get(i-1).time*1.0/1000000));
        }

        // save excel
        try {
            OutputStream outputStream;
            outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
    }
}

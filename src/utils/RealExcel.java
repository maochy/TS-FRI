package utils;

import common.ComparableTestCase;
import model.SaveObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RealExcel {
    public static void recordPrec(String path, int dimension, String inputRegion, int n, ArrayList<SaveObject> saves, ArrayList<ComparableTestCase> starts, ArrayList<Double> prec){
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
        cell.setCellValue("initial coordinates");
        cell = hssfRow.createCell(4);
        cell.setCellValue("the number of points");
        cell = hssfRow.createCell(5);
        cell.setCellValue("parameter ψ");
        cell = hssfRow.createCell(6);
        cell.setCellValue("parameter τ");
        for (int i = 1; i <= n; i++) {
            cell = hssfRow.createCell(6+i);
            cell.setCellValue("the "+i+"th coordinates");
        }
        cell = hssfRow.createCell(6+n+1);
        cell.setCellValue("S_ratio");
        for (int i = 1; i <= starts.size(); i++) {
            hssfRow = sheet.createRow(i);
            cell = hssfRow.createCell(0);
            cell.setCellValue(String.valueOf(i));
            cell = hssfRow.createCell(1);
            cell.setCellValue(dimension);
            cell = hssfRow.createCell(2);
            cell.setCellValue(inputRegion);
            cell = hssfRow.createCell(3);
            cell.setCellValue(starts.get(i-1).list.toString());
            cell = hssfRow.createCell(4);
            cell.setCellValue(String.valueOf(n));
            cell = hssfRow.createCell(5);
            cell.setCellValue(String.valueOf(1000));
            cell = hssfRow.createCell(6);
            cell.setCellValue(String.valueOf(20));
            for (int j = 1; j <= n; j++) {
                cell = hssfRow.createCell(6+j);
                cell.setCellValue(saves.get(i-1).list.get(j-1).list.toString());
            }
            cell = hssfRow.createCell(6+n+1);
            cell.setCellValue(prec.get(i-1));
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
    }

    public static void recordNum(String path, int dimension, String inputRegion, int n, ArrayList<SaveObject> saves, ArrayList<ComparableTestCase> starts){
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
        cell.setCellValue("initial coordinates");
        cell = hssfRow.createCell(4);
        cell.setCellValue("the number of points");
        cell = hssfRow.createCell(5);
        cell.setCellValue("parameter ψ");
        cell = hssfRow.createCell(6);
        cell.setCellValue("parameter τ");
        for (int i = 1; i <= n; i++) {
            cell = hssfRow.createCell(6+i);
            cell.setCellValue("the"+i+"th coordinates");
        }
        cell = hssfRow.createCell(6+n+1);
        cell.setCellValue("the average times");
        for (int i = 1; i <= starts.size(); i++) {
            hssfRow = sheet.createRow(i);
            cell = hssfRow.createCell(0);
            cell.setCellValue(String.valueOf(i));
            cell = hssfRow.createCell(1);
            cell.setCellValue(dimension);
            cell = hssfRow.createCell(2);
            cell.setCellValue(inputRegion);
            cell = hssfRow.createCell(3);
            cell.setCellValue(starts.get(i-1).list.toString());
            cell = hssfRow.createCell(4);
            cell.setCellValue(String.valueOf(n));
            cell = hssfRow.createCell(5);
            cell.setCellValue(String.valueOf(1000));
            cell = hssfRow.createCell(6);
            cell.setCellValue(String.valueOf(20));
            long sum = 0;
            for (int j = 1; j <= n; j++) {
                cell = hssfRow.createCell(6+j);
                cell.setCellValue(saves.get(i-1).shape.allTime.get(j-1));
                sum+=saves.get(i-1).shape.allTime.get(j-1);
            }
            cell = hssfRow.createCell(6+n+1);
            cell.setCellValue(String.valueOf(sum*1.0/n));
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
    }


    public static void recordTime(String path, int dimension, String inputRegion, int n, ArrayList<SaveObject> saves, ArrayList<ComparableTestCase> starts,ArrayList<Long> times){
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
        cell.setCellValue("initial coordinates");
        cell = hssfRow.createCell(4);
        cell.setCellValue("the number of points");
        cell = hssfRow.createCell(5);
        cell.setCellValue("parameter ψ");
        cell = hssfRow.createCell(6);
        cell.setCellValue("parameter τ");
        cell = hssfRow.createCell(7);
        cell.setCellValue("cost times(ms)");
        for (int i = 1; i <= starts.size(); i++) {
            hssfRow = sheet.createRow(i);
            cell = hssfRow.createCell(0);
            cell.setCellValue(String.valueOf(i));
            cell = hssfRow.createCell(1);
            cell.setCellValue(dimension);
            cell = hssfRow.createCell(2);
            cell.setCellValue(inputRegion);
            cell = hssfRow.createCell(3);
            cell.setCellValue(starts.get(i-1).list.toString());
            cell = hssfRow.createCell(4);
            cell.setCellValue(String.valueOf(n));
            cell = hssfRow.createCell(5);
            cell.setCellValue(String.valueOf(1000));
            cell = hssfRow.createCell(6);
            cell.setCellValue(String.valueOf(20));
            cell = hssfRow.createCell(7);
            cell.setCellValue(String.valueOf(times.get(i-1)*1.0/1000000));
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
    }

}

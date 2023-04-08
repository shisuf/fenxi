package xyz.shisuf.text;

import com.alibaba.excel.EasyExcel;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class ExcelWriteTest {
    @Test
    public  void Test1(){

        String FileName = "E:/data.xlsx";

        ExcelData data1 = new ExcelData("vc1.txt",265.0,0.481);
        ExcelData data2 = new ExcelData("vc2.txt",265.0,0.461);
        ExcelData data3 = new ExcelData("vc3.txt",265.0,0.441);
        ExcelData data4 = new ExcelData("vc4.txt",265.0,0.421);
        ExcelData data5 = new ExcelData("vc5.txt",265.0,0.401);
        ArrayList<ExcelData> excelData = new ArrayList<>();
        excelData.add(data1);
        excelData.add(data2);
        excelData.add(data3);
        excelData.add(data4);
        excelData.add(data5);

        EasyExcel.write(FileName,EasyExcel.class).sheet("信息").doWrite(excelData);
    }

}

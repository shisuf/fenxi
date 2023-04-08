package xyz.shisuf.Main;

import com.alibaba.excel.annotation.ExcelProperty;

public class ExcelData {
    @ExcelProperty("名称")
    private String FileName;
    @ExcelProperty("波长")
    private String  wave;
    @ExcelProperty("吸光度")
    private String  ABS;

    public String getFileName() {
        return FileName;
    }

    public String getWave() {
        return wave;
    }

    public String getABS() {
        return ABS;
    }

    public ExcelData(String fileName, String  wave, String ABS) {
        FileName = fileName;
        this.wave = wave;
        this.ABS = ABS;
    }
}

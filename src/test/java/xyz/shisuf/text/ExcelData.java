package xyz.shisuf.text;

import com.alibaba.excel.annotation.ExcelProperty;

public class ExcelData {
    @ExcelProperty("名称")
    private String FileName;
    @ExcelProperty("波长")
    private Double wave;
    @ExcelProperty("吸光度")
    private Double ABS;

    public String getFileName() {
        return FileName;
    }

    public Double getWave() {
        return wave;
    }

    public Double getABS() {
        return ABS;
    }

    public ExcelData(String fileName, Double wave, Double ABS) {
        FileName = fileName;
        this.wave = wave;
        this.ABS = ABS;
    }
}

package xyz.shisuf.Main;

import com.alibaba.excel.annotation.ExcelProperty;

public class waveExcel {

    @ExcelProperty("项目")
    private String project;
    @ExcelProperty("1")
    private String name1;

    @ExcelProperty("2")
    private String name2;

    @ExcelProperty("3")
    private String name3;

    @ExcelProperty("4")
    private String name4;

    @ExcelProperty("5")
    private String name5;

    @ExcelProperty("6")
    private String name6;

    @ExcelProperty("7")
    private String name7;

    @ExcelProperty("8")
    private String name8;

    public waveExcel(String project, String name1, String name2, String name3, String name4, String name5, String name6, String name7, String name8) {
        this.project = project;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.name4 = name4;
        this.name5 = name5;
        this.name6 = name6;
        this.name7 = name7;
        this.name8 = name8;
    }

    public String getProject() {
        return project;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getName3() {
        return name3;
    }

    public String getName4() {
        return name4;
    }

    public String getName5() {
        return name5;
    }

    public String getName6() {
        return name6;
    }

    public String getName7() {
        return name7;
    }

    public String getName8() {
        return name8;
    }
}

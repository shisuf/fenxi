package xyz.shisuf.Main;

import com.alibaba.excel.EasyExcel;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String Path = args[0];
        //String Path = "E:\\admin\\Desktop\\folder\\1\\8zu";
        //String Path = "E:\\admin\\Desktop\\folder\\1\\6组";
        //String Path = "E:\\adiver\\emo\\www\\fenxi\\out\\artifacts\\fenxi_jar\\5组";
        //
        System.out.println("请检查有无不规范文件！！！");
        System.out.println("最好只保留需要分析的文件！！！");
        //
        //初始化数据处理方式
        Scanner scanner = new Scanner(System.in);
        double[] doubles = initForm(scanner);

        File file = new File(Path);
        File[] files = file.listFiles();
        File dataDir = new File("data/");
        File dealDir = new File("data_deal/");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        if (!dealDir.exists()) {
            dealDir.mkdir();
        }

        //存储txt文件的数组
        ArrayList<File> TXTs = new ArrayList<File>();

        //储存最大吸收处的波长及数据的文件
        File txtFile = new File("data_deal/data.txt");
        if (!txtFile.exists()) {
            txtFile.createNewFile();
        }
        //创建文件写入流
        BufferedWriter txtW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(txtFile, true)));

        //用map保存treemap
        Map<String, TreeMap<Double, Double>> maps = new HashMap<>();
        //用map保存用户选择的文件给方法maxWave分析选择最大波长
        Map<String, TreeMap<Double, Double>> waveMap = new HashMap<>();

        for (int i = 0; i < files.length; i++) {
            String DataName = "data/" + files[i].getName() + ".properties";
            String DealName = "data_deal/" + files[i].getName();
            //
            if (ident(files[i].getName()) != null) {
                TXTs.add(files[i]);
                //写入文件数据
                writeFile(DataName, replaceSpace(readFile(files[i])));

                //写入排序后数据
                Map<Double, Double> map = dataLoad(DataName, doubles);
                BufferedWriter writer =
                        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DealName, true)));
                for (Map.Entry<Double, Double> entry : map.entrySet()) {
                    writer.write(entry.getValue() + "\t" + entry.getKey() + "\n");
                    writer.flush();
                }
                TreeMap<Double, Double> treeMap = (TreeMap) map;
                writer.write("max:\n" + treeMap.lastEntry());
                writer.close();
                //把treemap加入maps
                maps.put(files[i].getName(), treeMap);
                //写出txt文件
                txtW.write(files[i].getName() + "中的最大吸收波长及其值(ABS=nm):" + treeMap.lastEntry() + "\n");
                txtW.flush();
            }

        }

        //显示文件名称
        for (Map.Entry<String, TreeMap<Double, Double>> entry : maps.entrySet()) {
            System.out.print("\n是否需要使用该文件:"+"\n"+entry.getKey()+"\n(y是,n否,默认y):");
            String next = scanner.next();
            if (next.equals("n")) {
                System.out.println("取消选择文件:" + entry.getKey());
            } else {
                waveMap.put(entry.getKey(), maps.get(entry.getKey()));
                System.out.println("已选择:" + entry.getKey());
            }
        }
        //分析出现次数最多的,并写入文件
        String key = maxWave(waveMap, txtW);
        //用次数最多的创建表格
        excelDeal(waveMap, key);

        //waveMap给出文件名称，去data中寻找对应的文件进行读写
        //list中储存对应文件名字
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, TreeMap<Double, Double>> entry: waveMap.entrySet()){
            list.add(entry.getKey());
        }
        wExcel(list,doubles);

    }

    //##方法
    //查询txt文件并返回名字
    public static String ident(String name) {
        String sub = name.substring(name.lastIndexOf("."));
        if (sub.equals(".TXT") || sub.equals(".txt")) {
            return name;
        } else {
            return null;
        }
    }

    //读取文件并返回字符串
    public static String readFile(File file) {
        try {
            BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
            String s = "";
            byte[] buf = new byte[99999];
            int len;
            while ((len = stream.read(buf)) != -1) {
                s = new String(buf, 0, len);
            }
            return s.substring(s.lastIndexOf("nm\tAbs"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //把数据写入data.properties
    public static void writeFile(String DataName, String s) {
        File file = new File(DataName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(s);
                writer.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    //把'	'转换为等号并返回
    public static String replaceSpace(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '	') {
                builder.append('=');
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    //将data.properties中数据读取出来放入Map中并返回
    public static Map<Double, Double> dataLoad(String Path, double[] doubles) {
        File file = new File(Path);
        Properties properties = new Properties();
        //
        //
        DecimalFormat df_nm = new DecimalFormat("####.00");
        double NBegin = Double.parseDouble(df_nm.format(doubles[0]));
        double NEnd = Double.parseDouble(df_nm.format(doubles[1]));
//
        Map<Double, Double> map = new TreeMap<>();
        try {
            properties.load(new FileReader(file));
            //
            for (double nm = NBegin; nm < NEnd; nm = nm + doubles[2]) {
                String property = properties.getProperty(df_nm.format(nm));
                double ABS = Double.parseDouble(property);
                map.put(ABS, nm);
            }
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //初始化数据处理格式
    public static double[] initForm(Scanner scanner) {
        double[] doubles = new double[3];
        System.out.println("请输入开始波长(默认200.00):");
        doubles[0] = scanner.nextDouble();
        if (doubles[0] <= 0) {
            doubles[0] = 200.0;
        }
        System.out.println("请输入结束波长(默认400.00):");
        doubles[1] = scanner.nextDouble();
        if (doubles[1] <= doubles[0]) {
            doubles[1] = 400.0;
        }
        System.out.println("请输入几nm一取样(填错了不行哦！！！默认5):");
        doubles[2] = scanner.nextInt();
        if (doubles[2] <= 0) {
            doubles[2] = 5;
        }
        System.out.println(doubles[0] + "  " + doubles[1] + "  " + doubles[2]);
        return doubles;
    }

    //分析数据选择最大波长
    public static String maxWave(Map<String, TreeMap<Double, Double>> map, BufferedWriter writer) {
        //用HashMap来储存最大波长及出现次数
        Map<Double, Integer> indexes = new HashMap<>();
        for (Map.Entry<String, TreeMap<Double, Double>> entry : map.entrySet()) {

            Double key = entry.getValue().get(entry.getValue().lastKey());
            //不相同着创建一个，相同则改变数值
            if (indexes.get(key) == null) {
                indexes.put(key, 1);
            } else {
                indexes.put(key, indexes.get(key) + 1);
            }
        }
        //储存出现最多次数及对应波长
        double max_nm = 0.0;
        int index = 0;

        //将出现次数写入文件
        try {
            for (Map.Entry<Double, Integer> entry : indexes.entrySet()) {
                if (entry.getValue() > index) {
                    max_nm = entry.getKey();
                    index = entry.getValue();
                    writer.write(entry.getKey() + "出现" + entry.getValue() + "次");
                    writer.flush();
                }
            }

            writer.write(max_nm + "出现最多次数:" + index);
            writer.close();
            System.out.println(max_nm + "出现最多次数:" + index);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return max_nm + "0";
    }

    //读取数据写入excel
    public static void excelDeal(Map<String, TreeMap<Double, Double>> map, String key) {
        System.out.println("选择波长为:" + key);
        ArrayList<ExcelData> data = new ArrayList<>();
        //文件一个个处理
        try {
            for (Map.Entry<String, TreeMap<Double, Double>> entry : map.entrySet()) {
                String FileName = "data/" + entry.getKey() + ".properties";
                Properties properties = new Properties();
                properties.load(new FileReader(FileName));
                String ABS = properties.getProperty(key);//选择的波长对应的吸光度
                System.out.println(entry.getKey() + ": 波长:" + key + " 吸光度:" + ABS);
                //接下来创建表格,
                data.add(new ExcelData(entry.getKey(),key,ABS));
            }
            //写入excel
            String FileName = "data.xlsx";
            EasyExcel.write(FileName,ExcelData.class).sheet("最大波长吸光度").doWrite(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    //写入普通的波长与吸光度的关系，文件为浓度相关，及waveMap中存放的文件
    public static void wExcel(ArrayList<String> list,double[] doubles){
        ArrayList<waveExcel> waveExcels = new ArrayList<>();
        if (list.size()>=8){
            waveExcel fileName = new waveExcel("项目",list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7));
            waveExcels.add(fileName);
            //用数组储存properties文件
            ArrayList<Properties> properties = new ArrayList<>();
            try {
                for (int i = 0;i <list.size();i++){
                    String Path = "data/" + list.get(i) + ".properties";
                    Properties property = new Properties();
                    property.load(new FileReader(Path));
                    properties.add(property);
                }

                //根据波长及取样点进行读取
                double NBegin = doubles[0];
                double NEnd = doubles[1];
                for (double nm = NBegin;nm <=NEnd;nm += doubles[2]){
                    waveExcel waveExcel = new waveExcel(nm + "0", properties.get(0).getProperty(nm + "0"), properties.get(1).getProperty(nm + "0"), properties.get(2).getProperty(nm + "0"), properties.get(3).getProperty(nm + "0"), properties.get(4).getProperty(nm + "0"), properties.get(5).getProperty(nm + "0"), properties.get(6).getProperty(nm + "0"), properties.get(7).getProperty(nm + "0"));
                    waveExcels.add(waveExcel);
                }
                //写入excel
                String EXCELPath = "波长吸光度.xlsx";
                System.out.println(waveExcels.size());
                EasyExcel.write(EXCELPath, waveExcel.class).sheet("波长吸光度").doWrite(waveExcels);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else {
            System.out.println("生成波长与吸光度.excel失败");
        }

    }
}

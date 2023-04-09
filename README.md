# 做仪器分析后数据很多，于是傻傻的花了三天写了一个没啥用的程序帮忙分析

## 都是为了方便分析最大波长做图，谁懂呀！

## 1.nm	Abs作为分割依据

![image-20230408181405545](https://raw.githubusercontent.com/shisuf/fenxi/main/imges/image-20230408181405545.png)

类似这种数据，然后分析8个自己用的数据并生成excel

## 2.data.xlsx文件中存放的是

![image-20230408174122475](https://raw.githubusercontent.com/shisuf/fenxi/main/imges/image-20230408174122475.png)

这八次重复最多次数的最大吸收波长和对应的吸光度

会在data_deal中写出最多次数，但是有点乱

262.0出现2次263.0出现3次263.0出现最多次数:3

## 3.方便的当然是数据点最多的:波长吸光度.xlsx

200多条数据分把钟就插进去了

![image-20230408174705319](https://raw.githubusercontent.com/shisuf/fenxi/main/imges/image-20230408174705319.png)

谁懂啊，怎么方便想用用，却发现只有自己这样用吧

## 4.最重要的当然是使用方法了

### 1.第一步自然是打开cmd然后运行jar文件(前提是好像得装java)

```shell
java -jar fenxi.jar "这里是你的文件夹的名称，自然路径也是可以的"
```

然后简简单单的按下回车

### 2.现在开始输入关键的格式了吧

默认是200开始，400结束，每5nm取一个样点(别问我为什么，当初设计1个默认的时候打脸了的)

![image-20230408175258329](https://raw.githubusercontent.com/shisuf/fenxi/main/imges/image-20230408175258329.png)

![image-20230408175705527](https://raw.githubusercontent.com/shisuf/fenxi/main/imges/image-20230408175705527.png)

然后回车

### 3.第三步是选自己要装进excel里的文件名称(8个，少一个会报警，多了只能装8个)！！！默认是插入哦，注意看清楚！

![image-20230408180038897](https://raw.githubusercontent.com/shisuf/fenxi/main/imges/1680949328870.png)

就这样选完之后等一下就会生成两个excel

![image-20230408180208935](https://raw.githubusercontent.com/shisuf/fenxi/main/imges/image-20230408180208935.png)

然后数据就可以很方便的提取出来了

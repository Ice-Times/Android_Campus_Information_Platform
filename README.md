# 校园信息平台

## 运行客户端
在AndroidStudio运行即可

## 运行服务端

需要改一下文件存储路径改成自己的。
```
//service.java
29:			public static String path="D://service//1.txt";
174:                    File file=new File("D://service//1.txt");

//service2.java
39:			String url = "D://service//IMG/"+createtime+".jpg";
65:			FileWriter  writer = new FileWriter("D://service//1.txt",true);
86:			file=new File("D://service//1.txt");

//service3.java
26:			public static String path="D://service//IMG";
27:			public static String path2="D://service//login.txt";


```

在命令行运行
```
java service.class
java service2.class
java service3.class
```

## 运行效果

[演示视频](https://www.bilibili.com/video/BV1di4y1G7P1/)

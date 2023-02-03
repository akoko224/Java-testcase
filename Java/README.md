# Sample



```bash
以下四个参数均支持多参传入
--classes sample\java\target\demo.jar 指定扫描目标。任意 .class 目录或者 jar 包
--auxclasspath sample\java\target\lib 添加分析依赖，但分析器不会从中分析 bug
--onlyAnalyze 
  com.fy.demo.-             递归扫描 com.fy.demo 下的所有 classes
  com.fy.demo.testcode.*    仅扫描 com.fy.demo.testcode 包下的 classes
--jsrcpath
  sample\java\      扫描目标对应的源码(父)目录
  other\       
```

eg1:

```shell
 --classes sample\java\target\demo.jar  --auxclasspath sample\java\target\lib --onlyAnalyze com.fy.demo.- --jsrcpath sample\java\
```

eg2: 推荐   

注：必须使用 --onlyAnalyze 参数选取分析目标

```shell
 --classes sample\java\target\  --onlyAnalyze com.fy.demo.- --jsrcpath sample\java\src\
```


**如过没有指定源码目录或者不正确，在 Convert 阶段将会产生如下警告**

`[WARNING] - No source file found: com/fy/demo/testcode/CWE_22_path_traversal.java`


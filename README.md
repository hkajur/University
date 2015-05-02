# University

University Student Database Project

### Prequisites
In order to run the following program you need to have mysql jar file added to your classpath or at least download to your current folder

If you already have the jar file, then in order to compile the program, run the following command in terminal

```bash
  javac Drivers.java MysqlConnect.java Menu.java
```

In order to run the program, you should run the following command

```bash
  java Menu
```

If you don't have the mysql jar added into your ClassPath, you need to run the following commands:

In order to compile:

```bash
  javac -cp mysql-connector-java-5.1.35-bin.jar MysqlConnect.java Drivers.java Menu.java
```

In order to run the program:

```bash
  java -cp .:mysql-connector-java-5.1.35-bin.jar Menu
```



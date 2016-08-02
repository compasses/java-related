# Jave read and practice

# Maven
生成简单的工程：

```
mvn archetype:generate -DgroupId=com.mycompany.helloworld -DartifactId=helloworld -Dpackage=com.mycompany.helloworld -Dversion=1.0-SNAPSHOT
```
man package 打包

 运行：
 ```
 java -cp target/helloworld-1.0-SNAPSHOT.jar com.mycompany.helloworld.App
 ```

可以看到运行时的全部POM内容：
```
mvn help:effective-pom
```

POM 中，groupId, artifactId, packaging, version 叫作 maven 坐标，它能唯一的确定一个项目。maven 插件；maven生命周期，mvn package 生命周期的一个阶段，执行直到package 结束。

1.process-resources 阶段：resources:resources compile 阶段：compiler:compile process-classes 阶段：(默认无目标) process-test-resources 阶段：resources:testResources test-compile 阶段：compiler:testCompile test 阶段：surefire:test prepare-package 阶段：(默认无目标) package 阶段：jar:jar
Maven 依赖管理 自动下载所需依赖到本地库；

2.mvn install. 会将包安装到本地库:~/.m2/repository
# IDE
[IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows) community版，总体上比eclipse好用。


# BOOKS
### [Thinking in Java](http://compasses.github.io/2016/07/26/book-review-thinking-in-java/)

##

## Java 并发编程系列

### 并发基础：
1. 绝大多数时间，所有的线程都应该以默认的优先级运行。试图操纵优先级通常是一种错误。
2. Thread 类自身不执行任何操作，它只是驱动赋予它的任务。
3. 共享资源一般是以对象形式存在的内存片段，但也可以是文件、输入、输出端口。
4. 可视性问题远比原子性问题多得多。理解原子性和易变性不是相同的概念很重要。非volatile域上的原子操作不必刷新到主存中，因此其他看到该域的任务不必是最新的值。如果多个任务在同时访问某个域，那么这个域应该是volatile的，否则这个域应该经由同步来访问。同步也会导致向主存中刷新。因此如果一个域完全由synchronized方法或语句块来保护，那就不必要将其设置为volatile。
5. 使用volatile而不是synchronized的唯一安全的情况是类中只有一个可变的域。
6. I/O 具有锁住你的多线程程序的潜在可能。nio能够支持让阻塞的NIO通道会自动地响应中断。
7. 同一个互斥操作可以被同一个任务多次获得。Java SE5并发类库中的ReentrantLock上阻塞的任务具备可以被中断的能力。
8. 线程间协作，wait，notify，notifyAll是object 的一部分而不是Thread的。
9. while 惯用法：
  ```
  while (conditionIsNotMet)
    wait();
  ```
  可以保证你退出循环之前，条件将得到满足。



## 深入java虚拟机

### java 虚拟机是什么
1.抽象规范
2.一个具体的实现
3.一个运行中的虚拟机实例


### java虚拟机的组成：
类装载器子系统
执行引擎
没有寄存器
方法区
堆
程序计数器
java 栈
栈帧
本地方法栈
虚拟机实现线程：必须同事支持两个方面：对象锁定，线程等待和通知。
java虚拟机通过装载、连接、初始化一个JAVA类型。连接分为三个步骤：验证、准备、解析。
每个类或接口首次主动使用时初始化。

1. 当创建某个类的新实例时；
2. 当调用某个类的静态方法时；
3. 当使用某个类或接口的静态字段，或对该字段赋值时。用final修饰的静态字段除外，它被初始化为一个编译时的常量表达式。
4. 当调用Java API中的某些反射方法时。
5. 当初始化某个类的子类时。
6. 当虚拟机启动时某个被标明为启动的类。

任何一个类的初始化都需要它的所有祖先类预先被初始化，而一个接口的初始化，并不需要祖先接口预先被初始化。
被动使用：

1. 使用一个非常量的静态字段只有当类或者接口的确声明了这个字段时才是主动使用。例子说明
  ```
class NewParent{
    static int hoursOfSleep = (int)(Math.random()*3.0);
    static {
        System.out.println("New Parent initilized ");
    }
}

class NewBornBaby extends NewParent {
    static  int hoursOfCrying = 6 + (int)(Math.random()*2.0);
    static {
        System.out.println("New baby was initialized");
    }
}

class Example{
    public static void main(String[] args) {
        int hours = NewBornBaby.hoursOfSleep;
        System.out.println(hours);
    }

    static {
        System.out.println("example was initialized");
    }
}
```
2. 如果一个字段即是静态的又是最终的，并且使用一个编译时常量表达式初始化。
### 对象的生命周期

1. 类实例化
	1. 四种途径：new操作符、调用Class、java.lang.reflect.Constructor对象的newInstance方法、调用任何现有对象的clone方法、或者使用java.io.ObjectInputStream类的getObject方法反序列化。
	2. 初始化：

		1. 虚拟机完成对象的内存分配和赋值默认初始值后， 执行初始化；
		2. clone和readObject通过输入进行初始化；
		3. 其他调用类的实例初始化方法。java编译器会为每一个类的构造方法产生一个init方法。

	3. 垃圾收集&卸载
		1. 终结方法：名为finalize 返回void，垃圾收集器会在释放这个实例所占据的内存之前执行这个方法一次。
		2. 从类型数据，java虚拟机必须能够确定对象的类，它的所有超类以及所有超接口的class实例。

连接模型

1. 动态连接和解析
	1. 常量池：每个class文件都把它所引用符号保存在这里。常量池解析：把符号引用变成直接引用。
	2. 早解析和迟解析：虚拟机选择解析的时机，最开始全部解析还是直到引用时才去解析class文件。


#Java 8
## interface
1. default methods 支持。
2. static methods 支持。
## New Date and Time API
Domain-driven design and Immutability-for thread safe

## CompletableFuture
可以声明式的处理和合并多个异步任务。
```
findBestPrice("iPhone6")
        .thenCombine(lookupExchangeRate(Currency.GBP),
this::exchange)
.thenAccept(localAmount -> System.out.printf("It will cost
    you %f GBP\n", localAmount));

private CompletableFuture<Price> findBestPrice(String product Name) {
return CompletableFuture.supplyAsync(() -> priceFinder.find BestPrice(productName));
}

private CompletableFuture<Double> lookupExchangeRate(Currency localCurrency) {
return CompletableFuture.supplyAsync(() -> exchangeService.lookupExchangeRate(Currency.USD, localCur
rency)); }
```
## Optional
```
public String getCityForEvent(int id) {
  Optional.ofNullable(getEventWithId(id)).flatMap(this::getLocation) .map(this::getCity) .orElse("TBC");
}
```
At any point, if a method returns an empty Optional, you get the default value "TBC".

## Method Reference Lambda Expressions
### Lambda Expression
1. why lambda expression: behavior parameterization
2. what：anonymous function can be  passed around
3. how: (parameters) -> expression ; (parameters) -> { statements；}
4. where to use：@FunctionalInterface：A functional interface is an interface that declares a single abstract method.
### Method Reference
1. A method reference to a static method:
  ```
  Function<String, Integer> converter = Integer::parseInt;
  Integer number = converter.apply("10");
  ```
2. A method reference to an instance method. Specifically, you’re referring to a method of an object that will be supplied as the first parameter of the lambda:
 ```
 Function<Invoice, Integer> invoiceToId = Invoice::getId;
 ```
3. A method reference to an instance method of an existing object:
```
Consumer<Object> print = System.out::println;
```
Specifically, this kind of method reference is very useful when you want to refer to a private helper method and inject it into another method:
```
File[] hidden = mainDirectory.listFiles(this::isXML);
private boolean isXML(File f) { return f.getName.endsWith(".xml");
}
```
4. A constructor reference:
```
Supplier<List<String>> listOfString = List::new;
```

## Stream
1. what：a sequence of elements from a source that supports aggregate operations.
  1. Sequence of elements
  2. Source, consume
  3. aggregate operations , filter, map, reduce, findFirst, allMatch, sorted.
  4. Pipelining
  5. Internal Iteration
2. Filtering
  1. filter, distinct, limit, skip
3. Matching
  1. anyMatch, allMatch, noneMatch
4. Finding
  1. findFirst, findAny, all return Optional  Object
5. Mapping
  1. map, function argument to map new elements
6. Reducing
  1. two arguments：An initial value， A BinaryOperator<T>.
  ```
    int product = numbers.stream().reduce(1, (a, b) -> a * b); int max = numbers.stream().reduce(Integer.MIN_VALUE,
    Integer::max);
  ```
## Collectors
```
Map<Customer, List<Invoice>> customerToInvoices
= invoices.stream().collect(Collectors.group
ingBy(Invoice::getCustomer));
```

# 框架
## Spring
1. org.springframework.beans 和 org.springframework.context 是Spring IoC的基础。BeanFactory提供了配置框架的基础功能。ApplicationContext是更加面向企业级的功能，是BeanFactory 的超集。

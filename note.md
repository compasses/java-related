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

## java编程思想
### 第五章 初始化与清理
1. 根据参数列表不同进行函数重载，无法根据返回值进行函数重载。因为函数调用者不关心函数的返回值，编译器就无法判定。参数列表的不同顺序也能区分不同的函数，但是这样维护性不够好，不建议这样做。
2. 如果自己类里面已经定义了一个构造器，无论是否有参数，编译器都不会再生成默认的构造器了，跟C++一样。
3. this 关键字代表当前对象，可以用this调用一个构造器，但是不能同时调用两个；也必须将构造器置于最开始的地方，否则编译器会报错。
4. finalize 用于验证终结条件。垃圾回收和终结，都不保证一定会发生。如果java虚拟机并未面临内存耗尽的情况。
5. 在定义类成员变量的地方为其赋值。构造器初始化之前，类成员变量就已经进行了初始化。类内部，变量定义的先后顺序决定了初始化的顺序。即使变量定义散布于方法定义之间，他猛仍旧会在任何方法被调用之前得到初始化。
6. 静态初始化只在Class 对象首次加载的时候进行一次。非静态实例初始化，与静态初始化子句一模一样，只是少了static关键字。这种语法对于支持匿名内部类的初始化是必须的。
7. 可变参数列表：void f(Object... args),除了Object之外，其他的数据类型也可以作为可变参数列表。

### 第六章 访问权限控制
1. 访问权限等级：public，protected，包访问权限，private。
2. 一个java源文件为一个编译单元，每个编译单元只能有一个public类。java可运行程序是一组可以打包压缩为一个java文档文件的.class文件，java解释器负责这些文件的查找、装载和解释。
3. 包访问权限，默认包访问权限没有任何关键字。默认包：处于相同的目录并且没有给自己设定任何包名称，这样的文件自动看作是隶属于该目录的默认包之中。
4. 类既不可以是private的，也不可以是protected。如果没能为类访问权限指定一个访问修饰符，它就会默认得到包访问权限。相同目录下的所有不具有明确package声明的文件，都被视作该目录下默认包的一部分。

### 第七章 复用类
1. 组合语法和继承语法，带参数的构造器，使用super调用，并传递适当的参数。组合技术通常用于在新类中使用现有类的功能而非它的接口这种情形。
protected：任何继承于此类的导出类或者其他任何位于同一个包内的类来说，是可以访问的。protected提供了包内访问权限。
2. 代理：将一个成员对象置于所要构造的类中，就像组合，但与此同时我们在新类中暴露了该成员对象的所有方法，就像继承。
3. 覆写与重载：@Override
4. 向上转型：导出类转换为基类。
5. final：一个既是static又是final的域只占据一段不能改变的存储空间。Blank Final：必须在域的定义处或者每个构造器中用表达式对final进行赋值。final参数，这一特性主要用来想匿名内部类传递数据。final方法，只有在想明确禁止覆盖时，才将方法设置为final。final和private关键字，所有的private的方法都隐式地指定为final的。final类，类不做任何改动，不能继承。

### 第八章 多态
1. 面向对象程序设计语言中，多态是继数据抽象和继承之后的第三种基本特征。
2. java中除了static方法和final方法之外，其他所有的方法都是后期绑定。只有非private的方法才能被覆盖。
3. 初始化过程：将分配给对象的存储空间初始化成二进制的零；调用基类构造器；按照声明的顺序调用成员的初始化方法；调用导出类的构造器主体。基类构造器内调用final方法，既不能被覆盖的方法。
4. 用继承表达行为间的差异，并用字段表达状态上的变化。

### 第九章 接口
1. 接口和内部类为我们提供了一种将接口与实现分离的更加结构化的方法
2. 抽象方法，abstract void f（），包含抽象方法的类为抽象类。
3. interface关键字使抽象的概念更向前迈进了一步。提供了接口部分，没有提供任何相应的具体实现，接口被用来建立类与类之间的协议。
4. 创建一个能够根据所传递的参数对象的不同而具有不同行为的方法，被称为策略设计模式。通过接口容易实现策略模式，同一个入口点，不同的实现实例来调用。
    适配器设计模式：适配器中的代码将接受你所拥有的接口，并产生你所需要的接口。将接口从具体实现中解耦使得接口可以应用于多种不同的的具体实现。
    ```
    //FilterAdapter 为一个适配器，实现了process 接口。
    class FilterAdapter implements Processor {
    }
    ```

5. 使用接口的原因：可以能够向上转型为多个基类型；使用抽象基类相同，防止客户端程序员创建该类的对象，并确保这仅仅是建立一个接口。接口的一种常见的用法就是策略设计模式。
6. 通过interface关键字提供伪多重继承机制，让方法接受接口类型，是一种让任何类都可以对该方法进行适配的方式。
7. 接口中的变量都是自动是static和final的。接口是实现多重继承的途径，而生成遵循某个接口对象的典型方式就是工厂方法设计模式，在工厂对象上调用的是创建方法，而该工厂对象将生成某个实现的对象。通过这种方式，代码完全与接口的实现分离，这样可以透明的使得我们将某个实现替换为另一种实现。
8. 优先选择类而不是接口，从类开始，如果接口的必需性变得非常明确，那么就进行重构。
9. interface前面加上关键字public，则所有成员都是public的，否则只具有包访问权限。

### 第十章 内部类
1. 内部类自动拥有对其外围类所有成员的访问权。构建内部类时，需要一个指向其外围类对象的引用。内部类对象会秘密的捕获一个外部类对象的引用。拥有外部类对象之前是不可能创建内部类对象的。因为内部类对象会暗暗地连接到创建外部类对象上，除非是嵌套类，即静态内部类。
2. 匿名内部类，工厂方法可以使用匿名内部类实现。
3. 嵌套类：将内部类声明为static。创建嵌套类对象，不需要其外围类的对象；不能从嵌套类的对象中访问非静态的外围类对象。
4. 为啥需要内部类：每个内部类都能独立继承自一个实现，所以无论外围类是否已经继承了某个实现，对于内部类没有影响。它使得多重继承的解决方案变得完整，内部类允许继承多个非接口类型。
5. 闭包：是一个可调用的对象，它记录了一些信息。内部类是面向对象的闭包，通过内部类实现闭包，即可实现回调。

### 第十一章 持有对象

### 第十二章通过异常处理错误

### 第十三章 字符串
1. StringBuilder创建字符串更加高效；

### 第十四章 类型信息
1. RTTI：运行时，识别一个对象的类型；java使用Class对象执行RTTI。RTTI必须在编译时已知的对象。instanceof保持了类型的概念，class比较没有考虑继承，只看是否为这个确切的类型。
2. 反射：反射机制，.class文件在编译时是不可获取的，在运行时打开和检查class文件。
3. 类三个步骤：加载、链接、初始化。instanceof 保持了类型的概念。


### 第十五章 泛型
1. 主要目的之一：用来指定容器要持有什么类型的对象，而且编译器来保证类型的正确性。
2. 泛型方法，泛型方法不必指明参数类型，，编译器会为我们找出具体的类型，这称为类型参数推断。
3. 在泛型代码内部，无法获得任何有关泛型参数类型的信息。
4. 装饰器模式使用分层对象来动态的透明地向单个对象添加责任。
5. Java泛型是通过擦除来实现的。类型参数将擦除到它的第一个边界。类型变量在没有指定边界的情况下被擦除为Object。

### 第十九章 枚举类型
1. switch中可以直接使用enum。
2. 命令模式：需要有一个只有单一方法的接口，然后从该接口实现具有各种不同行为的多个子类。

```
interface Command {void action();}

EnumMap<enumType, Command> em = new EnumMap<enumType, Command>(enumType.class);
em.put(ACTION_ONE, new Command() {
  public void action() {print("ACTION_ONE fired!");}
  })
```
3. enum 实例可以编写方法，从而为每个enum实例赋予不同的行为。
4. 职责链模式：程序员以多种不同的方式来解决一个问题然后将它们链接在一起。当一个请求到来时，它遍历整个链，直到链中的某个解决方案能够处理该请求。
5. enum类型非常适合创建状态机。
6. 多路分发，java只支持单路分发。例如a.plus(b)，不知道a，b的类型时，必须自己来判定其他的类型，从而实现自己的动态绑定行为。
  1. 使用接口超类，每个类型实现接口。
  2. 使用enum分发。



### 第二十一章并发

1. Java的线程机制来自C的低级pthread线程。
2. synchronized，同步方法，不属于方法特征签名，覆盖方法上使用。volatile解决可视性问题。``` javap -c classname ``` 看class字节码。
3. 临界区也叫同步控制块，一个代码段，多个线程同时访问。同一个互斥锁可以被同一个线程多次获得。



# 框架
## Spring
1. org.springframework.beans 和 org.springframework.context 是Spring IoC的基础。BeanFactory提供了配置框架的基础功能。ApplicationContext是更加面向企业级的功能，是BeanFactory 的超集。
2. Constructor-based dependency injection
3. Setter-based dependency injection

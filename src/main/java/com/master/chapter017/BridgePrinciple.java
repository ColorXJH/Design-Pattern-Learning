package com.master.chapter017;


import java.sql.Driver;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 桥接模式
 * @date 2021/3/4 10:57
 */
public class BridgePrinciple {
    public static void main(String[] args) {
        /*桥接模式--抽象和具体分层（两个不同的维度分层），抽象聚合（组合）具体*/
        //获取折叠手机（样式加品牌）
        Phone phone=new ZDPhone(new XiaoMi());
        phone.open();
        phone.call();
        phone.close();
        Phone phone1=new XZPhone(new HuaWei());
        phone1.open();
        phone1.call();
        phone1.close();
        //jdbc桥接模式源码分析
        //com.mysql.cj.jdbc.Driver
        Driver driver=null;
        //getConnection是基于接口的实现（得到各个不同的connection）
        //与标准的桥接模式不同在于：DriverManager是一个具体的类（拿掉了抽象层），不是抽象类，它依赖/使用Connection接口




    }
}

//问题展开：手机操作问题
//现在对不同手机类型的不同品牌实现操作编程：比如开机，关机，上网，打电话，如下
//传统方式：手机--》折叠式，直立式，旋转式--》华为，小米，vivo
//类图会很膨胀：phone下有三种类型，类型下有三种品牌 ，每个方法都需要实现不同的子类
//1：扩展性问题（类爆炸），如果我们再增加手机的样式（旋转式），就需要增加各个品牌手机的类，
    //同样如果我们增加一个手机品牌，也要在各个手机样式下增加
//2：违反了单一职责原则，当我们增加手机样式时，要同事增加所有品牌的手机，增加了代码的维护成本
//3：解决方式==》桥接模式

//基本介绍
//1：桥接模式是指：将实现与抽象放在两个不同的类层次中，使两个层次可以独立改变
//2: 这是一种结构型设计模式
//3：bridge基于类的最小设计原则，通过使用封装，聚合以及继承等行为让不同的类承担不同的职责，它的主要特点是把抽象
    //与行为现实分离开来，从而可以保持各部分的独立性以及应对他们的功能扩展

//代码实现
/*品牌接口*/
interface  Brand{
    void open();
    void close();
    void call();
}

class XiaoMi implements Brand{

    @Override
    public void open() {
        System.out.println("小米手机开机了");
    }

    @Override
    public void close() {
        System.out.println("小米手机关机了");
    }

    @Override
    public void call() {
        System.out.println("小米手机打电话");
    }
}

class HuaWei implements Brand{

    @Override
    public void open() {
        System.out.println("华为手机开机了");
    }

    @Override
    public void close() {
        System.out.println("华为手机关机了");
    }

    @Override
    public void call() {
        System.out.println("华为手机打电话");
    }
}
/*手机抽象*/
abstract class Phone{
    /*组合品牌*/
    private Brand brand;
    /*构造器*/
    public Phone(Brand brand){
        super();
        this.brand=brand;
    }
    /*抽象表现*/
    protected  void open(){
        brand.open();
    }
    protected  void close(){
        brand.close();
    }
    protected  void call(){
        brand.call();
    }
}
/*具体手机样式--旋转*/
/*这是抽象层的线*/
//通过Phone这个桥连接Brand和它的子类（XZPhone/ZDPhone）
class ZDPhone extends Phone{

    public ZDPhone(Brand brand) {
        super(brand);
        System.out.println("折叠样式手机");
    }

    @Override
    protected void open() {
        super.open();
        System.out.println("折叠样式手机");
    }

    @Override
    protected void close() {
        super.close();
        System.out.println("折叠样式手机");
    }

    @Override
    protected void call() {
        super.call();
        System.out.println("折叠样式手机");
    }
}

/*旋转手机*/
class XZPhone extends Phone{

    public XZPhone(Brand brand) {
        super(brand);
        System.out.println("旋转样式手机");
    }

    @Override
    protected void open() {
        super.open();
        System.out.println("旋转样式手机");
    }

    @Override
    protected void close() {
        super.close();
        System.out.println("旋转样式手机");
    }

    @Override
    protected void call() {
        super.call();
        System.out.println("旋转样式手机");
    }
}

//桥接模式再JDBC的源码解析
//1：jdbc的Driver接口，如果从桥接模式来看，Driver就是一个接口，下面可以有Mysql的driver,oracel的driver
    //这些就可以当作实现接口类
//2：代码分析见main方法中



//桥接模式注意事项和细节
//1：实现了抽象和实现部分的分离，从而极大地提供了系统的灵活性，让抽象部分和实现部分独立开来，这有助于系统进行分层设计，从而产生更好的结构化系统
//2：对于系统的高层部分，只需要知道抽象部分和实现部分的接口即可，其他部分由具体业务来完成
//3：桥接模式替代多层继承方案，可以减少子类的个数，降低系统的管理和维护成本
//4：桥接模式的引入增加了系统的理解和设计难度，由于聚合关联关系建立在抽象层，要求设计者针对抽象进行设计和编程
//5：桥接模式要求正确识别出系统中两个独立变化的维度，因此其适用范围有一定的局限性，需要注意这样的应用场景


//桥接模式的其他使用场景：
//1：对于那些不希望使用继承或因为多层继承导致系统类的个数急剧增加，桥接模式尤为适应
//2：常见的使用场景
    //1：jdbc驱动程序
    //2: 银行转账系统，转账分类：网上转账，柜台转账，ATM转账；转账用户类型：普通用户，银卡用户，金卡用户
    //3：消息管理：消息类型：即时消息，延时消息；消息分类：手机短信，邮件消息，qq消息...


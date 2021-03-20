package com.master.chapter003;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 依赖倒置原则优化
 * @date 2021/2/23 10:20
 */
public class DependenceInversionPrinciple2 {
    public static void main(String[] args) {
        Person1 Person1=new Person1();
        Person1.recieve(new Email1());
        Person1.recieve(new Wx1());

        //1：通过接口传递依赖
        IOpenAndClose iOpenAndClose=new OpenAndClose();
        iOpenAndClose.open(new TV());
        //2:通过构造方法传递依赖
        IOpenAndClose1 iOpenAndClose1=new OpenAndClose1(new TV1());
        iOpenAndClose1.open();
        //3：通过setterf方式传递依赖
        IOpenAndClose2 openAndClose2=new OpenAndClose2();
        openAndClose2.setITV(new TV2());
        openAndClose2.open();

    }
}

class  Person1{
    //对接口依赖：面向接口编程
    public void recieve(IReceive iReceive){
        iReceive.receive();
    }
}
//消息接收者接口
interface  IReceive{
    void receive();
}

class Email1 implements  IReceive{

    @Override
    public void receive() {
        System.out.println("收到电子邮件信息");
    }
}
class Wx1 implements  IReceive{

    @Override
    public void receive() {
        System.out.println("收到微信信息");
    }
}

//依赖关系传递的三种方式和应用案例
//1：接口传递
//2：构造方法传递
//3：setter方式传递

//1：通过接口传递实现依赖
//开关的接口
interface IOpenAndClose{
    void open(ITV itv);//抽象方法，接收接口（这里体现通过ITV接口实现依赖传递），
}
//视频接口
interface  ITV{
    public void play();//播放操作
}
//实现开关接口(各种不同的开关)
class OpenAndClose implements  IOpenAndClose{

    @Override
    public void open(ITV itv) {
        itv.play();
    }
}
//实现视频接口
class TV implements ITV{

    @Override
    public void play() {
        System.out.println("通过接口传递依赖");
    }
}


//2：通过构造方法传递依赖
interface IOpenAndClose1{//开关接口
    void open();//抽象方法
}
interface ITV1{//视频接口
    void play();
}

class OpenAndClose1 implements IOpenAndClose1{
    private ITV1 itv1;//接口属性（成员）
    public OpenAndClose1 (ITV1 itv1){//通过构造方法传递依赖
        this.itv1=itv1;
    }
    @Override
    public void open() {
        itv1.play();
    }
}

class TV1 implements ITV1{

    @Override
    public void play() {
        System.out.println("通过构造方法传递依赖");
    }
}

//3：通过setter方法传递依赖
interface IOpenAndClose2{//开关接口
    void open();
    void setITV(ITV2 itv2);
}
interface ITV2{//视频接口
    void play();
}

class OpenAndClose2 implements IOpenAndClose2{
    private ITV2 itv2;
    @Override
    public void open() {
        itv2.play();
    }

    @Override
    public void setITV(ITV2 itv2) {
        this.itv2=itv2;
    }
}

class  TV2 implements ITV2{

    @Override
    public void play() {
        System.out.println("通过setter方法传递依赖");
    }
}
//依赖倒置原则注意事项和细节
//1：低层模块尽量都要有抽象类或者接口，或者两者都有，程序稳定性更好
//2：变量的声明类型尽量是抽象类或者接口，泽阳我们的变量引用和实际对象间，就存在一个缓冲层，有利于程序扩展和优化
//3：继承时遵循里氏替换原则

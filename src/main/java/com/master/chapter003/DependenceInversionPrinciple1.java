package com.master.chapter003;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 依赖倒置原则
 * @date 2021/2/23 9:34
 */
public class DependenceInversionPrinciple1 {
    public static void main(String[] args) {
        Person person=new Person();
        person.receive(new Email());
    }
}

//完成person接受消息的功能
//方式1
//1:简单，比较容易想到
//2: 如果我们获取的对象是微信，或者手机短信，则需要新增类，同时person也需要新增相应的接受方法
//3：解决思路：引入一个新接口IReceive,表示接收者，这样person类与IReceive接口发生依赖（使用）
    //因为email.微信，信息都属于接收者范围，他们各自实现IRECEIVE接口就行，这样就负荷依赖倒置原则


class Email{
    public  String getInfo(){
        return "电子邮件信息: hello email";
    }
}
class Person{
    public void receive(Email email){
        System.out.println(email.getInfo());
    }

}

//基本介绍：
//依赖倒置原则是指
//1：高层模块不应该依赖底层模块，二者都应该依赖其抽象
//2：抽象不应该依赖细节，细节应该依赖抽象
//3：依赖倒置的中心思想是面向接口编程
//4：依赖倒置原则是基于这样的设计理念：相对于细节的多变性，抽象的东西要稳定的多，以抽象为基础
   //搭建的架构比以细节为基础搭建的架构要稳定的多，在java中，抽象指的是接口或者抽象类
   //细节就是具体的实现类
//5：使用接口或抽象类的目的是制定好规范，而不涉及任何具体的操作，把展现细节的任务交给他们的实现类去完成

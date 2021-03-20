package com.master.chapter011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 简单工厂模式
 * @date 2021/2/24 21:28
 */
public class SimpleFactory {
    public static void main(String[] args) {
        //传统方法1
        //OrderPizza orderPizza=new OrderPizza();
        //简单工厂方法2
        //SimplePizzaFactory factory=new SimplePizzaFactory();
        //OrderPizza2A orderPizza2A=new OrderPizza2A();
        //设置工厂
        //orderPizza2A.setFactory(factory);
        //orderPizza2A.orderPizza();

        //静态工厂3
        OrderPizzaA2 orderPizzaA2=new OrderPizzaA2();
        orderPizzaA2.orderPizza2();

    }
}

//看一个具体的需求
//看一个披萨的项目：要便于披萨种类的扩展，要便于维护
//1：披萨的种类很多，比如GreekPizza,CheesePizza
//2: 披萨的制作有prepare,bake,cut,box
//3: 完成披萨店订购功能

//pizza抽象类
abstract  class  Pizza{
    protected String name;
    public void setName(String name){
        this.name=name;
    }
    //准备原材料，不同的pizza原材料不同，设计成一个抽象方法
    public abstract  void prepare();
    public void bake(){
        System.out.println(name+" baking");
    }
    public void cut(){
        System.out.println(name+" cutting");
    }
    public void box(){
        System.out.println(name+" boxing");
    }

}

//具体实现类
class GreekPizza extends Pizza{

    @Override
    public void prepare() {
        setName("奶酪pizza");
        System.out.println(name+" preparing");
    }
}

class CheesePizza extends Pizza{

    @Override
    public void prepare() {
        setName("芝士pizza");
        System.out.println(name+" preparing");
    }
}

//订购pizza类
class OrderPizza{
    public OrderPizza(){
        Pizza pizza=null;
        String orderType;
        do{
            orderType=getType();
            if(orderType.equals("greek")){
                pizza=new GreekPizza();
            }else if(orderType.equals("cheese")){
                pizza=new CheesePizza();
            }else{
                break;
            }
            //输出pizza制作过程
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.bake();
        }
        while (true);
    }


    //写一个方法，可以获取客户希望订购的pizza种类
    private String  getType(){
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String type=reader.readLine();
            return type;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

//传统方式的优缺点：
//1：优点是比较好理解，简单易操作
//2：缺点是违反了设计模式的ocp原则，即对扩展开放，对修改关闭，即当我们给类增加新的功能的时候，尽量不修改代码
    //或者是尽量可能少的修改代码
//3：比如我们要新增加一个pizza种类，IceCookPizza,我们需要做如下修改：

//1：新增一个类 (对扩展开放--提供方)
class IceCookPizza extends Pizza{

    @Override
    public void prepare() {
        setName("冰可乐披萨");
        System.out.println(name+" preparing");
    }
}

//2：订购pizza类(重写)
class OrderPizza2{
    public OrderPizza2(){
        Pizza pizza=null;
        String orderType;
        do{
            orderType=getType();
            if(orderType.equals("greek")){
                pizza=new GreekPizza();
            }else if(orderType.equals("cheese")){
                pizza=new CheesePizza();
            }
            //此处为新增加的代码块 （这里违反了对修改关闭--使用方）
            else if(orderType.equals("icecook")){
                pizza=new IceCookPizza();
            }
            else{
                break;
            }
            //输出pizza制作过程
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.bake();
        }
        while (true);
    }


    //写一个方法，可以获取客户希望订购的pizza种类
    private String  getType(){
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String type=reader.readLine();
            return type;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}


//4：改进的思路分析：
//分析：修改代码可以接受，但是如果我们在其他方面也有创建pizza的代码，就意味着也需要修改，而创建pizza的代码，往往有多处
//思路：把创建pizza对象封装到一个类中，这样我们有新的pizza种类时，只需要修改该类即可，其他有创建到 pizza对象的代码
        //就不需要修改了--》简单工厂模式


//简单工厂模式
//基本介绍：
//1：简单工厂模式属于创建型模式，是工厂模式的一种，简单工厂模式是由一个工厂对象决定创建出哪一种产品类的实例，简单工厂模式
    //是工厂模式家族中最简单实用的模式
//2：简单工厂模式：定义了一个创建对象的类，由这个类来封装实例化对象的行为（代码）
//3：在软件开发中，当我们会用到大量的创建某种，某类，或者某批对象时，就会使用到工厂模式


//使用简单工厂模式对前面pizza代码改进
//1：定义一个可以实例化Pizza对象的类，封装创建对象的代码，如下：
class SimplePizzaFactory{
    //使用简单工厂模式，封装创建代码
    public Pizza createPizza(String type){
        System.out.println("------使用简单工厂模式--------");
        Pizza pizza=null;
        if(type.equals("greek")){
            pizza=new GreekPizza();
        }else if(type.equals("cheese")){
            pizza=new CheesePizza();
        }else if(type.equals("icecook")){
            pizza=new IceCookPizza();
        }else{

        }
        return pizza;
    }
}


class OrderPizza2A{
    //聚合关系
    SimplePizzaFactory factory;
    public void setFactory(SimplePizzaFactory factory){
        this.factory=factory;
    }
    //订购pizza
    public void orderPizza(){
        String type="";
        Pizza pizza=null;
        do{
            type= getType();
            //调用工厂方法
            pizza=factory.createPizza(type);
            if(pizza==null){
                System.out.println("类型错误，退出订购模式");
             break;
            }
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        }while(true);

    }

    //写一个方法，可以获取客户希望订购的pizza种类
    private String  getType(){
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String type=reader.readLine();
            return type;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}

//简单工厂模式也叫静态工厂模式：如下只需要将方法修改为静态的，然后在调用时直接使用静态方法，无需使用setFactory

class StaticFactory{
    public static Pizza createPizza(String type){
        Pizza pizza=null;
        if(type.equals("greek")){
            pizza=new GreekPizza();
        }else if(type.equals("cheese")){
            pizza=new CheesePizza();
        }else if(type.equals("icecook")){
            pizza=new IceCookPizza();
        }else{

        }
        return pizza;
    }
}

class OrderPizzaA2{

    //订购pizza
    public void orderPizza2(){
        Pizza pizza=null;
        do{
            String type=getType();
            pizza=StaticFactory.createPizza(type);
            if(pizza==null){
                System.out.println("没有该类pizza");
              break;
            }
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        }while(true);
    }

    //写一个方法，可以获取客户希望订购的pizza种类
    private String  getType(){
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza type:");
            String type=reader.readLine();
            return type;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
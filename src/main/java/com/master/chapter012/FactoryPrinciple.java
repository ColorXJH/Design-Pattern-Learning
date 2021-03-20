package com.master.chapter012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 工厂方法模式
 * @date 2021/2/26 9:18
 */
public class FactoryPrinciple {
    public static void main(String[] args) {
        //OrderPizza orderPizza=new BJOrderPizza();
        OrderPizza orderPizza=new LDOrderPizza();



    }
}

//工厂方法模式
//看一个新的需求，pizza项目新的需求：客户在点披萨时，可以点不同口味的pizza,比如北京的奶酪披萨，北京的胡椒披萨
//或者时伦敦的奶酪披萨，伦敦的胡椒披萨

//思路:
//1:使用简单工厂模式，创建不同的简单工厂类，如LDPizzaFactory，BJPizzaFactory等等，从当前案例来说也是可以的
    //但是考虑到项目的规模，以及软件的可维护性，可扩展性并不是特别好，
//2:使用工厂方法模式

//工厂方法模式介绍
    //工厂方法模式设计方案：将pizza项目的实例化功能抽象成抽象方法，在不同的口味点餐子类中具体实现
    //工厂方法模式：定义了一个创建对象的抽象方法，由子类决定要实例化的类，工厂方法模式将对象的实例化过程推迟到了子类（这句是重点）
//代码实现如下：
abstract class Pizza{
     abstract void prepare();
     protected  String name;
     public void setName(String name){
         this.name=name;
     }
     public void bake(){
         System.out.println(name+" is baking");
     }
     public void cut(){
         System.out.println(name+" is cutting");
     }
     public void box(){
         System.out.println(name+" is boxing");
     }

}
//北京奶酪披萨
class BJCheesePizza extends  Pizza{

    @Override
    void prepare() {
        setName("北京奶酪披萨");
        System.out.println(name+" is prepaing");
    }
}

//北京奶酪披萨
class BJGreekPizza extends  Pizza{

    @Override
    void prepare() {
        setName("北京芝士披萨");
        System.out.println(name+" is prepaing");
    }
}
//伦敦奶酪披萨
class LDCheesePizza extends Pizza{

    @Override
    void prepare() {
        setName("伦敦奶酪披萨");
        System.out.println(name+" is prepaing");
    }
}
//伦敦芝士披萨
class LDGreekPizza extends Pizza{

    @Override
    void prepare() {
        setName("伦敦芝士披萨");
        System.out.println(name+" is prepaing");
    }
}

//pizza订购类
abstract class OrderPizza{
    //抽象工厂方法
    abstract Pizza createPizza(String type);
    //模板方法（不要去重写这些实现的方法，可能会影响到继承它的类）
    public OrderPizza(){
        Pizza pizza=null;
        String type="";
        do{
            type=getType();
            pizza=createPizza(type);
            if(pizza==null){
                System.out.println("没有该类型的pizza");
                break;
            }
            pizza.prepare();
            pizza.bake();
            pizza.cut();;
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

//具体的各个地区pizza订购类
class BJOrderPizza extends OrderPizza{
    private Pizza pizza=null;
    @Override
    Pizza createPizza(String type) {
        if(type.equals("cheese")){
            pizza=new BJCheesePizza();
        }else if(type.equals("greek")){
            pizza=new BJGreekPizza();
        }else{
            pizza=null;
        }
        return pizza;
    }
}
class LDOrderPizza extends OrderPizza{
    private Pizza pizza=null;
    @Override
    Pizza createPizza(String type) {
        if(type.equals("cheese")){
            pizza=new LDCheesePizza();
        }else if(type.equals("cheese")){
            pizza=new LDGreekPizza();
        }else{
            pizza=null;
        }
        return pizza;
    }
}



//工厂模式在JDK Calendar应用的源码分析
//1：Calender类使用了简单工厂模式
//2：详情请见JDK源码

package com.master.chapter013;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 抽象工厂模式
 * @date 2021/2/26 20:14
 */
public class AbstractFactory {
    public static void main(String[] args) {
        OrderPizza orderPizza=new OrderPizza();
        //AbstractFactoryInterface bjfactory=new BJFactory();
        //orderPizza.setAbstractFactoryInterface(bjfactory);
        AbstractFactoryInterface ldfactory=new LDFactory();
        orderPizza.setAbstractFactoryInterface(ldfactory);
        orderPizza.order();

        //工厂模式在JDK Calendar应用的源码分析
        //静态方法
        Calendar instance=Calendar.getInstance();
        //getInstance调用了createCalendar();
        //createCalendar内部调用了简单工厂方法（根据不同的类型switch判断创建哪种类型的Calendar实例）
   }
}
//抽象工厂模式基本介绍（比工厂方法模式多了一层抽象层）
//1：抽象工厂模式：定义了一个interface用于创建相关或有依赖关系的对象簇，而无需指明具体的类
//2：抽象工厂模式可以将简单工厂模式和工厂方法模式进行整合
//3：从设计层面上来看，抽象工厂模式就是对简单工厂模式的改进（或者称为进一步抽象）
//4：将 工厂抽象成两层，AbstractFactory（抽象工厂）和具体实现的工厂子类，程序员可以根据创建
    //对象类型使用对应的工厂子类，这样将单个的简单工厂类变成了工厂簇，更利于代码的维护和扩展




class OrderPizza{
    //聚合关系
    private AbstractFactoryInterface abstractFactoryInterface;
    public void setAbstractFactoryInterface(AbstractFactoryInterface abstractFactoryInterface){
        this.abstractFactoryInterface=abstractFactoryInterface;
    }
    //订购披萨
    public void order(){
        do{
           String type=getType();
           Pizza pizza=abstractFactoryInterface.createPizza(type);
           if(pizza==null){
               System.out.println("没有该类型的pizza");
               break;
           }
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        }while(true);

    }

    private String getType(){
        String type="";
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("请输入披萨类型：");
            type=reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            type="";
        }

        return type;
    }
}


//抽象工厂模式抽象层
interface AbstractFactoryInterface{
    //让下面的工厂子类来具体实现
    Pizza createPizza(String type);
}
//具体工厂类
class BJFactory implements  AbstractFactoryInterface{

    @Override
    public Pizza createPizza(String type) {
        Pizza pizza=null;
        if(type.equals("greek")){
            pizza=new BJGreekPizza();
        }else if(type.equals("cheese")){
            pizza=new BJCheesePizza();
        }else{
            pizza=null;
        }
        return pizza;
    }
}

class LDFactory implements AbstractFactoryInterface{

    @Override
    public Pizza createPizza(String type) {
        Pizza pizza=null;
        if(type.equals("greek")){
            pizza=new LDGreekPizza();
        }else if(type.equals("cheese")){
            pizza=new LDCheesePizza();
        }else{
            pizza=null;
        }
        return pizza;
    }
}


abstract class Pizza{
    protected String name;
    public void setName(String name){
        this.name=name;
    }
    public abstract  void prepare();
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

//各种不同类型的pizza
class BJCheesePizza extends Pizza{

    @Override
    public void prepare() {
        setName("北京奶酪披萨");
        System.out.println(name+" is preparing");
    }
}
class BJGreekPizza extends Pizza{

    @Override
    public void prepare() {
        setName("北京芝士披萨");
        System.out.println(name+" is preparing");
    }
}
class LDCheesePizza extends Pizza{

    @Override
    public void prepare() {
        setName("伦敦奶酪披萨");
        System.out.println(name+" is preparing");
    }
}
class LDGreekPizza extends Pizza{

    @Override
    public void prepare() {
        setName("伦敦芝士披萨");
        System.out.println(name+" is preparing");
    }
}


//工厂模式小结
//1：工厂模式的意义：将实例化对象的代码提取出来，放到一个类中统一管理和维护，达到和主项目的依赖关系的解耦，从而提高项目的扩展性和维护性
//2：三种工厂模式：简单工厂 工厂方法 抽象工厂
//3：设计模式的依赖抽象原则
    //创建对象实例时，不要直接new 类，而是把这个new类的动作，放在在一个工厂的方法中并返回，有的书上说：变量不要直接持有具体类的引用
    //不要让类继承具体类，二十继承抽象类或者实现接口
    //不要覆盖基类中已经实现的方法

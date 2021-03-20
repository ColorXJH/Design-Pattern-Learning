package com.master.chapter032;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 策略模式
 * @date 2021/3/20 12:46
 */
public class StrategyPattern {
    public static void main(String[] args) {
        Duck duck=new WildDuck();
        duck.fly();
        duck=new ToyDuck();
        duck.fly();
        duck=new PekingDuck();
        duck.fly();

        //策略模式在jdk中的源码分析 ：compare
        Arrays arrays=null;
        //数组
        Integer[] data={9,49,3,12,45};
        //实现升序排序

        //1:comparator接口（策略接口），匿名类 对象 new Comparator<Integer>(){。。。}实现了策略接口
        //2：public int compare就是具体的处理方式
        Comparator<Integer> comparator=new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(o1>o2){
                    return 1;
                }else{
                    return -1;
                }
            }
        };
        //指定加入具体的策略（关键！！comparator为策略实现类）
        Arrays.sort(data,comparator);
        System.out.println(Arrays.toString(data));
        Arrays.sort(data,(a,b)->{
            if(a>b){
                return -1;
            }else{
                return 1;
            }
        });
        System.out.println(Arrays.toString(data));
    }
}

//编写鸭子项目，具体要求如下
    //1：有各种鸭子（比如野鸭，北京鸭，水鸭，鸭子的各种行为，比如叫，飞行等）
    //2：显示鸭子的信息


//1：传统方案：各种鸭子继承Duck抽象类，实现抽象方法
    //1：其他鸭子都继承duck类，所以fly让左右子类都会飞了，这是不正确的
    //2：上面说的1的问题，其实是继承带来的问题：对类的局部改动，尤其超类的局部改动，会影响其他部分，会有溢出效应
    //3：为了改进1问题，我们可以通过覆盖fly方法来解决，==》覆盖解决
    //4：问题又来了，如果我们有一个玩具鸭子，这样就意味着需要玩具鸭子去覆盖duck的所有实现方法
        //==》解决思路：策略模式


//策略模式工作原理
    //1：策略模式中，定义算法簇，分别封装起来，让他们之间可以互相替换，此模式让算法的变化独立于使用算法的客户
    //2：这算法体现了几个设计原则，第一，把变化的代码从不变的代码中分离出来，第二，针对接口编程而不是具体类（定义了策略接口）
        //第三，多用组合/聚合，少用继承（客户通过组合方式使用策略模式）

//从策略模式类图可以看到：客户context有成员变量strategy,或者其他的策略接口，至于需要使用到哪个策略
//可以在构造器中指定

//代码实现鸭子项目（策略模式）

/*策略接口--使用算法的对象*/
interface  FlyBehavior{
    void fly();//子类具体实现
}
/*具体策略*/
class GoodFlyBehavior implements FlyBehavior{

    @Override
    public void fly() {
        System.out.println("---飞翔技术高超---");
    }
}

class BadFlyBehavior implements FlyBehavior{

    @Override
    public void fly() {
        System.out.println("---飞翔技术不行---");
    }
}

class NoFlyBehavior implements FlyBehavior{

    @Override
    public void fly() {
            System.out.println("---不会飞行---");
    }
}


/*鸭子抽象类--算法提供者*/
abstract class Duck{
    //属性，策略接口
    FlyBehavior flyBehavior;
    public FlyBehavior getFlyBehavior() {
        return flyBehavior;
    }
    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }
    public Duck(){}

    //显示鸭子信息
    public abstract void display();

    public void quack(){
        System.out.println("---鸭子嘎嘎叫---");
    }
    public void swim(){
        System.out.println("---鸭子会游泳---");
    }
    public void fly(){
        if(flyBehavior!=null){
            flyBehavior.fly();
        }
    }

}

/*具体鸭子*/
class WildDuck extends Duck{
    //构造器传入flyBehavior的对象
    public WildDuck(){
        flyBehavior=new GoodFlyBehavior();
    }
    
    
    @Override
    public void display() {
        System.out.println("野鸭");
    }
}

class PekingDuck extends Duck{
    public PekingDuck(){
        flyBehavior=new NoFlyBehavior();
    }
    @Override
    public void display() {
        System.out.println("北京鸭");
    }
}

class ToyDuck extends Duck{
    public ToyDuck(){
        flyBehavior=new BadFlyBehavior();
    }
    @Override
    public void display() {
        System.out.println("玩具鸭");
    }
}

//策略模式在jdk中的源码分析 ：Arrays
    //1:jdk的Arrays的Comparator就使用了策略模式
    //2：代码分析见main


//策略模式的源码和细节
    //1：策略模式的关键是：分析项目中的变化和不变化的部分
    //2：策略模式的核心思想是：多用组合/聚合 少用继承；用行为类组合，而不是行为类的继承，更有弹性
    //3：体现了“对修改关闭，对扩展开放”的原则，客户端增加行为不用修改原有代码，只要添加一种策略（或者行为）即可，
        //避免了使用多重转移语句（if-else if-else）
    //4：提供了可以独立继承关系的办法，策略模式将算法封装在独立的strategy类中，使得你可以独立于其Context改变他，
        //使他易于切换，易于理解，易于扩展
    //5：需要注意的是：每添加一个策略，就要增加一个类，当策略过就会导致类数目过大

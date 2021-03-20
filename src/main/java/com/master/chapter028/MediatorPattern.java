package com.master.chapter028;

import ch.qos.logback.core.net.SyslogOutputStream;
import sun.security.util.ManifestEntryVerifier;

import java.util.HashMap;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 中介者模式
 * @date 2021/3/15 22:36
 */
public class MediatorPattern {
    public static void main(String[] args) {
        //1:创建一个中介者对象
        Mediator mediator=new ConcreteMediator();
        //2:创建闹钟
        Alarm alarm=new Alarm(mediator,"alarm");
        CoffeeMachine coffeeMachine=new CoffeeMachine(mediator,"coffee");
        TV tv=new TV(mediator,"tv");
        alarm.sendAlarm(0);
        alarm.sendAlarm(1);
    }
}

//智能家庭管理问题
    //1：智能家庭包括各种设备，闹钟，咖啡机，电视机，窗帘等
    //2：主人要看电视时，各个设备可以协同工作，自动完成看电视的准备工作，比如流程为：
        //闹钟响起-》咖啡机开始做咖啡-》窗帘自动落下-》电视机开始播放

//传统方式问题分析：
    //1：当各电器对象有多种状态改变时，相互之间的调用关系会比较复杂
    //2：各个电器对象彼此联系，你中有我我中有你，不利于松耦合
    //3：各个电器对象之间所传递的消息（参数），容易混乱
    //4：当系统增加一个新的电器对象时，或者执行流程改变时，代码的可维护性，扩展性都不理想--》考虑中介者模式



//中介者模式（mediator-pattern）基本介绍
    //1：用一个中介对象来来封装一系列的对象交互，中介者使各个对象不需要显式的相互引用，从而使其耦合松散
            //并且可以独立的改变他们之间的交互
    //2：中介者模式属于行为型模式，使代码易于维护
    //3：比如MVC模式，C（Controller控制器）是M(Model模型)和V(View试图)的中介者，在前后端交互中起到了中间人的作用

//类图角色和职责
    //1：mediator:抽象中介者，定义了同事对象到中介者的接口
    //2：colleage:抽象同事类
    //3：concreteMediator:具体的中介者对象，实现抽象方法，他需要知道所有的具体的同事类（即以一个集合来管理）
            //并接收某个同事对象消息，完成相应任务
    //4：concreteColleage:具体的同事类，会有很多，每个同事只知道自己的行为，而不了解其他同事类的行为（方法）
            //但是他们都含有（依赖）中介者对象


//智能家庭操作流程
    //1：创建一个concreteMediator
    //2: 创建各个同事类对象，alarm,coffeeMachine,tv
    //3：在创建同事类对象的时候就直接通过构造器，加入到concreteMediator的ColleageMap集合中去
    //4：同事类的对象可以调用sendMessage(),最终会去调用ConcreteMediator的getMessage()方法
    //5：getMessage()会根据接收到的同事对象发出的消息协调调用其他的同事对象，完成任务
    //6：可以看到concreteMediator中的getMessage()是核心方法，同事之间是不会直接调用的，

//代码如下
/*中介者抽象类*/
abstract class Mediator{
    //将中介者对象加入到集合
    abstract void register(String name,Colleage colleage);
    //接收消息，具体的同事对象发出
    abstract void getMessage(int stateChange,String name);
    abstract void sendMessage();
}

/*具体的中介者*/
class ConcreteMediator extends Mediator{
    /*维护同事接口集合*/
    private HashMap<String,Colleage>colleageHashMap;
    private HashMap<String,String>interMap;
    public ConcreteMediator(){
        colleageHashMap=new HashMap<>();
        interMap=new HashMap<>();
    }


    @Override
    void register(String name, Colleage colleage) {
        colleageHashMap.put(name,colleage);
        interMap.put(name,name);
    }

    /*具体的中介者的核心方法*/
    //1：根据得到的消息，完成对应的任务，
    //2：中介者在这个方法中，协调各个具体的同事对象，完成任务
    @Override
    void getMessage(int stateChange, String name) {
        if(colleageHashMap.get(name) instanceof Alarm){
            if(stateChange==0){
                ((CoffeeMachine) colleageHashMap.get("coffee")).startCoffee();
                ((TV)colleageHashMap.get("tv")).startTV();
            }else if(stateChange==1){
                ((TV)colleageHashMap.get("tv")).stopTV();
            }
        }else if(colleageHashMap.get(name) instanceof TV){
            //...
        }else if(colleageHashMap.get(name) instanceof CoffeeMachine){
            //...
        }else{
            //...
        }
    }

    @Override
    void sendMessage() {

    }
}


/*同事抽象类*/
abstract class Colleage{
    private Mediator mediator;
    private String name;
    public Colleage(Mediator mediator,String name){
        this.mediator=mediator;
        this.name=name;
    }

    public Mediator getMediator(){
        return this.mediator;
    }
    public String getName(){
        return this.name;
    }
    public abstract  void sendMessage(int stateChange);
}

/*具体的同事类*/
class Alarm extends Colleage{

    public Alarm(Mediator mediator, String name) {
        super(mediator, name);
        /*在创建具体对象的同事时，将自己放入中介中的集合中维护*/
        mediator.register(name,this);

    }
    public void sendAlarm(int stateChange){
        sendMessage(stateChange);
    }
    @Override
    public void sendMessage(int stateChange) {
        System.out.println("--alarm--sendmessage--");
        this.getMediator().getMessage(stateChange,this.getName());
    }
}


class Curtains extends  Colleage{

    public Curtains(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name,this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange,this.getName());
    }

    public void upCurtains(){
        System.out.println("--------关闭窗帘---------");
    }
}


class TV extends Colleage{

    public TV(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name,this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange,this.getName());
    }

    public void startTV(){
        System.out.println("-------i am statt tv-------");
    }

    public void stopTV(){
        System.out.println("-------i am stop tv-------");
    }
}


class CoffeeMachine extends Colleage{


    public CoffeeMachine(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name,this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange,this.getName());
    }

    public void startCoffee(){
        System.out.println("------------现在开始煮咖啡------------");
    }
}


//中介者模式的注意事项和使用细节
    //1：多个类互相耦合，会形成网状结构，使用终结者模式将网状结构分离为星型结构，进行解耦
    //2：减少类间依赖，降低了耦合，负荷迪米特法则
    //3：中介者承担了较多的责任，一旦中介者出现了问题，整个系统就会受到影响
    //4：如果设计不当，中介者对象本身变得过于复杂，这点在实际运用中需要注意

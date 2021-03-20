package com.master.chapter025;

import org.omg.PortableInterceptor.ACTIVE;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 访问者模式
 * @date 2021/3/14 20:11
 */
public class VisitorPattern {
    public static void main(String[] args) {
        //创建ObjectStruct
        ObjectStruct objectStruct=new ObjectStruct();
        objectStruct.attach(new Man());
        objectStruct.attach(new Woman());
        //成功
        Success success=new Success();
        objectStruct.dispaly(success);
        //失败
        Fail fail=new Fail();
        objectStruct.dispaly(fail);

    }
}


//测评系统需求
    //1：将观众分为男人和女人，对歌手进行点评，当看完某个歌手表演后，得到他们对该歌手不同的评价（评价有不同的种类：成功/失败）
    //传统方案问题分析：如果系统小还好，随着增加新功能，对代码改动较大，违反了ocp原则，并且扩展性也不好
    //2：引出我们使用的新的设置模式：访问者模式


//访问者模式：基本介绍
    //1：封装一些作用于某种数据结构的各元素的操作，它可以在不改变数据结构的前提下定义作用于这些元素的新的操作
    //2：主要将数据结构与数据操作分离，解决了数据结构和操作耦合性的问题
    //3：基本工作原理是：在被访问的类里面加一个对外提供接待访问者的接口
    //4：主要应用场景：需要对一个对象结构中的对象进行很多不同的操作（这些操作彼此没有关联），同时需要避免让这些操作
            //”污染“z这些对象的类，可以选用访问者模式来解决

    //visitor:抽象访问者，为该对象结构中的ConcreteElement的每一个类声明visit操作
    //ConcreteVisitor:是一个具体访问者，实现每个有visitor声明的操作，是每个操作实现的部分
    //ObjectStructure: 能枚举它的元素，它能提供一个高层的接口，用来允许访问者访问元素
    //element:定义了一个accept方法，可以接受一个访问者对象
    //ConcreteElement：具体元素，实现了accept方法


//代码如下
/*访问者接口*/
abstract  class Action{
    //得到男性的测评结果
    abstract void getManResult(Man man);
    //得到女性的测评
    abstract void getWomanResult(Woman woman);
}
/*具体访问者*/
class Success extends  Action{

    @Override
    void getManResult(Man man) {
        System.out.println("男人给的评价是很成功");
    }

    @Override
    void getWomanResult(Woman woman) {
        System.out.println("女人给的评价是很成功");
    }
}

class Fail extends Action{

    @Override
    void getManResult(Man man) {
        System.out.println("男人给的评价是很失败");
    }

    @Override
    void getWomanResult(Woman woman) {
        System.out.println("女人给的评价是很失败");
    }
}

/*不同的人*/
abstract class Person{
    /*提供一个方法让访问者可以访问*/
    abstract  void accept(Action action);
}

/*这里我们使用到了双分派，即首先在客户端程序中，将具体的状态作为参数传递到了Man中（第一次分派），然后在该类中调用了作为参数的具体方法getManResult，同时将自己（this）作为参数传给了Action(第二次分派)*/
class Man extends Person{

    @Override
    void accept(Action action) {
        action.getManResult(this);
    }
}
class Woman extends Person{

    @Override
    void accept(Action action) {
        action.getWomanResult(this);
    }
}


class ObjectStruct{
    /*维护了一个集合*/
    private List<Person>list=new LinkedList<>();

    //增加
    public void attach(Person person){
        list.add(person);
    }
    //移除
    public void dettach(Person person){
        list.remove(person);
    }
    //显示测评情况
    public void dispaly(Action action){
            for(Person person:list){
                person.accept(action);
            }
    }

}

//小结
    //双分派：所谓双分派是指不管类如何变化，我们都能找到期望的方法运行。双分派意味着得到执行的操作取决于请求的种类和两个接收者的类型
    //以上述实例为例，假设我们要添加一个NotBat的状态类，考察Man类和Woman类的反应，由于使用了双分派，只需要增加Action子类即可在客户端调用即可，
        //不需要改动任何其他类的代码




//访问者模式注意事项和细节
    //优点
        //1：访问者模式符合单一职责原则，让程序具有优秀的扩展性，灵活性非常高，
        //2：访问者模式可以对功能经行统一，可以做报表，ui,拦截器和过滤器，适用于数据结构相对稳定的系统

    //缺点
        //1：具体元素对访问者公布细节，也就是说访问者关注了其他类的内部细节，违背了迪米特法则
        //2： 违背了依赖倒置原则，访问者依赖的是具体元素，不是抽象元素
   //因此，如果一个系统有比较稳定的数据结构，又有经常变化的功能需求，那么访问者模式比较适合



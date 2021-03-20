package com.master.chapter002;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 接口隔离原则
 * @date 2021/2/22 19:27
 */
public class InterfaceSegretion2 {
    public static void main(String[] args) {
        AA aa=new AA();
        aa.depend1(new BB());//AA类通过接口去依赖BB类
        aa.depend2(new BB());
        CC cc=new CC();
        cc.depend1(new DD());//CC类通过接口去依赖DD类
        cc.depend2(new DD());
    }

}

interface  interface11{
    public void operation1();
}
interface  interface12{
    public void operation2();
    public void operation3();
}
interface  interface13{
    public void operation4();
    public void operation5();
}

class BB implements interface11,interface12{

    @Override
    public void operation1() {
        System.out.println("BB 实现了operation1");
    }

    @Override
    public void operation2() {
        System.out.println("BB 实现了operation2");
    }

    @Override
    public void operation3() {
        System.out.println("BB 实现了operation3");
    }
}

class DD implements interface11,interface13{

    @Override
    public void operation1() {
        System.out.println("DD 实现了operation1");
    }

    @Override
    public void operation4() {
        System.out.println("DD 实现了operation4");
    }

    @Override
    public void operation5() {
        System.out.println("DD 实现了operation5");
    }
}
//AA 通过interface11 interface12依赖（使用）BB类
class AA{
    public void depend1(interface11 interface11){
        interface11.operation1();

    }
    public void depend2(interface12 interface12){
        interface12.operation2();
        interface12.operation3();

    }


}
//CC 通过interface11 interface13依赖（使用）DD类
class CC{
    public void depend1(interface11 interface11){
        interface11.operation1();

    }
    public void depend2(interface13 interface13){
        interface13.operation4();
        interface13.operation5();

    }
}

//接口隔离原则：
//基本介绍：客户端不应该依赖它不需要的接口，即一个类对另一个类的依赖应该建立在最小的接口上
//类A通过interface1依赖类B,类C通过interface1依赖类D,如果接口interface1对于类A和类C来说
//不是最小的接口，那么类B和类D必须去实现他们不需要的方法
//解决方法：按接口隔离原则应当这样处理：
//将接口interface1拆分为几个独立的接口，类A和类C分别与他们需要的接口建立依赖（使用）关系
//也就是采用接口隔离原则

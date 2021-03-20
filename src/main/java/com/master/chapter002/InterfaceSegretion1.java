package com.master.chapter002;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 接口隔离原则
 * @date 2021/2/22 16:26
 */
public class InterfaceSegretion1 {
    public static void main(String[] args) {

    }

}

//接口
interface interface1{
    void operation1();
    void operation2();
    void operation3();
    void operation4();
    void operation5();
}


class B implements  interface1{

    @Override
    public void operation1() {
        System.out.println("B 中实现operation1");
    }

    @Override
    public void operation2() {
        System.out.println("B 中实现operation2");
    }

    @Override
    public void operation3() {
        System.out.println("B 中实现operation3");
    }

    @Override
    public void operation4() {
        System.out.println("B 中实现operation4");
    }

    @Override
    public void operation5() {
        System.out.println("B 中实现operation5");
    }
}


class D implements interface1{

    @Override
    public void operation1() {
        System.out.println("D 中实现operation1");
    }

    @Override
    public void operation2() {
        System.out.println("D 中实现operation2");
    }

    @Override
    public void operation3() {
        System.out.println("D 中实现operation3");
    }

    @Override
    public void operation4() {
        System.out.println("D 中实现operation4");
    }

    @Override
    public void operation5() {
        System.out.println("D 中实现operation5");
    }
}
//A 通过接口interface依赖（使用） B ，但是只会用到1 2 3 方法
class A {
    public void depend1(interface1 interface1){
        interface1.operation1();
    }
    public void depend2(interface1 interface1){
        interface1.operation2();
    }
    public void depend3(interface1 interface1){
        interface1.operation3();
    }
}


//C 通过接口interface依赖 （使用） D,但是只会用到1 3 4方法
class C {
    public void depend1(interface1 interface1){
        interface1.operation1();
    }
    public void depend3(interface1 interface1){
        interface1.operation3();
    }
    public void depend4(interface1 interface1){
        interface1.operation4();
    }
}

//存在问题
//类 A 通过接口interface1 依赖（使用）B,类 C通过接口interface1 依赖（使用）D,如果接口interface1
//对于A,C来说不是最小接口，那么B,D必须去实现他们不需要的方法

//solution:
//将接口差分为多个独立的最小接口，类A C分别实现他们需要的独立的最小接口

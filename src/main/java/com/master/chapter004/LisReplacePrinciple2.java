package com.master.chapter004;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 里氏替换原则优化
 * @date 2021/2/23 13:30
 */
public class LisReplacePrinciple2 {
    public static void main(String[] args) {
        A1 a1=new A1();
        System.out.println("11-3= "+a1.func1(11,3));
        System.out.println("1-8= "+a1.func1(1,8));
        System.out.println("----------------------");

        B1 b1=new B1();
        //因为B1不再继承A1,因此调用者不会再func1求减法,采用func3
        //使用组合仍然可以调用到A的方法
        System.out.println("11-3= "+b1.func3(11,3));

    }
}

class  Base{
    //把更加基础的方法和成员写到base
}

class A1 extends Base{
     public int func1(int a,int b){
         return a-b;
     }
}

class  B1 extends  Base{
    //如果B需要使用A类的方法，使用组合关系
    private A a=new A();

    public int func1(int a,int b){
        return a+b;
    }

    public int func2(int a,int b){
        return func1(a,b)+9;
    }

    //我们仍然想使用A的方法
    public int func3(int a,int b){
        return this.a.func1(a,b);
    }
}

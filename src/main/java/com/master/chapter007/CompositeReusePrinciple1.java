package com.master.chapter007;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 合成复用原则
 * @date 2021/2/23 21:11
 */
public class CompositeReusePrinciple1 {
    public static void main(String[] args) {

    }
}

//基本介绍
//原则是尽量使用合成/聚合的方式，而不是使用继承
//依赖，聚合，组合 替代继承
class A{
    //A 依赖 (使用)B   dependency
    public  void dependency(B b) {
    }
    //A 聚合 B  aggregation
    private B b;
    public void setB(B b){
        this.b=b;
    }
    //A 组合 B   compose
    private B bs=new B();
}
class B{
    public  void operation1() {
    }
    public  void operation2() {
    }
    public  void operation3() {
    }

}

//设计原则的核心思想
//1: 找出应用中可能需要变化之处，把他们独立出来，不要和那些不需要变化的代码混合在一起
//2：准对接口编程，而不是针对实现编程
//3：为了交互对象之间的松耦合设计而努力

//单一职责 接口隔离 依赖倒置 里氏替换 开闭原则 迪米特法则 合成复用



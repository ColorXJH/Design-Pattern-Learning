package com.master.chapter001;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 方案三
 * @date 2021/2/22 15:28
 */
public class SingleResponsibility3 {

    public static void main(String[] args) {
        Vehicle2 vehicle2=new Vehicle2();
        vehicle2.run("摩托");
        vehicle2.runAir("飞机");
        vehicle2.runWater("轮船");
    }

}
//方式三分析
//这种修改方法没有对原来的类进行大的修改，只是增加了方法
//这里虽然没有在类这个级别上遵循单一职责原则，但是在方法级别上仍然遵守
//单一职责：各行其职
class Vehicle2{
    public void run(String vehicle){
        System.out.println(vehicle+" 在公路上运行");
    }
    public void runAir(String vehicle){
        System.out.println(vehicle+" 在天空上运行");
    }
    public void runWater(String vehicle){
        System.out.println(vehicle+" 在水面上运行");
    }

}

//注意事项和细节
//1：降低类的复杂程度，达到一个类只负责一项职责
//2：提高类的可读性，可维护性
//3：降低变更引起的风险
//4：通常情况下，我们应当遵守单一职责原则，只有逻辑足够简单，才可以在代码级别违反单一职责原则
    //只有类中的方法足够少，可以在方法级别保持单一职责原则
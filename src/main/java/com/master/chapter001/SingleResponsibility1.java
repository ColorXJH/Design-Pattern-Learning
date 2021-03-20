package com.master.chapter001;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 单一职责原则
 * @date 2021/2/22 15:09
 */
public class SingleResponsibility1 {

    public static void main(String[] args) {
        Vehicle vehicle=new Vehicle();
        vehicle.run("摩托车");
        vehicle.run("汽车");
        vehicle.run("飞机");
    }


}

//交通工具类
//方式1，在run 方法中，违反了单一职责原则
//解决方案：可以新建多个类拆分职责
class Vehicle{
    public void run(String vehicle){
        System.out.println(vehicle+" 在公路上运行");
    }
}

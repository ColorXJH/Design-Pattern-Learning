package com.master.chapter001;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 方案2
 * @date 2021/2/22 15:14
 */
public class SingleResponsibility2 {

    public static void main(String[] args) {
        RoadVehicle roadVehicle=new RoadVehicle();
        AirVehicle airVehicle=new AirVehicle();
        WaterVehicle waterVehicle=new WaterVehicle();
        roadVehicle.run("摩托车");
        airVehicle.run("飞机");
        waterVehicle.run("轮船");
    }
}
//方案2的分析：
//1：遵循单一职责原则
//2：但是这样做代码改动很大，代码比较膨胀
//3：改进：直接修改vehicle,这样改动的代码会比较少=》方案三

//路上跑的交通工具
class RoadVehicle{
    public void run(String vehicle){
        System.out.println(vehicle+" 在公路上运行");
    }
}

//天空跑的交通工具
class AirVehicle{
    public void run(String vehicle){
        System.out.println(vehicle+" 在天空上飞行");
    }
}

//水上跑的交通工具
class WaterVehicle{
    public void run(String vehicle){
        System.out.println(vehicle+" 在水上运行");
    }
}
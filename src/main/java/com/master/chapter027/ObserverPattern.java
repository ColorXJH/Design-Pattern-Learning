package com.master.chapter027;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 观察者模式
 * @date 2021/3/15 18:36
 */
public class ObserverPattern {
    public static void main(String[] args) {
        //普通方法
        CurrentCondition condition=new CurrentCondition();
        WeathrDate date=new WeathrDate(condition);
        date.setData(12.3F,334.5F,34F);
        System.out.println("------------------------------------");
        //观察者模式
        WeatherDate dates=new WeatherDate();
        dates.reigsterObserver(new CurrentConditions());
        dates.reigsterObserver(new Baidu());
        dates.setData(22,34,56);


        //观察者模式在jdk应用的源码分析：Observable
        Observable observable=null;

    }
}


//天气预报项目需求
//1：气象站可以将每天测量到的温度，湿度，气压等等以公告的形式发布出去（比如发布到自己的网站或者第三方）
//2：需要设计开放api,便于其他第三方，也能接入气象站获得数据
//3: 提供温度，气压和湿度的接口
//4：测量数据更新时，要能实时的通知给第三方



//1：普通方案:WeatherDate类
    //getTempetature()
    //getHumidity();
    //getPressure();
    //dataChange()
    //getXXX方法，可以让第三方接入，并得到相关信息
    //当数据有更新时，气象站通过dataChange()去更新数据，当第三方再次获取时，就能得到最新数据，当然也可以推送
    //外部CurrentCondition类(当前的天气情况)---可以理解成是我们气象局的网站，//推送（weatherDATE维护它管理的网站）
        //update()
        //display()

//普通方案--代码实现（推送）

/*类比网站*/
/*显示当前天气情况*/
class CurrentCondition{
    //气温/气压/湿度
    private float temperature;
    private float pressure;
    private float humidity;

    /*更新*/
    public void update(float temperature,float pressure,float humidity){
        this.temperature=temperature;
        this.pressure=pressure;
        this.humidity=humidity;
        display();
    }
    /*展示*/
    public void display(){
        System.out.println("today temperature is "+temperature);
        System.out.println("today pressure is "+pressure);
        System.out.println("today humidity is "+humidity);
    }
}

/*核心类
* 1；包含最新的天气情况信息
* 2：包含了一个CurrentCondition对象
* 3：当数据有更新时，就主动调用CurrentCondition的update方法，这样接入方就看到了最新的信息
* */
class WeathrDate{
    //气温/气压/湿度
    private float temperature;
    private float pressure;
    private float humidity;
    private CurrentCondition condition;
    public WeathrDate(CurrentCondition condition){
        this.condition=condition;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }


    public void setData(float temperature,float pressure,float humidity){
        this.temperature=temperature;
        this.pressure=pressure;
        this.humidity=humidity;
        dataChange();
    }


    public void dataChange(){
        condition.update(getTemperature(),getPressure(),getHumidity());
    }
}

//问题分析
    //1：其他第三方介入气象站获取数据的问题
    //2：无法在运行时动态的添加第三方
        //在weatherDate中，当增加一个第三方，都需要创建一个对应的di'san'fang公告板对象，并且加入到dataChange
        //不利于维护，也不是动态加入---》观察者模式引入


//观察者模式原理：
    //1：观察者模式类似定牛奶业务：
            //1：奶站/气象局：Subject
            //2:用户/第三方网站：Observer
    //2:Subject：注册登记，移除和通知
            //1：registerObserver()注册
            //2：removeObserver()移除
            //3：notifyObservers()通知所有的注册用户，根据不同需求，可以是更新数据，让用户来取
                    //也可能是实时推送，看具体需求来定
    //3：Observer:接收输入
    //观察者模式：对象之间多对一依赖的一种设计方案，被依赖的对象成为Subject,依赖的对象称为Observer
        //subject通知observer变化，比如这里的奶站是subject,是1的一方，用户observer是多的一方


//观察者模式实现，代码如下：
/*主体接口*/
interface Subject{
    void reigsterObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();

}
/*观察者接口*/
interface Observer{
    void update(float tempetature,float pressuer,float humidity);
}

class CurrentConditions implements Observer{
    //气温/气压/湿度
    private float temperature;
    private float pressure;
    private float humidity;

    /*更新*/
    @Override
    public void update(float temperature,float pressure,float humidity){
        this.temperature=temperature;
        this.pressure=pressure;
        this.humidity=humidity;
        display();
    }
    /*展示*/
    public void display(){
        System.out.println("today temperature is "+temperature);
        System.out.println("today pressure is "+pressure);
        System.out.println("today humidity is "+humidity);
    }

}

class Baidu implements Observer{
    //气温/气压/湿度
    private float temperature;
    private float pressure;
    private float humidity;

    /*更新*/
    @Override
    public void update(float temperature,float pressure,float humidity){
        this.temperature=temperature;
        this.pressure=pressure;
        this.humidity=humidity;
        display();
    }
    /*展示*/
    public void display(){
        System.out.println("Baidu today temperature is "+temperature);
        System.out.println("Baidu today pressure is "+pressure);
        System.out.println("Baidu today humidity is "+humidity);
    }

}

/*包含最新的天气情况
* 含有观察者集合，使用arraylist
* 数据更新时，就主动调用arraylist,通知所有接入方，
* */
class WeatherDate implements Subject{
    //气温/气压/湿度
    private float temperature;
    private float pressure;
    private float humidity;
    /*观察者集合*/
    private List<Observer>observers;
    public WeatherDate(){
        observers=new ArrayList<>();
    }

    /*注册*/
    @Override
    public void reigsterObserver(Observer o) {
        observers.add(o);
    }
    /*移除*/
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    /*通知所有*/
    @Override
    public void notifyObservers() {
        observers.stream().forEach(i->{
            i.update(this.temperature,this.pressure,this.humidity);
        });
    }

    /*setter/getter*/
    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    /*设置并推送*/
    public void setData(float temperature, float pressure, float humidity){
        this.temperature=temperature;
        this.pressure=pressure;
        this.humidity=humidity;
        dataChange();
    }


    public void dataChange(){
        notifyObservers();
    }
}

//观察者模式的好处
    //1：观察者模式设计后，会以集合的方式来管理用户（observer）,包括注册，移除和通知
    //2：这样，我们增加观察者（这里可以理解为一个新的公告板），就不会去修改核心类WeatherDate,遵守ocp原则


//观察者模式在jdk应用的源码分析：Observable
    //1:Observable的作用和地位等价于Subject,它是一个类，不是接口，实现了核心方法，即管理Observer
    //2:Observer的作用等价于Baidu，有update方法






package com.master.chapter020;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.apache.ibatis.session.Configuration;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 外观模式
 * @date 2021/3/9 16:59
 */
public class FacadePattern {
    public static void main(String[] args) {
        HomeFacade homeFacade=new HomeFacade();
        homeFacade.ready();
        homeFacade.paly();
        homeFacade.pause();
        homeFacade.end();
        //外观模式在mybatis中的源码分析
        Configuration configuration=new Configuration();
        //Configuration内部组合了很多子系统工厂，通过调用newMetaObject()方法，返回MetaObject对象
        //调用端不用关系各个内部子系统工厂，只和Configuration类打交道
    }
}


//影院管理项目
//组件一个家庭影院
//DVD播放器，投影仪，自动屏幕，环绕立体声，爆米花机，要求完成使用家庭影院功能，其过程为：
    //1：直接使用遥控器：统筹各个设备开关
    //2：开爆米花机
    //3：放下屏幕
    //4：开投影仪
    //5：开音响
    //6：开dvd,选dvd
    //7: 去拿爆米花
    //8：调暗灯光
    //9：播放
    //10：观影结束后，关闭各种设备

//传统方式解决：
//创建各个类各个设备，包含开关播放方法等各个方法
//传统方式问题分析
    //1：在client端的main方法中，创建各个子系统的对象，并直接去调用各个子系统（对象）的相关方法，会造成调用过程混乱，没有清晰的过程
    //2：不利于在ClientTest中，去维护对子系统的操作
    //3：解决思路：定义一个高层接口，给子系统中的一组接口提供一个一致的界面（比如在高层接口中提供四个方法 ready,play,pause,end）
        //用来访问子系统中的一群接口
    //4：也就是说：通过定义一个一致的接口（界面类），用以屏蔽内部子系统的细节，使得调用端只需要跟这个接口发生调用
        //而无需关心这个子系统的内部细节==》外观模式



//外观模式--基本介绍
//1：外观模式（facade）,也叫过程模式：外观模式为子系统中的一组提供一个一致的界面，此模式定义了一个高层接口，这个接口使得这一子系统更加容易使用
//2：外观模式通过定义一个一致的接口，用以屏蔽内部子系统的细节，使得调用端只需跟这个接口发生调用，而无需关心这个子系统的内部细节

    //1：外观类 facade:为调用端提供统一的调用接口，外观类知道哪些子系统，负责处理请求，从而将调用端的请求代理给适当的子系统对象
    //2：调用者 client:外观接口的调用者
    //3：子系统的集合：指模块或子系统，处理facade对象指派的任务，他是功能的实际提供者



//外观模式解决影院管理
//1：外观模式可以理解为转换一群接口，客户只要调用一个接口，而不是调用多个接口才能达到目的，比如在pc上安装软件的时候经常有
    //一键安装选项（省去选择安装目录，安装的组件等等），还有就是手机的重启功能（关机和启动合为一个操作）
//2：外观模式就是解决多个复杂接口带来的使用困难，起到简化用户操作的目的

//代码如下：
/*DVD*/
class DvdPlayer{
    /*单例模式*/
    private static volatile  DvdPlayer instance=null;

    private DvdPlayer(){}

    public static DvdPlayer  getInstance(){
        if(instance==null){
            synchronized (DvdPlayer.class){
                if(instance==null){
                    instance=new DvdPlayer();
                }
            }
        }
        return instance;
    }

    /*打开-关闭-播放-暂停*/
    public void on(){
        System.out.println("DVD 打开了");
    }

    public void off(){
        System.out.println("DVD 关闭了");
    }

    public void play(){
        System.out.println("DVD 播放了");
    }

    public void pause(){
        System.out.println("DVD 暂停了");
    }

}

/*爆米花机器*/
class Popcorn{
    private static volatile Popcorn instance=null;
    private Popcorn(){}
    public static Popcorn getInstance(){
        if(instance==null){
            synchronized (Popcorn.class){
                if(instance==null){
                    instance=new Popcorn();
                }
            }
        }
        return instance;
    }

    /*打开-关闭-出爆米花-暂停*/
    public void on(){
        System.out.println("爆米花机器 打开了");
    }

    public void off(){
        System.out.println("爆米花机器 关闭了");
    }

    public void pop(){
        System.out.println("爆米花机器 正在出爆米花");
    }

    public void pause(){
        System.out.println("爆米花机器 暂停了");
    }

}

/*投影仪*/
class Projector{
    private volatile static  Projector instance=null;
    private Projector(){}
    public static Projector getInstance(){
        if(instance==null){
            synchronized (Projector.class){
                if(instance==null){
                    instance=new Projector();
                }
            }
        }
        return instance;
    }

    /*打开-关闭-聚焦-暂停*/
    public void on(){
        System.out.println("投影仪 打开了");
    }

    public void off(){
        System.out.println("投影仪 关闭了");
    }

    public void focus(){
        System.out.println("投影仪 聚焦了");
    }

    public void pause(){
        System.out.println("投影仪 暂停了");
    }

}

/*屏幕*/
class Screen{
    private static volatile Screen instance=null;
    private Screen(){}
    public static Screen getInstance(){
        if(instance==null){
            synchronized (Screen.class){
                if(instance==null){
                    instance=new Screen();
                }
            }
        }
        return instance;
    }

    /*上升-下降*/
    public void up(){
        System.out.println("屏幕 上升了");
    }

    public void down(){
        System.out.println("屏幕 下降了");
    }


}
/*立体声*/
class Stereo{
    private static volatile Stereo instance=null;
    private Stereo(){}
    public static Stereo getInstance(){
        if(instance==null){
            synchronized (Stereo.class){
                if(instance==null){
                    instance=new Stereo();
                }
            }
        }
        return instance;
    }

    /*打开-关闭-调大-调小*/
    public void on(){
        System.out.println("立体声 打开了");
    }

    public void off(){
        System.out.println("立体声 关闭了");
    }

    public void up(){
        System.out.println("立体声 调大了");
    }

    public void down(){
        System.out.println("立体声 调小了");
    }

}

/*灯光*/
class Light{
    private static volatile Light instance=null;
    private Light(){}
    public static Light getInstance(){
        if(instance==null){
            synchronized (Light.class){
                if(instance==null){
                    instance=new Light();
                }
            }
        }
        return instance;
    }


    /*打开-关闭-调亮-调暗*/
    public void on(){
        System.out.println("灯光 打开了");
    }

    public void off(){
        System.out.println("灯光 关闭了");
    }

    public void bright(){
        System.out.println("灯光 调亮了");
    }

    public void dim(){
        System.out.println("灯光 调暗了");
    }


}

/*外观类*/
class HomeFacade{
    //定义各个子系统对象
    /*dvd-爆米花机-投影仪-屏幕-立体声-灯光*/
    private DvdPlayer dvdPlayer;
    private Popcorn popcorn;
    private Projector projector;
    private Screen screen;
    private Stereo stereo;
    private Light light;

    /*构造器*/
    public  HomeFacade(){
        super();
        dvdPlayer=DvdPlayer.getInstance();
        popcorn=Popcorn.getInstance();
        projector=Projector.getInstance();
        screen=Screen.getInstance();
        stereo=Stereo.getInstance();
        light=Light.getInstance();
    }

    //操作分为4步
    public void ready(){
        popcorn.on();
        popcorn.pop();
        screen.down();
        projector.on();
        stereo.on();
        dvdPlayer.on();
        light.dim();
    }

    public void paly(){
        dvdPlayer.play();
    }

    public void pause(){
        dvdPlayer.pause();
    }

    public void end(){
        popcorn.off();
        light.bright();
        screen.up();
        projector.off();
        stereo.off();
        dvdPlayer.off();
    }

    public DvdPlayer getDvdPlayer() {
        return dvdPlayer;
    }

    public void setDvdPlayer(DvdPlayer dvdPlayer) {
        this.dvdPlayer = dvdPlayer;
    }

    public Popcorn getPopcorn() {
        return popcorn;
    }

    public void setPopcorn(Popcorn popcorn) {
        this.popcorn = popcorn;
    }

    public Projector getProjector() {
        return projector;
    }

    public void setProjector(Projector projector) {
        this.projector = projector;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Stereo getStereo() {
        return stereo;
    }

    public void setStereo(Stereo stereo) {
        this.stereo = stereo;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

}


//外观模式在mybatis中的源码分析
//1：Mybatis中的configuration去创建MetaObject,对象使用到外观模式


//外观模式的注意事项和细节
//1：外观模式对外屏蔽了子系统的细节，因此外观模式降低了客户端对子系统使用的复杂性
//2：外观模式对客户端与子系统的耦合关系，让子系统内部的模块更容易维护和扩展
//3：通过合理的使用外观模式，可以帮我们更好的划分访问的层次
//4：当系统需要进行分层设计时，可以考虑到使用facade模式
//5：在维护一个遗留的大型项目时，可能这个系统已经变得非常难以维护和扩展，此时可以考虑为新系统开发一个facade类
    //来提供遗留系统的比较清晰简单的接口，让新系统与facade类交互，提高复用性
//6：不要过多的或者不合理的使用外观模式，使用外观模式好，还是直接调用模块好，要以让系统有层次，利于维护为目的

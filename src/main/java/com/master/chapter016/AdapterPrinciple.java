package com.master.chapter016;

import org.springframework.web.servlet.DispatcherServlet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 适配器模式
 * @date 2021/2/28 13:51
 */
public class AdapterPrinciple {
    public static void main(String[] args) {
        //类适配器模式
        Phone phone=new Phone();
        phone.setEle5kV(new Adapter());
        phone.chongdian();
        //对象适配器模式
        Adapter2 adapter2=new Adapter2();
        Ele220kV ele220kV=new Ele220kV();
        adapter2.setEle220kV(ele220kV);
        phone.setEle5kV(adapter2);
        phone.chongdian();
        //接口适配器
        System.out.println("--------------接口适配器---------------");
        Phone3 phone3=new Phone3();
        Adapter3 adapter3=new Adapter3();
        adapter3.setSrc(new Src());
        phone3.setAdapter3(adapter3);
        phone3.chongdian();

        //springMVC源码分析
        DispatcherServlet dispatcherServlet=new DispatcherServlet();
        //doDispatch --》getHandler--》getHandlerAdapter--》

        System.out.println("--------------手写---------------");
        //手写
        MyDispatcherServlet myDispatcherServlet=new MyDispatcherServlet();
        myDispatcherServlet.doDispatch();
    }
}
//插座问题
//多功能转接插头（适配器）

//适配器模式基本介绍
//1：适配器模式（Adapter Pattern）将某个类的接口转换成客户端期望的另一个接口表示，主要目的是兼容性
    //让原本因接口不匹配不能一起工作的两个类可以协调工作，其别名为包装器（Wrapper）
//2: 适配器模式属于结构型模式
//3：主要分为三类：类适配器模式 对象适配器模式 接口适配器模式


//适配器模式的工作原理
//1：将一个类的接口转换成另一种接口让原本接口不相容的类可以兼容
//2：从用户的角度是看不到被适配者的，是解耦合的
//3：用户调用适配器转化出来的目标接口方法，适配器再调用被适配者的相关接口方法
//4：用户收到反馈结果，感觉只是和目标接口交互
    //目标，最终需要的输出《===适配器《===被适配者



//1：类适配器模式
//基本介绍：Adapter类，通过继承 src类，实现dst类接口，完成src=>dst的适配
//应用实例说明：生活中的充电器例子：充电器本身相当于adapter,220v交流电相当于src(即被适配者),
    //我们的5v直流输出电压为目标（dst）
//代码如下
//交流220V电压(被适配的类 src)
class Ele220kV{
    void run220kV(){
        System.out.println("输出220kV电压");
    }
}
//直流5V电压（适配接口 dst）
interface Ele5kV{
    void run5kV();
}
//适配器(充电器)
class Adapter extends Ele220kV implements Ele5kV{
    @Override
    public void run5kV() {
        run220kV();
        System.out.println("现在将电压输出为5kv-----------------");
        System.out.println("输出5kV电压");

    }
    @Override
    public void run220kV(){
        System.out.println("重写后的源方法--adapter灵活性增强--输出220kV电压");

    }
}

//手机
class Phone{
    //依赖
    private Ele5kV ele5kV;
    public void setEle5kV(Ele5kV ele5kV){
        this.ele5kV=ele5kV;
    }
    public void chongdian(){
        ele5kV.run5kV();
    }

    //或者使用下面这种依赖方式
    /*public void chongdian2(Ele5kV ele5kV2){
        ele5kV2.run5kV();
    }*/


}
//类适配器注意事项和细节
//1：java是单继承模式，所以类适配器需要继承src类，这一点算是一个缺点，因为这要求dst必须是一个接口，有一定局限性
//2：src类的方法在adapter中都会暴露出来，也增加了使用成本
//3：由于其继承了src类（被适配类），所以它可以根据需求重写src类的方法，是的adapter类的灵活性增强了




//2:对象适配器
//基本介绍：基本思路和类适配器模式相同，只是将adapter类作修改不是继承rsc类，而是持有src类的实例
    //以解决兼容性的问题，即：持有src类，实现dst类接口，完成src->dst的适配
//2：根据合成复用原则，在系统中尽量使用关联关系来代替继承关系
//3：对象适配器模式是适配器模式常用的一种
//代码如下：

class Adapter2 implements Ele5kV{
    /*聚合*/
    private Ele220kV ele220kV;
    public void setEle220kV(Ele220kV ele220kV){
        this.ele220kV=ele220kV;
    }
    @Override
    public void run5kV() {
        ele220kV.run220kV();
        System.out.println("-----对象适配器模式----");
        System.out.println("输出5kV电压");
    }
}
//对象适配器注意事项和细节
//1：对象适配器和类适配器其实算是同一种思想，只不过实现方式不同，根据合成复用原则，使用聚合来代替继承，
    //所以它解决了适配器必须继承src(被适配者)的局限性，也不再要求dst(目标)必须是接口
//2：使用成本更低，更灵活





//3：接口适配器模式
//基本介绍：又称适配器模式（default-adapter-pattern）或缺省适配器模式
//1：当不需要全部实现接口提供的方法时，可以设计一个抽象类先实现接口，并为该接口中每个方法提供一个默认实现（空方法）
    //那么该抽象类的子类可有选择性的覆盖父类的某些方法来实现需求
//2：适用于一个接口不想使用其所有方法的情况

//目标对象接口
interface Dst{
    void m1();//我们只想适配该方法
    void m2();
    void m3();
    void m4();
}
//目标对象抽象类
abstract  class AbsAdpter implements  Dst{
    //抽象类默认实现接口方法
    @Override
    public void m1(){

    }
    @Override
    public void m2() {

    }

    @Override
    public void m3() {

    }

    @Override
    public void m4() {

    }


}
//源对象类
class Src{
    public void src_m1(){
        System.out.println("这是被适配类--源对象类--src-m1方法");
    }
}

class Adapter3 extends AbsAdpter{
    private Src src;
    public void setSrc(Src src){
        this.src=src;
    }

    @Override
    public void m1() {
        System.out.println("适配src源对象的方法src_m1----");
        src.src_m1();
    }


}
class Phone3{
    private Adapter3 adapter3;
    public void setAdapter3(Adapter3 adapter3){
        this.adapter3=adapter3;
    }
    public void chongdian(){
        adapter3.m1();
    }
}




//适配器模式在springmvc框架应用的源码分析
//1：springMVC中的HandlerAdapter使用了适配器模式
//2：springMVC处理请求的流程回顾
//3：使用HandlerAdapter的原因分析：

//可以看到：处理器的类型不同，有多重实现方式，那么调用方式就不是确定的，如果需要直接调用Controller方法，需要调用的时候就得
    //不断得使用if else 来进行判断是哪一种子类然后执行，那么如果后面要扩展Controller,就得修改源来的代码，违背了ocp原则


//流程：
//1:浏览器login.jsp登陆页面--->1：请求login,经过tomcat,spring的中央调度控制器（springMVC配置文件，
    // 这个文件从形式和工作原理上和spring的ioc容器文件类似 applicationContext-mvc.xml,类似struct.xml）
    //中央调度控制器：是一个servlet,所有的请求都要经过他：dispatcherServlet,在web.xml
//2:通过handlerMapping的处理，找到用户希望请求的handler(UertTest普通类，加入注解@Controller就成为一个控制器，在springMVC中
    //我们不再叫控制器，叫处理器 )
//3：执行目标方法 (使用到适配器);
//4:访问一个结果，该结果是一个对象：ModelAndView
//5：调用InternalResourceViewResolver来对返回ModelAndView进行解析，就得到指定的资源，该对象需要在applicationContext.xml中配置
//6：执行解析login.OK资源,生成一个字符串
//7：将这个字符串结果，返回给tomcat,然后tomcat对这个字符串进行包装，生成一个静态的html页面，返回给浏览器
//8：浏览器本身解析返回的静态页面，并显示


//动手写一个springmvc通过适配器设计模式获取到对应Controller的源码
//说明
//1：spring定义了一个适配接口，使得每种controller都有一种对应的适配器实现类
//2：适配器代替controller执行相应的方法
//3：扩展controller时，只需要增加一个适配器类就能完成springmvc的扩展了，这就是设计模式的力量

//代码如下：

/*controller 接口*/
interface  Controller{

}
/*controller 实现*/
class HttpController implements Controller{
    public void doHttpHandler(){
        System.out.println("http Controller started...");
    }
}

class SimpleController implements Controller{
    public void doSimpleHandler(){
        System.out.println("simple Controller started...");
    }
}

class AnnotationController implements Controller{
    public void doAnnotationHandler(){
        System.out.println("annotation Controller started...");
    }
}

/*适配器接口*/
interface HandlerAdapter{
    boolean  support(Object handler);
    void handle(Object handler);
}
/*适配器实现类*/
class SimpleHandlerAdapter implements  HandlerAdapter{

    @Override
    public boolean support(Object handler) {
        return handler instanceof SimpleController;
    }

    @Override
    public void handle(Object handler) {
        ((SimpleController)handler).doSimpleHandler();
    }
}

class HttpHandlerAdapter implements HandlerAdapter{

    @Override
    public boolean support(Object handler) {
        return handler instanceof HttpController;
    }

    @Override
    public void handle(Object handler) {
        ((HttpController)handler).doHttpHandler();
    }
}

class AnnotationAdapter implements  HandlerAdapter{

    @Override
    public boolean support(Object handler) {
        return handler instanceof AnnotationController;
    }

    @Override
    public void handle(Object handler) {
        ((AnnotationController)handler).doAnnotationHandler();
    }
}

/*中央调度控制器*/
class MyDispatcherServlet{
    /*聚合*/
    public static List<HandlerAdapter>handlerAdapters=new ArrayList<>();
    /*初始化*/
    public MyDispatcherServlet(){
        handlerAdapters.add(new SimpleHandlerAdapter());
        handlerAdapters.add(new HttpHandlerAdapter());
        handlerAdapters.add(new AnnotationAdapter());
    }

    public void doDispatch(){
        //从此处模拟springMVC从request获取handler的对象
        //适配器可以获取到希望的Controller
        AnnotationController controller=new AnnotationController();
        //得到对应的适配器
        HandlerAdapter adapter=getHandler(controller);
        //通过适配器调用contoller里面的方法
        adapter.handle(controller);
    }

    public HandlerAdapter  getHandler(Controller controller){
        HandlerAdapter  myHandler=null;
        for(HandlerAdapter handlerAdapter:handlerAdapters){
            if(handlerAdapter.support(controller)){
                return handlerAdapter;
            }
        }
        return null;
    }
}


//适配器模式的注意事项和细节
//1：三种命名方式：是根据src是以怎样的形式给到adapter(在adapter里的形式)来命名的
//2：类适配器：以类给到，adapter 继承src
    //对象适配器：以类成员变量，adapter聚合（持有/依赖）src
    //接口适配器：以接口给到，在adapter中src作为一个接口
//3：adapter模式最大的作用还是将原来不兼容的接口融合到一起工作
//4：实际开发中，实现起来不拘泥于我们说的三种形式

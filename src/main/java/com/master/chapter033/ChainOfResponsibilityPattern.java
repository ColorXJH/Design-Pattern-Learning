package com.master.chapter033;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 责任链模式
 * @date 2021/3/20 18:23
 */
public class ChainOfResponsibilityPattern {
    public static void main(String[] args) {
        //创建请求
        Request myrequest=new Request(1,1000,1);
        //创建处理器
        Approver department=new DepartmentApprover("李主任");
        Approver colleage=new CollegeApprover("张院长");
        Approver viciSchool=new ViceSchoolApprover("陈副校长");
        Approver master=new SchoolApprover("王校长");
        //设置链条（处理人构成一个环形：可以从任何处理器开始调用）
        department.setFilter(colleage);
        colleage.setFilter(viciSchool);
        viciSchool.setFilter(master);
        master.setFilter(department);
        //处理请求
        master.processRequest(myrequest);


        //责任链模式在springmvc框架应用的源码分析
        //1：springMVC-HandlerExecutionChain 类就使用了责任链模式
        DispatcherServlet servlet=null;
            //servlet 的 doDispatch() 方法内部的 getHandler（）方法得到 chain
        HandlerExecutionChain chain=null;
                //责任链+拦截器（拦截器去处理各个handler）
            //mappedHandler.applyPreHandle
            //ha.handle
            //mappedHandler.applyPostHandle


    }
}

//需求：OA系统采购审批需求
    //1：采购员采购教学器材
    //2：如果金额小于5000，由教学主任审批
    //3：如果金额小于10000，由院长审批
    //4：如果金额小于30000，由副校长审批
    //5：如果金额超过30000，由校长审批

//传统解决方案:设计一个类，拥有处理方法，让子类（校长，院长。。。）继承
    //问题分析：
        //1：传统方式是：接收到一个采购请求后，根据采购的金额来调整对应的Approver(审批人)完成审批
        //2：带来的问题是:客户端这里会使用到分支判断（if-else/switch）,来对不同的采购请求处理，这样就存在如下问题
            //1：如果各个级别的人员审批金额发生变化，客户端也需要发生变化，
            //2：客户端必须明确的知道，有多少个审批级别和访问
        //3：这样对一个采购请求 进行处理 和Approver(审批人)就存在强耦合处理，不利于代码的维护和扩展
        //4：解决方案==》责任链模式


//责任链模式基本介绍
    //1：为请求创建了一个接收者对象的链，这种模式对请求的发送者和接收者进行解耦
    //2：责任链模式通常每个接收者都包含对另一个接收者的引用，如果一个对象不能处理该请求，那么它会把相同的请求传递给下一个接收者，以此类推
    //3：这种类型的设计模式属于行为型模式
        //使多个对象都有机会处理请求，从而避免请求的发送者和请求的接收者之间的耦合关系
        //将这个对象连成一条链条，并沿着这条链条传递该请求，知道有一个对象处理它位置

//责任链模式类图说明
    //1：handler:抽象的处理者，定义了一个处理请求的方法，同时含有另一个handler
    //2:concreteHandlerA,B 具体的处理者，处理它自己负责的请求，同时可以访问它的后继者（即下一个处理者）
            //如果可以处理请求，则自己处理，否则将请求交给下一个处理者处理，从而形成责任链
    //3：request:含有许多属性，表示一个请求

//采购-责任来奶模式代码如下

/*抽象处理者*/
abstract class Approver{
    Approver filter;//下一个处理者
    String name;//名字
    public Approver(String name){
        this.name=name;
    }

    //下一个处理者
    public void setFilter(Approver filter) {
        this.filter = filter;
    }

    abstract  void processRequest(Request request);
}
/*教学主任级别*/
class DepartmentApprover extends Approver{

    public DepartmentApprover(String name) {
        super(name);
    }

    @Override
    void processRequest(Request request) {
        if(request.getPrice()<=5000){
            System.out.println("请求编号："+request.getId()+" 被 "+name+" 处理");
        }else{
            filter.processRequest(request);
        }
    }
}


/*学院级别*/
class CollegeApprover extends Approver{

    public CollegeApprover(String name) {
        super(name);
    }

    @Override
    void processRequest(Request request) {
        if(request.getPrice()>5000&&request.getPrice()<=10000){
            System.out.println("请求编号："+request.getId()+" 被 "+name+" 处理");
        }else{
            filter.processRequest(request);
        }
    }
}

/*副校长级别*/
class ViceSchoolApprover extends Approver{

    public ViceSchoolApprover(String name) {
        super(name);
    }

    @Override
    void processRequest(Request request) {
        if(request.getPrice()>10000&&request.getPrice()<=30000){
            System.out.println("请求编号："+request.getId()+" 被 "+name+" 处理");
        }else{
            filter.processRequest(request);
        }

    }
}

/*校长级别*/
class SchoolApprover extends Approver{

    public SchoolApprover(String name) {
        super(name);
    }

    @Override
    void processRequest(Request request) {
        if(request.getPrice()>30000){
            System.out.println("请求编号："+request.getId()+" 被 "+name+" 处理");
        }else{
            filter.processRequest(request);
        }
    }
}

/*请求*/
class Request{
    private int type=0;//请求类型
    private int number=0;//第几个请求
    private float price=0f;//
    private int id=0;

    public Request(int type, float price, int id) {
        this.type = type;
        this.price = price;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


//责任链模式在springmvc框架应用的源码分析
    //1：springMVC-HandlerExecutionChain 类就使用了责任链模式
    //详情见main方法
        //1:springMVC请求的流程中，执行了拦截器的相关方法interceptor.preHandler()
        //2:在处理springMVC请求时，使用了责任链模式，也使用到了适配器模式
        //3：HandlerExecuteChain主要负责的是请求拦截器的执行和请求处理，但是它本身不处理请求
            //只是将请求分配给链条上注册的处理器执行，这是责任链的实现方式，减少了责任链本身
            //与处理逻辑之间的耦合，规范了流程
        //4：HandlerExecuteChain维护了HandlerInterceptor集合，它可以向其中注册相应的拦截器


//责任链模式的注意事项和细节
    //1：将请求和处理分开，实现解耦，提高系统的灵活性
    //2：简化了对象，使对象不需要知道链条的结构
    //3：性能会受到影响，特别是在链条比较长的情况下，因此需要控制链条中最大节点数量，一般是通过在handler
        //中设置一个最大节点数量，在setNext()方法中判断是否已经超出阈值，超出则不允许创建链，避免出现超长链无意识的破坏系统幸能
    //4：调试不方便，采用了类似递归的方式，调试时逻辑可能比较复杂
    //5：最佳应用场景；有多个对象可以处理同一个请求时，比如，多级请求，例如请假，加薪，等审批流程，javaweb中的tomcat对Encoding的处理，拦截器


package com.master.chapter022;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.ObjectInputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 代理模式
 * @date 2021/3/11 16:58
 */
public class ProxyPattern {
    public static void main(String[] args) {
        //静态代理
        System.out.println("--------静态代理开始-------");
        ITeacherDao teacherDao=new TeacherDaoProxy(new TeacherDao());
        teacherDao.teach();
        //jdk动态代理
        System.out.println("--------jdk动态代理开始-----------");
        //创建目标对象
        ITeacherDao2 target=new TeacherDao2();
        //创建代理对象工厂
        ProxyFactory proxyFactory=new ProxyFactory(target);
        //返回动态生成的代理对象
        ITeacherDao2 proxy=(ITeacherDao2)(proxyFactory.getProxyInstance());
        /*proxy=class  com.master.chapter022.$Proxy0 内存中动态生成了代理对象*/
        System.out.println(proxy);
        System.out.println("proxyInstance="+ proxy.getClass());
        /*通过代理对象调用目标对象方法*/
        proxy.teach("ColorXJH");
        //cglib 代理
        System.out.println("--------cglib动态代理开始-----------");
        /*目标对象*/
        TeacherDao3 teacherDao3=new TeacherDao3();
        /*代理对象工厂*/
        ProxyFactory3 proxyFactory3=new ProxyFactory3(teacherDao3);
        //获取代理对象
        TeacherDao3 proxy3=(TeacherDao3)proxyFactory3.getProxyInstance();
        /*执行代理对象方法*/
        proxy3.teacher();

    }
}


//代理模式基本介绍
//1：代理模式：为一个对象提供一个替身，以控制对这个对象的访问，即通过代理对象访问目标对象，这样做的好处是：
    //可以在目标对象的基础上，增强额外的功能操作，即扩展目标对象的功能
//2：被代理的对象可以是远程对象，创建开销大的对象，或者需要安全控制的对象
//3：代理模式主要有三种不同的形式：静态代理，动态代理（jdk代理/接口代理），和cglib代理（可以在内存中动态创建对象实现代理，而不需要实现接口，属于动态代理范畴）


//1：静态代理
//基本介绍：静态代理在使用时，需要定义接口或者父类，被代理对象（即目标对象）与代理对象一起实现相同的接口或者是继承相同的父类，

//用于实例
//具体要求：
    //1：定义一个接口ITeacherDao
    //2: 目标对象TeacherDao实现接口ITeacherDao
    //3: 使用静态代理方式，就需要在代理对象TeacherDaoProxy中也实现ITeacherDao
    //4: 调用的时候通过调用代理对象的方法来调用目标对象
    //5： 特别提醒：代理对象与目标对象要实现相同的接口，然后通过调用相同的方法来调用目标对象的方法

/*共同接口*/
interface ITeacherDao{
    void teach();
}

/*目标对象*/
class TeacherDao implements  ITeacherDao{

    @Override
    public void teach() {
        System.out.println("老师授课中。。。。");
    }
}


class TeacherDaoProxy implements ITeacherDao{
    /*被代理对象*/
    private ITeacherDao targer;

    public TeacherDaoProxy(ITeacherDao targer){
        this.targer=targer;
    }

    @Override
    public void teach() {
        System.out.println("代理对象开始代理-------");
        targer.teach();
        System.out.println("代理对象结束代理-------");
    }
}

//静态代理优缺点
    //1：优点：在不修改目标对象的功能的前提下，能通过代理对象对目标对象功能扩展
    //2：缺点：因为代理对象需要与目标对象实现一样的接口，所以会有很多代理类，一旦接口增加方法，目标对象与代理对象都需要维护



//2：动态代理
//动态代理模式的基本介绍
//1：代理对象，不需要实现接口，但是目标对象需要实现接口，否则不能用动态代理
//2：代理对象的生成，是利用JDK的API,动态在内存中构建代理对象
//3：动态代理也叫：JDK代理，接口代理


//jdk中生成代理对象的api
//1：代理类所在包：java.lang.reflect.Proxy
//2: jdk实现代理只需要使用newProxyInstance方法，但是该方法需要接受三个参数，完整的写法是：
    //static Object newProxyInstance(ClassLoader loader,Class<?>[] interface,InvocationHandler h)

/*接口*/
interface  ITeacherDao2{
    void teach(String name);//授课方法

}

/*目标对象--实现接口*/
class TeacherDao2 implements  ITeacherDao2{

    @Override
    public void teach(String name) {
        System.out.println("老师正在授课----------"+name);
    }
}

/*代理工厂*/
class ProxyFactory{
    /*维护一个目标对象*/
    private Object target;
    /*构造器，对target经行初始化*/
    public ProxyFactory(Object target){
        this.target=target;
    }
    /*给目标对象生成一个代理对象*/
    public Object getProxyInstance(){
        //动态构造代理对象
        //1：classloader:指定当前目标对象使用的类加载器，获取加载器的方法固定
        //2：interface:目标对象实现的接口类型，使用泛型方法确认类型
        //3：invocationhandler:事件处理，执行目标对象的方法时，回去触发事件处理器的方法（会把当前执行的目标对象方法作为参数传入）
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
            /*匿名子类*/
            new InvocationHandler() {
            @Override
            //proxy自动生成的代理实例，proxy为target.getClass().getInterfaces()的实例
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("jdk代理开始111-----------");
                //反射机制调用目标对象的方法
                Object returnVal=method.invoke(target,args);
                System.out.println("jdk代理结束111-----------");
                return returnVal;
            }
        });
    }

}




//3：cglib（Code Generation Library:代码生成类库）代理
//基本介绍
//1：静态代理和jdk代理模式都要求目标对象实现一个接口，但是有时候目标对象只是一个单独的对象，并没有实现任何的接口
    //这时候可使用目标对象子类来实现代理---这就是cglib代理
//2：cglib代理也叫子类代理，它是在内存中构建一个子类对象从而实现对目标对象功能扩展，它也属于动态代理
//3：cglib时一个强大的高性能的代码生成包（类库），它可以在运行其扩展java类与实现java接口，它广泛的被用在许多
    //AOP的框架使用，例如spring AOP 实现方法拦截
//4：在AOP编程中如何选择代理模式
    //1：目标对象需要实现接口，JDK动态代理
    //2；目标对象不需要实现接口，cglib代理
//5：cglib包的底层是通过使用字节码处理框架ASM来转换字节码并生成新的类
        //注意，
            //1: 代理的类不能为final
            //2: 目标对象的方法如果为final/static,那么就不会被拦截，即不会执行目标对象额外的业务方法

//代码如下

class TeacherDao3{
    public void teacher(){
        System.out.println("-------老师授课中,我是cglib代理，我不需要实现接口-------");
    }
}
/*spring4.x之后自动集成了cglib*/
class ProxyFactory3 implements MethodInterceptor {
    /*维护一个目标对象*/
    private Object target;
    /*构造器传入一个被代理的对象（目标对象target）*/
    public ProxyFactory3(Object target){
        this.target=target;
    }
    /*返回一个代理对象（是target对象的代理对象）*/
    public Object getProxyInstance(){
        //1：创建一个工具类
        Enhancer enhancer=new Enhancer();
        //2：设置父类
        enhancer.setSuperclass(target.getClass());
        //3：设置回调函数
        enhancer.setCallback(this);
        //4：创建子类对象（即代理对象）
        return enhancer.create();
    }
    /*重写，会调用目标对象的方法，类似invoke*/
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("-----cglib--start----");
        Object returnVal=method.invoke(target,args);
        System.out.println("-----cglib--end----");

        return null;
    }
}


//代理模式的变体
//1：防火墙代理：内网通过代理穿透防火墙，实现对公网的访问
//2：缓存代理：比如当请求图片等资源时，先到缓存代理取，如果取到资源就ok,如果取不到资源，再到公网或者数据库取，然后缓存
//3：远程代理：远程对象的本地代表，通过它可以把远程对象当成本地对象来调用，远程代理通过网络和真正的远程对象沟通信息
//4：同步代理：主要使用在多线程编程中，完成多线程间同步工作


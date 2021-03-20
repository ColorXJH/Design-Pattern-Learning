package com.master.chapter010;

import java.awt.image.VolatileImage;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 单例模式
 * @date 2021/2/24 16:49
 */
public class SinglePrinciple1 {
    public static void main(String[] args) {
        System.out.println("饿汉模式（静态变量）-----------------");
        SingleTest01 singleTest01= SingleTest01.getInstance();
        SingleTest01 singleTest02= SingleTest01.getInstance();
        //判断是否相等
        System.out.println(singleTest01==singleTest02);
        System.out.println(singleTest01.hashCode());
        System.out.println(singleTest02.hashCode());

        System.out.println("饿汉模式（静态代码块）---------------");
        SingleTest02 singleTest021=SingleTest02.getInstance2();
        SingleTest02 singleTest022=SingleTest02.getInstance2();
        //判断是否相等
        System.out.println(singleTest021==singleTest022);
        System.out.println(singleTest021.hashCode());
        System.out.println(singleTest022.hashCode());

        System.out.println("懒汉模式（线程不安全）---------------");
        SingleTest03 singleTest031=SingleTest03.getInstance03();
        SingleTest03 singleTest032=SingleTest03.getInstance03();
        //判断是否相等
        System.out.println(singleTest031==singleTest032);
        System.out.println(singleTest031.hashCode());
        System.out.println(singleTest032.hashCode());

        System.out.println("懒汉模式（线程安全,同步方法）---------------");
        SingleTest04 singleTest041=SingleTest04.getInstance04();
        SingleTest04 singleTest042=SingleTest04.getInstance04();
        //判断是否相等
        System.out.println(singleTest041==singleTest042);
        System.out.println(singleTest041.hashCode());
        System.out.println(singleTest042.hashCode());


        System.out.println("懒汉模式（线程安全,同步代码块）---------------");
        SingleTest05 singleTest051=SingleTest05.getInstance05();
        SingleTest05 singleTest052=SingleTest05.getInstance05();
        //判断是否相等
        System.out.println(singleTest051==singleTest052);
        System.out.println(singleTest051.hashCode());
        System.out.println(singleTest052.hashCode());


        System.out.println("双检查模式---------------");
        SingleTest06 singleTest061=SingleTest06.getInstance06();
        SingleTest06 singleTest062=SingleTest06.getInstance06();
        //判断是否相等
        System.out.println(singleTest061==singleTest062);
        System.out.println(singleTest061.hashCode());
        System.out.println(singleTest062.hashCode());


        System.out.println("静态内部类---------------");
        SingleTest07 singleTest071=SingleTest07.getInstance07();
        SingleTest07 singleTest072=SingleTest07.getInstance07();
        //判断是否相等
        System.out.println(singleTest071==singleTest072);
        System.out.println(singleTest071.hashCode());
        System.out.println(singleTest072.hashCode());


        System.out.println("枚举方式---------------");
        SingleTest08 singleTest081=SingleTest08.instance08;
        SingleTest08 singleTest082=SingleTest08.instance08;
        //判断是否相等
        System.out.println(singleTest081==singleTest082);
        System.out.println(singleTest081.hashCode());
        System.out.println(singleTest082.hashCode());


        //jdk java.lang.Runtime使用了单例模式
        Runtime runtime=Runtime.getRuntime();
    }

}
//所谓类的单例设计模式，就是采取一定的方法保证在整个的软件系统中，对某个类只能存在一个对象实例，并且该类只提供一个
//取得其对象实例的方法

//比如hibernate的SessionFactory，它充当数据存储源的代理，并负责创建session对象，SessionFactory并不是轻量级的
//一般情况下，一个项目通常只需要一个sessionFactory就够，这就会使用到单例模式

//单例模式的8种写法
//1饿汉模式（静态常量）
//2饿汉模式（静态代码块）
//3懒汉模式（线程不安全）
//4懒汉模式（线程安全，同步方法）
//5懒汉模式（线程安全，同步代码块）
//6双重检查
//7静态内部类
//8枚举

//1：饿汉模式（静态常量） 步骤如下：
    //1：构造器私有化
    //2：类的内部创建对象
    //3：向外部暴露一个静态的公共方法
    //4：代码实现
class SingleTest01{
    //1:构造器私有化
    private SingleTest01(){

    }

    //2：本类内部创建一个对象实例
    //注意这里的final也可以不要
    //final修饰后必须要复制，在属性后面赋值，或者在初始化代码块或者构造函数中赋值
    private final static SingleTest01 instance=new SingleTest01();

    //3提供一个公有的静态方法，返回实例对象
    public static SingleTest01 getInstance(){
        return instance;
    }

}

//优缺点说明
//1:优点，这种写法比较简单，就是在类装载的时候完成了实例化，避免了线程同步问题
//2：缺点：在类装在的时候就完成了实例化，没有达到lazy-loading的效果，如果一直没使用这个实例，则会造成内存的浪费
//3：这种方式基于classloader机制避免了多线程同步的问题，不过instance在类装载的时候就实例化，在单例模式种大多数都是调用getInstance方法
    //但是倒置类装载的原因有很多，因此不能确定有其他的方式（或者其他的静态方法）导致类装载，这时候初始化instance就没有达到lazy-loading的效果
//4：这种单例模式可用，但是可能造成内存浪费




//2：饿汉模式（静态代码块）
class SingleTest02{
    //1：定义内部实例变量
    private static SingleTest02 instance;
    //2:构造方法私有化
    private SingleTest02(){

    }
    //3：在静态代码块中创建单例对象
    static{
        instance=new SingleTest02();
    }
    //4：提供静态的公有方法返回实例
    public static SingleTest02 getInstance2(){
        return instance;
    }


}
//优缺点说明
//这种方式和上述方式相似，只不过将类的实例化过程放在了静态代码块中，也是在类装载的时候，就执行静态代码块中的代码
//初始化实例，优缺点和上述一样
//结论：这种单例模式可用，但是有可能造成内存浪费



//3:懒汉模式（线程不安全）
class SingleTest03{
    private static SingleTest03 instance;
    private SingleTest03(){

    }
    public static SingleTest03 getInstance03(){
        if(instance==null){
            instance=new SingleTest03();
        }
        return instance;
    }
}

//优缺点
//1:起到了lazy-loading的效果，但是只能在单线程模式下使用
//2：如果在多线程下，一个线程进入了if(single==null)判断语句块，还未来得及向下执行，另一个线程开始了执行，发现条件为真，同样
    //进入了该语句块，这样就产生了多个实例
//3：结论，在实际开发中不要使用这种方式



//4：懒汉模式（线程安全，同步方法）
class SingleTest04{
    private static SingleTest04 instance;
    private SingleTest04(){

    }
    //线程安全，同步方法
    public static  synchronized  SingleTest04 getInstance04(){
            if(instance==null){
                instance=new SingleTest04();
            }
            return instance;
    }
}
//优缺点
//1：解决了线程不安全的问题
//2：效率太低了，每个线程在想获取类的实例的时候，执行getInstance04()方法都要进行同步，而其实这个方法
    //只执行一次实例化代码就够了，后面的想获得该类的实例，直接return就可以，方法同步效率太低
//3：结论；在实际开发中，不推荐使用这种方式



//5：懒汉模式（线程安全，同步代码块）

class SingleTest05{
    private static SingleTest05 instance;
    private SingleTest05(){

    }
    public static SingleTest05 getInstance05(){
        if(instance==null){
            //这里其实并不能实现线程安全，多线程还是可能进入if代码块
            synchronized (SingleTest05.class){
                instance=new SingleTest05();
            }
        }
        return instance;
    }
}
//优缺点说明
//1：这种方式的本意是对方式4进行改进，但是这个方式并不能保证线程安全，所以开发中不能使用这种方式




//6双检查模式
class SingleTest06{
    private static volatile SingleTest06 instance;
    private SingleTest06(){

    }

    public static SingleTest06 getInstance06(){
        if(instance==null){
            //检查是否为空进入同步代码块，当多线程多次调用时只会刚开始几次进入同步，
            //后面就会直接判断是否为null,从而直接返回实例
            synchronized (SingleTest06.class){
                //如果为空创建实例
                if(instance==null){
                    instance=new SingleTest06();
                }
            }
        }
        return instance;
    }

}

//优缺点
//1：volatile（可以认为是轻量级的synchronized）关键字可以让修改的可见性立即更新到cup内存（即刷新缓存），保证了可见性
//2：synchronized代码块保证了只有一个线程可以创建实例，并且只能创建一次（第二个检查判断只允许创建一次），当后面的线程再次
    //进入时会被第一个检查判断拦截掉 ，也确保了lazy-loading
//double-check概念是多线程开发中经常使用的，如代码所示，我们进行了两次的if(instance06==null)检查，
//这样就可以保证线程安全了，实际开发中比较推荐这种模式



//7：静态内部类
class SingleTest07{
    private  SingleTest07 (){

    }
    //静态内部类（特点：SingleTest07装载的时候，静态内部类SingletonInstance不会被装载）
    //当我们去调用SingleTest07类的静态方法getInstance07()时，会导致静态内部类被装载（且只装载一次 classloader作用）
    //在类被装载的时候，是线程安全的
     private static class SingletonInstance{
        private static final SingleTest07 instaince=new SingleTest07();
    }
    //
    public static SingleTest07 getInstance07(){
        return SingletonInstance.instaince;
    }


}
//优缺点说明
//1：这种方式采用了类装载的机制来保证初始化实例时只有一个线程
//2：静态内部类方式在SingleTest07类被装载时并不会立即实例化，而是在需要实例化时，调用getInstance07()方法
    //才会去装载SingletonInstance类，从而完成SingleTest07的实例化
//3：类的静态属性只会在第一次加载类的时候初始化，所以在这里jvm帮助我们保证了线程的安全性，在类进行初始化时
    //别的线程是无法进入的
//4：优点：避免了线程的不安全，利用静态内部类的特点实现了延迟加载，效率高，推荐使用




//8：枚举方式 推荐使用
enum SingleTest08{
    instance08;
    public void method(){

    }
}

//优缺点说明
//1：借助jdk1.5中添加的枚举来实现单例模式，不仅能够避免多线程问题，而且还能防止反序列化重新创建新的对象
//2：这是effective-java作者josh bloch提倡的方式



//jdk中使用的单例模式
//java.lang.Runtime就是经典的单例模式


//单例模式的注意事项和细节说明
//1：单例模式保证了系统内存中只存在一个对象，节省了系统资源，对于一些需要频繁创建销毁的对象，使用单例模式可以提高性能
//2：当想要实例化一个单例类对象时，必须要记住要使用相应的获取对象的方法，而不是使用new
//3: 单例模式的使用场景：需要频繁的创建和销毁的对象，创建对象时耗时过多或者消耗资源过多（即重量级对象）
    //但又经常用到的对象，工具类对象，频繁访问数据库或文件的对象（比如数据源，session工厂等）


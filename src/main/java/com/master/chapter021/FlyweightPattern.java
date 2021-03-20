package com.master.chapter021;

import java.util.HashMap;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 享元模式
 * @date 2021/3/10 17:01
 */
public class FlyweightPattern {
    public static void main(String[] args) {
            String a="hello";
            String b=new String("hello");
            //虽然他们不相等，但是在string的常量池中，new是在堆空间中重新开辟一块内存，这个内存指向常量池中之前a变量的地址，本质上他们是引用了了同一个对象
            //但是因为new本身开辟了一块地址，所以比较地址时是不相等的
            System.out.println(a==b);


            //网站享元模式
            WebsiteFactory factory=new WebsiteFactory();
            Website web1=factory.getWebsite("新闻---");
            User user=new User();
            user.setName("xjh");
            web1.use(user);
            Website web2=factory.getWebsite("博客---");
            user.setName("kcy");
            web2.use(user);
            Website web3=factory.getWebsite("博客---");
            user.setName("wxy");
            web3.use(user);
            int count=factory.getWebsiteCount();
            System.out.println("网站的类型---："+count);


            System.out.println("-----------jdk享元模式源码分析----------------");
            //享元模式jdk源码分析
            //valueOf方法在-128~127范围内，就使用享元模式

            Integer x=Integer.valueOf(127);
            Integer y=new Integer(127);
            Integer z=Integer.valueOf(127);
            Integer w=new Integer(127);
            System.out.println(x.equals(y));//true
            System.out.println(x==y);//false
            System.out.println(x==z);//true
            System.out.println(w==x);//false
            System.out.println(w==y);//false

    }
}

//展示网站项目需求
//小型的外包项目，给客户A做一个产品展示网站，客户A的朋友感觉效果不错，也希望做这样的产品展示网站，但是要求有些不同：
    //1：有客户要求以新闻形式发布
    //2：有客户要求以博客形式发布
    //3：有客户希望以微信公众号形式发布


//传统方案解决网站展现项目
//1：直接复制粘贴一份，然后根据客户的需求不同，进行定制修改
//2：给每个网站租用一个空间

//传统方案问题问题
//1：需要的网站结构相似度很高，而且都不知高访问量网站，如果分成多个虚拟空间来处理，相当于一个相同的网站的实例对象有很多，造成服务器的资源浪费
//2：解决思路：整合到一个网站中，共享其相关的代码和数据，对于硬盘，内存，cpu，数据库空间等服务器资源都可以达成共享，减少服务器资源
//3：对于代码来说：由于是一份实例，维护和扩展都更加容易
//4：上面的解决思路就可以使用 享元模式 来解决



//享元模式基本介绍
//1:享元模式（flyweight-pattern）也叫蝇量模式，运用共享技术有效支持大量细粒度的对象
//2：常用于系统底层开发，解决系统的性能问题，像数据库连接池，里面都是创建好的连接对象，在这些连接对象中有我们需要的则直接拿来使用，
    //避免重复创建，如果没有我们需要的，则直接创建一个
//3：享元模式能够解决重复对象的内存浪费问题，当系统中有大量相似的重复对象，需要缓冲池时，不需要总是创建新对象，可以从缓冲池里面拿，
    //这样就可以降低系统内存，同时提高效率
//4：享元模式经典的应用场景就是池技术了，String常量池，数据库连接池，缓冲池等等都是享元模式的应用，享元模式是池技术的重要实现方式


//享元模式原理类图
    //1：flyweight是抽象的享元角色，他是产品的抽象类，同时定义对象的外部状态和内部状态（内部状态比较稳定，外部状态较多变）的接口或实现
    //2：ConcreteFlyweight是具体的享元角色，是具体的产品类，实现抽象角色定义相关业务
    //3：unsharedConcreteFlyweight:是不可共享的角色（可能出现在享元模式中，但是不会出现在享元工厂中），
    //4；flyweightFactory:享元工厂，用于构建一个池容器（集合），同时提供从池中获取对象方法
    //5：client:客户端，通过享元工厂获取ConcreteFlyweight/unsharedConcreteFlyweight

//聊聊外部状态和内部状体
    //比如围棋，五子棋，跳棋，他们都有大量的棋子对象，围棋和五子棋只有黑白两色，跳棋颜色稍微多一点，所以棋子颜色就是棋子的内部状态，
    //而各个棋子之间的差别就是位置的不同，当我们落子后，落子颜色是固定得，位置是变化的，所以棋子的坐标就是棋子的外部状态

//1：享元模式提出了两个要求：细粒度和共享对象，这里就涉及到了内部状态和外部状态了，即将对象的信息分为两个部分，内部状态和外部状态
//2：内部状态是指对象共享出来的信息，存储在享元对象内部，且不会随环境的改变而改变
//3：外部状态是指对象得以依赖的一个标记，是随环境改变而改变的，不肯共享的状态
//4：举个例子：围棋理论上有361个空位可以放棋子，每盘棋都有可能有两三百个棋子对象产生，因为内存空间有限，一台服务器很难支撑更多的玩家玩围棋游戏
    //如果用享元模式来处理棋子，那么棋子对象可以减少到两个实例，这样就很好的解决了对象的开销问题



//享元模式代码解决

/*网站抽象类*/
abstract class Website{
    abstract  void use(User user);
}
/*具体网站类*/
class ConcreteWebsite extends Website{
    /*内部状态*/
    private String type="";//网站发布类型

    public ConcreteWebsite(String type){
        this.type=type;
    }

    /*User为外部状态*/
    @Override
    void use(User user) {
        System.out.println("网站的发布形式为"+type+"使用者为 "+user.getName());
    }
}
/*外部状态*/
class User{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}


/*网站工厂*/
class WebsiteFactory{
    /*集合--充当池的作用*/
    private HashMap<String,ConcreteWebsite> pool=new HashMap<>();

    /*一般内部状态在工厂类中，外部状态不会在工厂类中*/
    //根据type,返回网站，如果没有，就创建一个放在池中
    public Website getWebsite(String type){
        if(!pool.containsKey(type)){
            pool.put(type,new ConcreteWebsite(type));
        }
        return (Website)pool.get(type);
    }

    //获取网站分类的总数（池中有多少个网站类型）
    public int getWebsiteCount(){
        return pool.size();
    }
}

//将内部状态和外部状态分开，共享内部状态集合，接口使用（依赖）外部状态

//jdk 享元模式源码分析
    //Integer类的静态方法valueOf(int x);



//享元模式注意事项和细节
    //1：享元模式可以这样理解，“享”表示共享，“元”表示对象
    //2：系统中有大量的对象，这些对象消耗大量内存，并且对象的状态大部分可以外部化时，我们就可以考虑使用享元模式
    //3：用唯一标识码判断，如果在内存中有，则返回这个唯一标识码所标识的对象，用hashmap/hashtable存储
    //4：享元模式大大减少了对象的创建，降低了程序内存的占用，提高了效率
    //5：享元模式提高了系统的复杂度，需要分离出内部状态和外部状态，而外部状态需要有固化特征，不应该随着内部状态改变而改变
        //这是我们使用享元模式需要注意的地方
    //6：使用享元模式时，注意划分内部状态和外部状态，并且需要一个工厂类加以控制，
    //7：享元模式的经典应用场景是需要缓冲池的场景，比如string常量池，数据库连接池






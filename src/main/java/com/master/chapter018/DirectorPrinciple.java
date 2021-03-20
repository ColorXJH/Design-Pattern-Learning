package com.master.chapter018;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 装饰器模式
 * @date 2021/3/5 10:13
 */
public class DirectorPrinciple {
    public static void main(String[] args) {
        //装饰者模式下咖啡订单

        //1：点一份longblack
        Drink drink=new LongBlack();
        System.out.println(drink.getDes()+"--咖啡价格--："+drink.cost());
        //2：加入一份牛奶
        drink=new Milk(drink);
        System.out.println(drink.getDes()+"--咖啡价格--："+drink.cost());
        //3:加入一份巧克力
        drink=new Chocolate(drink);
        System.out.println(drink.getDes()+"--咖啡价格--："+drink.cost());
        //4:加入两份巧克力
        drink=new Chocolate(drink);
        System.out.println(drink.getDes()+"--咖啡价格--："+drink.cost());
        //jdk FilterInputStream源码分析
        try {
            //InputStream类似于Drink(Component)
            //DataInputStream类似于各种调料
            //FileInputStream是装饰着，它包含了InputStream(被装饰这)
            DataInputStream dataInputStream=new DataInputStream(new FileInputStream("F:\\BaiduNetdiskDownload\\computer-basic.pdf"));
            int x=dataInputStream.read();
            System.out.println(x);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

//星巴克咖啡订单项目引出装饰器模式
//1：咖啡种类/单品咖啡：Espresso(意大利浓咖啡)，shortblack,longblack(美式咖啡)，decaf(无因咖啡)
//2：调料：milk,soy(豆浆)，chocolate
//3: 要求在扩展新的咖啡种类时，具有良好的扩展性，改动方便，维护方便
//4：使用OO的来计算不同种类咖啡的费用：客户可以点单品咖啡，也可以单品咖啡+调料组合



//方案1：较差的方案：将所有的组合通过类列举出来（类爆炸）
//方案2：稍微好的方案：前面方案一因为咖啡单品+调料组合会导致类的倍增，可以改进：将调料内置到drink类
    //drink {
        //milk --可以设计为boolean
        //soy
        //chocolate
        //cost();
        //hasMilk();
        //hasSoy();
        //hasChocolate();
    // }
    //然后各种单品继承该drink
    //该方案在增加/删除调料种类时，代码的维护量还是很大
    //考虑使用装饰者模式



//方案3：装饰者模式
//1：定义：动态的将新功能附加到对象上，在对象功能扩展方面，它比继承更有弹性，装饰者模式也体现了开闭原则
//2：这里提到的动态的将新功能附加到对象上和符合OCP原则，会在下面的实际代码中体现

//装饰者模式的原理：
//1：装饰者模式就像打包一个快递：主体：比如陶瓷，衣服；包装：比如报纸填充，塑料填充，纸板填充
//2：Component:主体，比如类似前面的drink
//3: ConcreteComponent和Decorator
    //ConcreteComponent:具体的主类：比如前面的各个单品咖啡
    //Decorator:装饰者：比如前面的各种调料
//4：在如图的Component和ConcreteComponent之间，如果ConcreteComponent类很多，还可以设计一个
    //缓冲层，将共有的部分提取出来，抽象成一个类


//装饰者模式下得订单： 2份巧克力+1份牛奶的LongBlack
    // longblack->milk->chocolate->chocolate
    //说明 1：milk包含了longblack
        //2:一份巧克力包含了（milk+longblack）
        //3:一份巧克力包含了（chocolate+milk+longblack）
        //4:这样不管是什么形式的单品咖啡+调料组合，通过递归方式可以方便的组合和维护
//代码如下：

/*component*/
abstract class Drink{
    private String des;
    private float price=0.0f;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    /*计算费用的抽象方法--子类实现*/
    public abstract float cost();
}
/*缓冲层*/
class Coffee extends Drink{

    @Override
    public float cost() {
       return  super.getPrice();
    }
}

/*具体单品咖啡*/
class Espresso extends Coffee{
    public Espresso(){
        setDes("意大利咖啡");
        setPrice(6.0f);
    }
}

class LongBlack extends Coffee{
    public LongBlack(){
        setDes("long-black");
        setPrice(5.0f);
    }
}

class ShortBlack extends Coffee{
    public ShortBlack(){
        setDes("short-black");
        setPrice(4.0f);
    }
}

/*装饰者--（包含bei装饰者）*/

class Decorator extends Drink{
    private Drink obj;
    //组合
    public Decorator(Drink obj){
        this.obj=obj;
    }
    @Override
    public float cost() {
        //getPrice():先拿到自己的价格+单品咖啡价格
       return  getPrice()+obj.cost();
    }

    @Override
    public String getDes() {
        //递归调用：拿到本身的描述和价格 并且递归调用其组合对象的描述和价格
          return super.getDes()+"-----"+super.getPrice()+"  and  "+obj.getDes();
    }
}

/*具体装饰者*/
class Chocolate extends Decorator{

    public Chocolate(Drink obj) {
        super(obj);
        setDes("--chocolate--");
        setPrice(3.0f);//巧克力调味品价格
    }
}

class Milk extends Decorator{

    public Milk(Drink obj) {
        super(obj);
        setDes("--milk--");
        setPrice(2.0f);//牛奶调味品价格
    }
}

class Soy extends Decorator{

    public Soy(Drink obj) {
        super(obj);
        setDes("--soy--");
        setPrice(1.0f);//豆浆调味品价格
    }
}


//装饰者模式在jdk的源码分析
//Java的IO结构，FilterInputStream就是一个装饰者
/*                                                          InputStream
        FileInputStream       StringBufferInputStream     ByteArrayInputStream     FilterInputStream
                                                                                   BuffferInputStream     DataInputStream    LineNumberInputStream
 // InputStream是一个抽象类，即Component
 // FilterInputStream 继承了 InputStream，是一个装饰者类。他包含了InputStream（被装饰的对象）
 // DataInputStream 继承了 FilterInputStream，实现了DataInput,是一个具体的装饰者
 */




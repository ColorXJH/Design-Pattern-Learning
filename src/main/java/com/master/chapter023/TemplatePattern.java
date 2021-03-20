package com.master.chapter023;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 模板方法模式
 * @date 2021/3/14 11:57
 */
public class TemplatePattern {
    public static void main(String[] args) {
        SoyaMilk red=new RedSoyaMilk();
        red.make();
        SoyaMilk peanut=new PeanutSoyaMilk();
        peanut.make();

        /*钩子方法*/
        System.out.println("--------钩子方法---hook-----------");
        SoyaMilk2 hook=new  PureSoyaMilk();
        hook.make();

        /*模板方法模式在spring框架中的源码分析*/
        ConfigurableApplicationContext context=null;
        //refresh()方法--》AbstractApplicationContext类的refresh()方法
    }
}

//豆浆制作问题：编写制作豆浆的程序如下：
    //1：制作豆浆的流程，选材--》添加配料--》浸泡--》放到豆浆机打碎
    //2：通过添加不同的配料，可以制作出不同口味的豆浆
    //3：选材，浸泡和放到豆浆机打碎这几个步骤对于制作每种口味的豆浆都是一样的
    //4：请使用模板方法模式完成

//模板方法基本介绍：
//1：模板方法模式（Template Method Pattern）：又叫模板模式（Template Pattern）,在一个抽象类
    //公开定义了执行它的方法的模板，它的子类可以按需要重写方法实现，但调用将以抽象类中定义的方法进行
//2：简单说，模板方法模式定义一个操作中的算法的骨架，而将一些步骤延迟到子类中，使得子类可以不改变一个算法的结构，
    //就可以重新定义该算法的某些特定步骤
//3：这种类型的设计模式属于行为型模式


//模板方法模式角色及职责
    //AbstractClass 抽象类，类中实现了模板方法，定义了算法的骨架，具体子类需要实现其中的额抽象方法
    //ConcreteClass 实现具体的抽象方法，完成算法中特定子类的步骤

//代码如下

/*抽奖模板类*/
abstract class SoyaMilk{
    /*模板方法，可以做成final类型，不让子类去覆盖*/
   public final void make(){
       select();
       add();
       soak();
       beat();
    };
    //选材料
    void  select(){
         System.out.println("选择新鲜的黄豆");
    };
    //添加配料
    abstract void  add();
    //浸泡
    void soak(){
        System.out.println("黄豆和配料浸泡三个小时");
    }
    //打碎
    void beat(){
        System.out.println("黄豆和配料放到豆浆机中打碎");
    }


}


/*红豆豆浆*/
class RedSoyaMilk extends SoyaMilk{

    @Override
    void add() {
        System.out.println("加入上好红豆作为配料");
    }
}

/*花生豆浆*/
class PeanutSoyaMilk extends SoyaMilk{

    @Override
    void add() {
        System.out.println("加入上好花生作为配料");
    }
}

//模板方法中的钩子方法
    //1：在模板方法模式的父类中，我们可以定义一个钩子方法，它默认不做任何事情，子类可以视情况要不要覆盖它，该方法被称作”钩子“
    //2：用上面的代码作为例子，：如果我们希望制作纯豆浆，不添加任何配料，改造一下代码


/*抽奖模板类*/
abstract class SoyaMilk2{
    /*模板方法，可以做成final类型，不让子类去覆盖*/
    public final void make(){
        select();
        if(customerCondiments()){

        }else{
            add();
        }

        soak();
        beat();
    };
    //选材料
    void  select(){
        System.out.println("选择新鲜的黄豆");
    };
    //添加配料
    abstract void  add();
    //浸泡
    void soak(){
        System.out.println("黄豆浸泡三个小时");
    }
    //打碎
    void beat(){
        System.out.println("黄豆放到豆浆机中打碎");
    }
    /*钩子方法：决定是否需要添加配料*/
    abstract boolean customerCondiments();

}
/*纯豆浆*/
class PureSoyaMilk extends  SoyaMilk2{


    @Override
    void add() {
    }

    @Override
    boolean customerCondiments() {
        return true;
    }
}

//模板方法模式在spring框架中的源码分析
    //1：spring IOC容器初始化时运用到了模板方法模式
    //2：代码分析见main方法


//模板方法模式注意事项和细节
    //1：基本思路是：算法只存在于一个地方，，也就是在父类中，容易修改，需要修改算法时，只要修改父类的模板方法
        //或者已经实现的某些步骤，子类就会继承这些修改，
    //2：实现了最大化代码复用，父类的模板方法和已经实现的某些步骤会被子类继承而直接使用
    //3：既统一了算法，又提供了很大的灵活性，父类的模板方法确保了算法的结构保持不变，同时由子类提供部分步骤的实现
    //4：该模式的不足之处在于：每一个不同的实现都需要一个子类实现，导致类的个数增加，使得系统更加庞大
    //5：一般模板方法都加上final关键字，防止子类重写模板方法
    //6：使用场景：当要完成某个过程，该过程需要执行一系列步骤，这一些列的步骤基本相同，但是其个别步骤在是现实时可能不同，通常考虑用模板方法模式来处理
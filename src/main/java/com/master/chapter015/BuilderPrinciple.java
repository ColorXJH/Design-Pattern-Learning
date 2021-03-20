package com.master.chapter015;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 建造者模式
 * @date 2021/2/28 10:18
 */
public class BuilderPrinciple {
    public static void main(String[] args) {

        //普通方式
        //CommonHouse  commonHouse=new CommonHouse();
        //commonHouse.build();

        //建造者模式
        Director director =new Director();
        Builder builder=new CommonBuilder();
        director.setBuilder(builder);
        director.createHouse();

        //建造者模式在JDK源码中的分析：java.lang.StringBuilder
        StringBuilder builder1=new StringBuilder("hello-world");
    }
}

//盖房子项目需求
//1：需要建造房子：这一过程为打桩，砌墙，封顶
//2：房子有各式各样的，比如普通房，高楼，别墅，各种房子的过程虽然一样，但是要求不要相同的
//3：编程 完成

//1：传统方式

abstract class House{
    //打桩，砌墙，封顶
    public abstract  void buildBasic();
    public abstract  void buildWalls();
    public abstract  void roofed();

    public void build(){
        buildBasic();
        buildWalls();
        roofed();
    }
}

class CommonHouse extends House{

    @Override
    public void buildBasic() {
        System.out.println("普通房子打地基");
    }

    @Override
    public void buildWalls() {
        System.out.println("普通房子砌墙");
    }

    @Override
    public void roofed() {
        System.out.println("普通房子封顶");
    }
}

//传统方式优缺点
//1：优点：比较好理解，简单易操作
//2：缺点：设计的程序结构，过于简单，没有设计缓冲层对象，程序的扩展和维护都不好，也就是说这种设计方案，把产品（即：房子）
    //和创建产品的过程（即：建房子的流程）封装在一起，耦合性增强了
//3：解决方案：将产品和产品建造过程解耦==》建造者模式

//建造者模式基本介绍
//1：建造者模式又叫生成器模式，是一种对象构建模式，它可以将复杂对象的建造过程抽象出来（抽象类别），使这个抽象过程的不同的不同实现方法
    //可以构造出不同表现（属性）的对象
//2：建造者模式是一步一步创建一个复杂的对象，它允许用户只通过指定复杂对象的类型和内容就可以构建他们
    //用户不需要知道内部的具体构建细节

//建造者模式的四个角色
//1：product(产品角色)：一个具体的产品对象
//2：builder(抽象建造者)：创建一个product对象的各个部件指定的接口/抽象类
//3：concreteBuilder(具体建造者)：实现接口，构建和装配各个部件
//4：director(指挥者)：构建一个使用builder接口的对象，它主要是用于创建一个复杂的对象，它主要有两个作用
    //1：隔离了客户与对象的生产过程
    //2：负责控制产品对象的生产过程



//造房子代码
/*建造者接口*/
interface Builder{
      HouseA house=new HouseA();
      void buildBasic();
      void buildWalls();
      void buildRoofed();
      HouseA getResult();

}
/*产品*/
class HouseA{
    private String basic;
    private String walls;
    private String foofed;

    public String getBasic() {
        return basic;
    }

    public void setBasic(String basic) {
        this.basic = basic;
    }

    public String getWalls() {
        return walls;
    }

    public void setWalls(String walls) {
        this.walls = walls;
    }

    public String getFoofed() {
        return foofed;
    }

    public void setFoofed(String foofed) {
        this.foofed = foofed;
    }

    @Override
    public String toString() {
        return "HouseA{" +
                "basic='" + basic + '\'' +
                ", walls='" + walls + '\'' +
                ", foofed='" + foofed + '\'' +
                '}';
    }
}
/*具体建造者，实现建造方法*/
class CommonBuilder implements Builder{

    @Override
    public void buildBasic() {
        Builder.house.setBasic("打地基");
        System.out.println("建造地基");
    }

    @Override
    public void buildWalls() {
        Builder.house.setWalls("砌围墙");
        System.out.println("建造围墙");
    }

    @Override
    public void buildRoofed() {
        Builder.house.setFoofed("盖房顶");
        System.out.println("建造房顶");
    }

    @Override
    public HouseA getResult() {
        return Builder.house;
    }
}

/*指挥者*/
class Director{
    /*聚合*/
    /*它对于房子来说抽象出了建造流程，并可以随意改变流程*/
    private Builder builder;
    public void setBuilder(Builder builder){
        this.builder=builder;
    }
    //如何处理建造房子的流程，交给指挥者(制作流程由此指挥)
    public void createHouse(){
        builder.buildBasic();
        builder.buildWalls();
        builder.buildRoofed();
        HouseA houseA=builder.getResult();
        System.out.println(houseA.toString());
    }
}



//建造者模式在JDK源码中的分析：java.lang.StringBuilder

//Appendable接口定义了append抽象方法（抽象建造者）
//AbstractStringBuilder 实现了Appendable接口方法，这里其实已经是一个建造者了，只是不能实例化
//StringBuilder 既充当了指挥者角色，同时充当了具体的建造者，建造方法的实现是由AbstractStringBuilder来完成
    //而StringBuilder只是继承了AbstractStringBuilder


//注意事项和细节
//1：客户端（使用程序）不必知道产品内部的组成细节，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象
//2: 每一个具体建造者都相对独立，而与其他具体建造者无关，因此可以很方便的替换具体建造者，用户使用不同的具体建造者即可得到
    //不同的产品对象
//3：可以更加精细的控制产品的创建过程，将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，也更方便实用程序来控制创建过程
//4：增加新的具体建造者无需修改原有类库的代码，指挥者针对抽象建造者编程，系统扩展方便，负荷开闭原则
//5：建造者模式所创建出的产品一般都具有较多的共同点，其组成部分相似，如果产品之间的差异性很大，则不适合使用建造者模式，因此适用范围受一定限制
//6：如果产品的内部变化复杂，可能会导致需要定义很多具体建造者来实现这种变化，会导致代码膨胀，需要考虑这点
//7：抽象工厂模式VS建造者模式
    //抽象工厂模式实现对产品家族的创建，一个产品家族是这样的一系列产品：具有不同分类维度的产品组合，采用抽象工厂模式不需要关系构建过程
    //只关心什么产品由什么工厂生产即可，而建造者模式则是要求按照指定的蓝图建造产品，它的主要目的是通过组装零配件来产生一个新的产品
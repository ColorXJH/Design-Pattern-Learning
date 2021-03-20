package com.master.chapter005;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 开闭原则
 * @date 2021/2/23 13:52
 */
public class OpenClosedPrinciple1 {
    public static void main(String[] args) {
        //画图形
        Shape shape1=new Shape1();
        Shape shape2=new Shape2();
        GraphicEditor graphicEditor=new GraphicEditor();
        graphicEditor.drowShape(shape1);
        graphicEditor.drowShape(shape2);

    }
}
//开闭原则基本介绍
//1: 开闭原则是编程中最基础，最重要的设计原则
//2：一个软件实体如类，模块和函数应该对扩展开放（对提供方），对修改关闭（对使用方），用抽象构建框架，用实现扩展细节
//3：当软件需要变化时，尽量通过扩展软件实体的行为来实现变化，而不是通过修改已有的代码来实现变化
//4：编程中遵循其他原则，以及使用设计模式的目的就是遵循开闭原则


//来看一下画图型的功能实现

class GraphicEditor{
    //这里修改了原来的代码，并不友好 （使用方-对修改关闭）
    public void drowShape(Shape s){
        if(s.m_type==1){
            drawShape1(s);
        }else if(s.m_type==2){
            drawShape2(s);
        }
    }

    public void drawShape1(Shape s){
      System.out.println("画图形1");
    }

    public void drawShape2(Shape s){
        System.out.println("画图形2");
    }
}
//图形类-基类
class Shape{
    int m_type;//默认初始化为0
}
//图形1 （提供方-对扩展开放）
class Shape1 extends  Shape{
    Shape1 (){
        super.m_type=1;
    }
}
//图形2 （提供放-对扩展开放）
class Shape2 extends  Shape{
    Shape2 (){
        super.m_type=2;
    }
}

//方式1的优缺点
//1：优点是比较好理解，简单易操作
//2：缺点是违反了设计模式的ocp原则，即对扩展开放，对修改关闭，即当我们给类增加新的功能时
     //尽量不要修改代码，或者尽可能少修改代码
//3；比如我们这时要新增加一个新的图形种类，我们需要做如下修改，修改的地方较多
     //需要修改原来已经写好的代码，这种修改为原来的程序带来了不稳定性


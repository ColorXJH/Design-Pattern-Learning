package com.master.chapter005;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 开闭原则优化
 * @date 2021/2/23 17:44
 */
public class OpenClosedPrinciple2 {
    public static void main(String[] args) {
        GraphicEditorA graphicEditorA=new GraphicEditorA();
        ShapeA shape1=new ShapeA1();
        ShapeA shape2=new ShapeA2();
        graphicEditorA.drawShape(shape1);
        graphicEditorA.drawShape(shape2);
    }
}

class GraphicEditorA{
    public void drawShape(ShapeA s){
            s.draw();
    }
}

//shapea抽象类
abstract  class ShapeA{
    int m_type;
    public abstract  void draw();//抽象方法
}


class ShapeA1 extends ShapeA{
    ShapeA1(){
        super.m_type=1;
    }
    @Override
    public void draw() {
        System.out.println("shape1 drawing");
    }
}

class ShapeA2 extends ShapeA{
    ShapeA2(){
        super.m_type=2;
    }
    @Override
    public void draw() {
        System.out.println("shape2 drawing");
    }
}


//
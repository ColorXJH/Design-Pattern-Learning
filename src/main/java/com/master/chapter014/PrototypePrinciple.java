package com.master.chapter014;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 原型模式
 * @date 2021/2/27 11:46
 */
public class
PrototypePrinciple {
    public static void main(String[] args) {
        //传统方法
        List<sheep>list=new ArrayList<>();
        for(int i=0;i<10;i++){
            sheep sheep=new sheep("tom",1,"white");
            list.add(sheep);
        }
        list.stream().forEach(i->{
            System.out.println(i.toString());
        });

        //原型模式(默认方法)
        sheep2 sheep2=new sheep2("tom2",1,"white");;
        sheep2 sheep3=(sheep2)sheep2.clone();
        System.out.println(sheep3.toString());

        //原型模式深拷贝-重写clone方法
        System.out.println("深拷贝克隆开始--------------");
        Sheep3 sheep31=new Sheep3("tom",3,"black");
        Sheep3 friend=new Sheep3("jerry",3,"pink");
        sheep31.setFriend(friend);
        Sheep3 clone=(Sheep3)sheep31.clone();
        friend.setAge(100);
        sheep31.setFriend(friend);
        System.out.println(friend.toString());
        System.out.println(clone.getFriend().toString());
        System.out.println("深拷贝克隆结束--------------");


        //深拷贝--对象序列化
        Sheep4 sheep4=new Sheep4("tom",4,"black");
        Sheep4 friend4=new Sheep4("jerry",4,"white");
        sheep4.setFriend(friend4);
        Sheep4 copys=(Sheep4)sheep4.deepCloneBySeriable();
        friend4.setAge(1000);
        sheep4.setFriend(friend4);
        System.out.println(sheep4.getFriend().toString());
        System.out.println(copys.getFriend().toString());
    }

}
//原型模式引出问题：克隆羊问题
//现有一只羊tom,姓名为tom,年龄为1，颜色为白色，请编程来创建10只和tom属性完全相同的羊


//1：传统方法解决：

class sheep{
    private String name;
    private int age;
    private String color;
    public sheep(String name,int age,String color){
         this.name=name;
         this.age=age;
         this.color=color;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "sheep{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }
}


//传统方式的优缺点
//1：优点：比较好理解，简单易操作
//2：缺点：在创建新的对象时，总是需要重新获取原始对象的属性，如果创建的对象比较复杂时，效率较低
    //总是需要重新初始化对象，而不是动态的获取对象运行时的状态，不够灵活
//3：改进思路
//java中Object类是所有类的基类，object类提供了一个clone方法，该方法可以将一个java对象复制一份，
//但是需要实现clone的java类必须要实现一个接口Cloneable,该接口表示该类能够复制且具有复制的能力=》原型模式


//原型模式基本介绍
//1：原型模式是指：用原型实例指定创建对象的种类，并且通过拷贝这些原型，创建新的对象
//2：原型模式是一种创建型设计模式，允许一个对象再创建另外一个可定制的对象，无需知道创建的细节
//3：工作原理是：通过将一个原型对象传递给那个需要发动创建的对象，这个要发动创建的对象通过请求原型对象拷贝他们自己来实施创建，即 对象.clone()
//4：形象的理解，孙大圣拔出猴毛，变出其他孙大圣

class sheep2 implements  Cloneable{
    private String name;
    private int age;
    private String color;
    public sheep2(String name,int age,String color){
        this.name=name;
        this.age=age;
        this.color=color;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "sheep{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }
    //重写clone方法，使用默认的克隆方法
    @Override
    protected Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }

    }
}


//原型模式在spring框架中的使用
//spring中bean的创建，用的就是原型模式
//beans.xml  <bean id="id001" class="com.master.Monster" scope="protetype"/>
class Test{
    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
        //这里getBean方法内部使用了原型模式
        Object bean=context.getBean("id001");
        System.out.println("bean is "+bean);
    }

}


//原型模式-深拷贝
//讨论深拷贝和浅拷贝
//浅拷贝
//1：对于数据类型是基本数据类的成员变量，浅拷贝会直接进行值传递，也就是将该属性值复制一份给新的对象
//2：对于数据类型是引用数据类型的成员变量，比如说成员变量是某个数组，某个类的对象等，那么浅拷贝会进行引用传递
    //也就是只是将该成员变量的引用值（内存地址），复制一份给新的对象，因为实际上两个对象的成员变量都指向同一个实例
    //在这种情况下，在一个对象中修改成员变量会影响到另一个对象中成员变量的值
//3：前面我们的克隆羊就是浅拷贝
//4：浅拷贝是使用默认的克隆方法来实现：sheep=(Sheep)super.clone();

//深拷贝
//1：复制对象的所有基本数据类型的成员变量值
//2：为所有引用数据类型的成员变量申请存储空间，并复制每个引用数据类型成员变量所引用的对象，直到该对象可达的所有对象
    //也就是说：对象进行深拷贝要对整个对象进行拷贝
//3：深拷贝实现方式1：重写clone方法来实现深拷贝
                //2：通过对象序列化来实现深拷贝




//1：重写clone方法
class Sheep3 implements Cloneable{
    private String name;
    private int age;
    private String color;
    private Sheep3 friend;//对象的引用
    public Sheep3(String name,int age,String color){
        super();
        this.name=name;
        this.age=age;
        this.color=color;
    }

    public Sheep3 getFriend() {
        return friend;
    }

    public void setFriend(Sheep3 friend) {
        this.friend = friend;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Sheep3{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                ", friend=" + friend +
                '}';
    }

    @Override
    protected Object clone() {
        Sheep3 sheep3=null;
        try {
            sheep3=(Sheep3) super.clone();
            String name=sheep3.getFriend().getName();
            int age=sheep3.getFriend().getAge();
            String color=sheep3.getFriend().getColor();
            sheep3.friend=new Sheep3(name,age,color);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            sheep3=null;
        }
        return sheep3;
    }
}


//2 通过对象序列化实现深拷贝
class Sheep4 implements Serializable {
    private String name;
    private int age;
    private String color;
    private Sheep4 friend;//对象的引用
    public Sheep4(String name,int age,String color){
        super();
        this.name=name;
        this.age=age;
        this.color=color;
    }

    public Sheep4 getFriend() {
        return friend;
    }

    public void setFriend(Sheep4 friend) {
        this.friend = friend;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Sheep3{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                ", friend=" + friend +
                '}';
    }

    //通过序列化实现克隆
    public Object deepCloneBySeriable(){
        ByteArrayOutputStream byteArrayOutputStream=null;
        ObjectOutputStream objectOutputStream=null;
        ByteArrayInputStream byteArrayInputStream=null;
        ObjectInputStream objectInputStream=null;

        try {
            //序列化
            byteArrayOutputStream=new ByteArrayOutputStream();
            objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
            //objectOutputStream以对象流的方式写出去
            objectOutputStream.writeObject(this);//当前这个对象以对象流的方式输出
            //反序列化
            //将输出流输出后又反序列化把它重新读入==》将对象克隆
            byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            objectInputStream=new ObjectInputStream(byteArrayInputStream);
            // objectInputStream以对象流的方式读回来
            Sheep4 copy=(Sheep4)objectInputStream.readObject();
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                byteArrayOutputStream.close();
                objectOutputStream.close();
                byteArrayInputStream.close();
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

    }
}


//原型模式注意事项和细节
//1：创建新的对象比较复杂时，可以利用原型模式简化对象的创建过程，同时也能提高效率
//2：不用重新初始化对象，而是动态的获得对象运行时的状态
//3：如果原始对象发生变化（增大或者减少属性），其他克隆对象也会发生相应变化无需修改代码
//4：在实现深度克隆的时候可能需要比较复杂的代码
//5：缺点：需要为每一个类配备一个克隆方法，这对全新的类来说并不是很难，但对已有的类进行改造时，
    //需要修改其源代码，违背了ocp原则，这点需要注意


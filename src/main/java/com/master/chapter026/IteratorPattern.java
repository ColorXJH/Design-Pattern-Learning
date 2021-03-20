package com.master.chapter026;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 迭代器模式
 * @date 2021/3/14 22:03
 */
public class IteratorPattern {
    public static void main(String[] args) {
        //迭代器模式

        //创建学院
        List<College> collegeList=new ArrayList<>();
        collegeList.add(new ComputerCollege());
        collegeList.add(new InfoCollege());
        OutputImpl output=new OutputImpl(collegeList);
        output.printCollege();

        //迭代器模式在jdk源码的使用：ArrayList
        ArrayList list=null;
    }
}

//具体需求：编写一个程序展示一个学校院系结构，需求是这样，要在一个页面中展示出学校的院系组成，
    //一个学校有多少个学院，一个学院有多少个系

//传统方式的问题分析
    //1：将学院看作是学校的子类，系是学院的子类，这样实际上是站在组织的大小上来进行分层的
    //2：实际上我们的需求是：在一个页面展示学校的院系组成，一个学校有多个学院，一个学院有多个系，
        //因此这种方式，不能很好的实现遍历操作
    //解决方式：迭代器模式

//迭代器模式：基本介绍
    //1:iterator-pattern是常用的设计模式，属于行为型模式，
    //2：如果我们的集合元素是用不同的方式实现的，有数组，还有java的集合类，或者还有其他方式，当客户端要遍历
        //这些集合元素时，就要使用多种遍历方式，而且还会暴露元素的内部结构，可以考虑使用迭代器模式解决
    //3：迭代器模式，提供一种遍历集合元素的统一接口，用一致的方法遍历集合元素，不需要知道集合对象的底层表示，即：不暴露其内部的结构

//类图角色说明
    //1：iterator:迭代器接口，是系统提供，含，hasNext,next,remove方法
    //2：concreteIterator:具体的迭代器类，管理迭代
    //3：aggregate:是一个统一的聚合接口，将客户端和具体的聚合解耦，
    //4：concreteAggregate:具体的聚合，持有对象的集合，并提供一个方法，返回一个迭代器
        //该迭代器能正确遍历集合
    //5：client:客户端，通过Iterator,Aggregate依赖子类

//代码如下

//系
class Department{
    private String name;
    private String desc;

    public Department(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


//各个学院迭代器
class ComputerCollegeIterator implements Iterator{
    /*这里我们需要知道Department，是以怎样的方式存放*/
    private Department[] departments;
    int position=0;//遍历的位置

    public ComputerCollegeIterator(Department[] departments){
        this.departments=departments;
    }

    @Override
    public boolean hasNext() {
        if(departments[position]==null||position>=departments.length){
            return false;
        }else{
            return true;
        }

    }

    @Override
    public Object next() {
        Department department=departments[position];
        position++;
        return department;
    }

    /*删除方法暂时不需要，默认空实现*/
    @Override
    public void remove() {

    }
}


class  InfoCollegeIterator implements  Iterator{
    private List<Department>list=new ArrayList<>();
    private int index=-1;//索引
    public InfoCollegeIterator(List<Department>list){
        this.list=list;
    }
    @Override
    public boolean hasNext() {
        /*可以借助ArrayList的现成实现*/
        //return list.iterator().hasNext();
        if(index>=list.size()-1){
            return false;
        }else{
            index++;
            return true;
        }
    }

    @Override
    public Object next() {
        /*可以借助ArrayList的现成实现*/
        //return list.iterator().next();
        return list.get(index);
    }

    /*删除方法暂时不需要，默认空实现*/
    @Override
    public void remove() {

    }
}

//是一个统一的聚合接口，将客户端和具体的聚合解耦，
interface College{
    /*返回创建的迭代器接口*/
    Iterator createIterator();

    String getName();
    /*增加系的方法*/
    void addDepartment(String name,String desc);
}

//各个学院
class ComputerCollege implements College{
    Department[] departments;
    int numberOfDepartments=0;//保存当前数组的对象个数
    public ComputerCollege(){
        departments=new Department[5];
        addDepartment("java","java");
        addDepartment("php","php");
        addDepartment("big","big");
        addDepartment("go","go");

    }

    @Override
    public Iterator createIterator() {
        return new ComputerCollegeIterator(departments);
    }

    @Override
    public String getName() {
        return "计算机学院";
    }

    @Override
    public void addDepartment(String name, String desc) {
        Department de=new Department(name,desc);
        departments[numberOfDepartments]=de;
        numberOfDepartments++;

    }
}


class InfoCollege implements College{
    List<Department>list;
    public InfoCollege(){
        list=new ArrayList<>();
        addDepartment("c++","c++");
        addDepartment("c","c");
        addDepartment("golang","golang++");
    }
    @Override
    public Iterator createIterator() {
        return new InfoCollegeIterator(list);
    }

    @Override
    public String getName() {
        return "信息过程学院";
    }

    @Override
    public void addDepartment(String name, String desc) {
        Department de=new Department(name,desc);
        list.add(de);
    }
}

//输出类
class OutputImpl{
    //学院的集合
    List<College>collegeList;
    public OutputImpl(List<College>collegeList){
        this.collegeList=collegeList;
    }
    //输出
        //遍历所有学院，然后调用printDepartment 输出各个学院的系

    public void printCollege(){
        //从collegeList取出所有学院
        Iterator<College> iterator=collegeList.iterator();
        while(iterator.hasNext()){
            College cl=iterator.next();
            System.out.println(cl.getName());
            /*传入学院下系的迭代器*/
            printDepartment(cl.createIterator());
        }
    }


        //学院输出系
    public void printDepartment(Iterator iterator){
        while(iterator.hasNext()){
           Department de= (Department)iterator.next();
           System.out.println(de.getName());
        }
    }
}


//迭代器模式在jdk源码的使用：ArrayList
    //内部类Itr:充当实现迭代器Iterator的类，作为ArrayList的内部类
    //List:充当了聚合接口，含有一个iterator()方法，返回一个迭代器对象
    //ArrayList:是实现聚合接口List的子类，实现了iterator()
    //Iterator:系统提供的接口
//迭代器模式解决了不同集合统一遍历的问题


//迭代器模式注意事项和细节
    //优点：提供一个统一的方法遍历对象，客户不用再考虑聚合的类型，使用一种方法就可以遍历对象了
    //隐藏了聚合的内部结构，客户端要遍历聚合的时候只能取到迭代器，而不会知道聚合的具体组成
    //提供了一种设计思想，就是一个类应该只有一个引起变化的原因（叫做单一职责原则），在聚合类中，我们
        //把迭代器分开，就是要把管理对象集合的遍历对象集合的责任分开，这样一来，集合改变的话，只会
        //影响到聚合对象，而如果遍历方式改变的话，之影响到了迭代器
    //当要展示一组相似对象，或者遍历一组相同对象时使用，适合使用迭代器模式

    //缺点：每个聚合对象都要一个迭代器，会生成多个迭代器不好管理


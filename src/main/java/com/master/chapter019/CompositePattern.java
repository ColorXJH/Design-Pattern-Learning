package com.master.chapter019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 组合模式
 * @date 2021/3/9 10:50
 */
public class CompositePattern {
    public static void main(String[] args) {
        //从大到小创建对象

        //1:创建学校 university
        OrganizationComponent university=new University("清华大学","中国顶级大学");

        //2：创建学院 college
        OrganizationComponent jsj=new College("计算机学院","计算机学院");
        OrganizationComponent xx=new College("信息工程学院","信息工程学院");


        //3：创建学院下面的系
        jsj.add(new Department("软件工程","软件工程good"));
        jsj.add(new Department("网络工程","网络工程good"));
        jsj.add(new Department("计算机科学与技术","计算机科学与技术"));

        xx.add(new Department("通信工程","通信工程good"));
        xx.add(new Department("信息工程","信息工程good"));

        //4：将学院加入大学
        university.add(jsj);
        university.add(xx);
        /*打印*/
        university.print();

        //jdk 源码 --组合模式
        Map<Integer,Object>map=new HashMap<>();
        map.put(0,"东游记");//直接存放叶子节点
        Map<Integer,String>submap=new HashMap<>();
        submap.put(0,"红楼梦");
        submap.put(1,"西游记");
        submap.put(2,"水浒传");
        submap.put(3,"三国演义");
        map.put(1,submap);
        System.out.println(map);
        //
        System.out.println("---------------------------");
        map.clear();
        map.putAll(submap);
        System.out.println(map);
        //Map 是一个抽象构建 ，类似于Component
        //HashMap继承了AbstractMap和实现了Map，  HashMap相当于Composite,实现（继承）了相关方法put putAll
        //AbstractMap相当于中间缓冲层， Map是顶级接口，相当于Component
        //叶子节点leaf是HashMap中的静态内部类Node
    }
}

//学校院系展示需求：编写程序展示一个学校院系结构：要在一个页面中展示出学校的院系组成，一个学校有多个学院，一个学院有多个系
//1：传统方案：学校《==学院《==系（继承关系）
    //问题：他么你彼此间真的是继承关系吗，更合理的应该是学校包含学院，学院包含系（组合关系）
    //1：传统方案实际上是站在组织大小来进行分层的，实际上我们的需求是：展示学校的院系组成，这种方案
        //不能很好实现管理操作，比如对学院/系得添加，删除，遍历等
    //解决方案：把学校，院，系都看作是组织结构，他们之间没有继承关系，而是一个树形结构，可以更好得实现管理操作==》组合模式



//组合模式
//基本介绍：
    //1：组合模式又叫部分整体模式，它创建了对象组的树形结构，将对象组合成树形结构以表示整体-部分的分层关系
    //2：组合模式依据树形结构来组合对象，用来表示部分以及整体层次
    //3：这种类型的设计模式属于结构型模式
    //4：组合模式使得用户对单个对象和组合对象的访问具有一致性，即：组合能让客户以一致性的方式处理个别对象及组合对象

//组合模式解决的问题
//1：组合模式解决这样的问题，当我们的要处理的对象可以生成一颗树形结构，而我们要对树上的节点和叶子进行操作时，它能够提供一致的方式
    //而不用考虑他是节点还是叶子
//2：对应示意图
/*              o(root)
*
*   o(node)        o(node)         0(node)
*
* 0   0   0         0            0   0   0  (leaf)
* */

//组合模式解决学校院系展示
//：类图


//代码如下
/*组合中对象声明接口，在适当情况下，实现所有类共有的接口默认行为
* 用于访问和管理Component子部件，口语i是抽象类或者接口
* */
//component为所有对象定义一个类，不管是叶子还是节点
interface Componnent{
    void opration();
    void add(Componnent componnent);
    void remove(Componnent componnent);
    Componnent getChild(int a);
}

/*表示叶子节点--没有子节点*/
//叶子没有孩子，不能操作孩子，但其定义组合内元素的行为
class Leaf implements Componnent{

    @Override
    public void opration() {

    }

    @Override
    public void add(Componnent componnent) {

    }

    @Override
    public void remove(Componnent componnent) {

    }

    @Override
    public Componnent getChild(int a) {
        return null;
    }
}


/*非叶子节点，用于存储子部件，在component接口中实现子部件的相关操作*/
//组合可以操作子节点和叶子，但可能不具有叶子的某些行为
class Composite implements Componnent{

    @Override
    public void opration() {

    }

    @Override
    public void add(Componnent componnent) {

    }

    @Override
    public void remove(Componnent componnent) {

    }

    @Override
    public Componnent getChild(int a) {
        return null;
    }
}








//学校-组织-系 代码实现
/*组织抽象类 就是 Component*/
abstract  class OrganizationComponent{
    private String name;//名字
    private String des;//描述

    /*构造函数*/
    public OrganizationComponent(String name,String des){
        super();
        this.name=name;
        this.des=des;
    }

    /*setter/getter*/
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }

    /*新增*/
    protected void add(OrganizationComponent organizationComponent){
        //默认实现（不要写成抽象的，因为叶子节点不需要该方法，所以不需要重写该方法：写成默认实现更适合）
        throw new UnsupportedOperationException();
    }

    /*删除*/
    protected  void remove(OrganizationComponent organizationComponent){
        //默认实现（不要写成抽象的，因为叶子节点不需要该方法，所以不需要重写该方法：写成默认实现更适合）
        throw new UnsupportedOperationException();
    }

    /*打印方法，子类需要实现*/
    protected  abstract  void print();


}
//大学
/*University 就是Composite,可以管理College*/
class University extends OrganizationComponent{
    /*聚合Component*/
    List<OrganizationComponent>list=new ArrayList<>();

    /*构造器*/
    public University(String name, String des) {
        super(name, des);
    }

    //print 就是输出 University里面包含的学院（College）
    @Override
    protected void print() {
        System.out.println("---------"+getName()+"-----------");
        /*递归调用*/
        list.stream().forEach(i->{
            i.print();
        });
    }

    /*重写部分方法*/

    @Override
    protected void add(OrganizationComponent organizationComponent) {
        list.add(organizationComponent);
    }

    @Override
    protected void remove(OrganizationComponent organizationComponent) {
        list.remove(organizationComponent);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDes() {
        return super.getDes();
    }
}


//学院
/*College 就是Composite,可以管理Department*/
class College extends  OrganizationComponent{
    List<OrganizationComponent>list=new ArrayList<>();
    public College(String name, String des) {
        super(name, des);
    }

    @Override
    protected void print() {
        System.out.println("-------------"+getName()+"-------------");
        list.stream().forEach(i->{
            /*递归调用*/
            i.print();
        });
    }


    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDes() {
        return super.getDes();
    }

    @Override
    protected void add(OrganizationComponent organizationComponent) {
        list.add(organizationComponent);
    }

    @Override
    protected void remove(OrganizationComponent organizationComponent) {
        list.remove(organizationComponent);
    }
}


//系
/*Department 就是leaf */
class Department extends  OrganizationComponent{

    public Department(String name, String des) {
        super(name, des);
    }

    @Override
    protected void print() {
            System.out.println("----------"+getName()+"---------");
    }

    /*add/remove 就不用写了,因为他是叶子节点，它不需要管理其他单位*/

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDes() {
        return super.getDes();
    }
}

//jdk 组合模式源码分析，见main方法

//组合模式使用细节和注意事项
//1：简化客户端操作，客户端只需要面对一致的对象而不用靠考虑整体/部分 或者节点/叶子的问题
//2：具有较强的扩展性，当我们需要更改组合对象时，我们只需要调整内部的层次关系，客户端不用做出任何改动
//3：方便创建出复杂的层次结构，客户端不用理会组合里面的组成细节，容易添加节点或者叶子，从而创建出复杂的树形结构
//4：需要遍历组织机构，或者处理的对象，具有树形结构，非常适合使用组合模式
//5：要求较高的抽象性，如果节点和叶子有很多差异的话，比如很多方法和属性都不一样，不适合使用组合模式



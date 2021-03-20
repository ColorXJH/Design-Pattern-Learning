package com.master.chapter006;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 迪米特法则
 * @date 2021/2/23 19:53
 */
public class DemeterPrinciple1 {
    public static void main(String[] args) {
        SchoolManager sm=new SchoolManager();
        sm.printAllEmployee(new ColleageManager());

    }
}

//基本介绍
//1：一个对象应该对其他对象保持最少了解
//2：类与类关系越密切，耦合度越大
//3：迪米特法则又叫最少知道原则，即一个类对自己依赖的类知道的越少越好，也就是说，对于被依赖的类
    //不管多么复杂，都尽量将逻辑封装在类的内部，对外除了提供的public方法，不对外泄露任何信息
//4：迪米特法则还有个更简单的定义：只与直接的朋友通信
//5：直接的朋友：每个对象都会与其他对象有耦合关系，只要两个对象之间有耦合关系，我们就说这两个
    //对象之间是朋友关系，耦合的方式有很多，依赖，关联，组合，聚合等，其中我们称出现成员变量、
    //方法参数、方法返回值中的类为直接朋友，而出现在局部变量中的类不是直接朋友，也就是说，陌生的类
    //最好不要以局部变量的形式出现在类的内部

//学校总部员工
class Employee{
    private String id;
    public void setId(String id){
      this.id=id;
    }
    public String getId(){
        return id;
    }
}


//学院员工
class ColleageEmployee{
    private String id;
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
}
//管理学院员工的一个类
class ColleageManager{
        public List<ColleageEmployee> getAllEmployee(){
        List<ColleageEmployee> list=new ArrayList<ColleageEmployee>();
        for(int i=0;i<20;i++){
            ColleageEmployee colleageEmployee=new ColleageEmployee();
            colleageEmployee.setId("学院员工id= "+i);
            list.add(colleageEmployee);
        }
        return list;
        }
}

//管理学校总部的类
//它的直接朋友有哪些？ Employee，ColleageManager
//ColleageEmployee不是它的直接朋友（以局部变量的形式出现在类的内部），违背了迪米特法则
//改进版见类2
class SchoolManager{
    public List<Employee>getAllEmployee(){
        List<Employee> list=new ArrayList<>();
        for(int i=0;i<20;i++){
            Employee employee=new Employee();
            employee.setId("学校员工id="+i);
            list.add(employee);
        }
        return list;
    }

    void printAllEmployee(ColleageManager cm){
        List<Employee> list1=this.getAllEmployee();
        List<ColleageEmployee> list2=cm.getAllEmployee();
        System.out.println("学校员工id如下：");
        for(Employee e:list1){
            System.out.println(e.getId());
        }
        System.out.println("学院员工id如下：");
        for(ColleageEmployee e:list2){
            System.out.println(e.getId());
        }
    }
}
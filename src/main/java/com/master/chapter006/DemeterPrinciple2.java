package com.master.chapter006;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 迪米特法则改进版
 * @date 2021/2/23 20:47
 */
public class DemeterPrinciple2 {
    public static void main(String[] args) {
        SchoolManager1 sm=new SchoolManager1();
        sm.printAllEmployee(new ColleageManager1());
    }
}
//改进思路
//1：前面设计的问题在于SchoolManager中，ColleageEmployee类并不是SchoolManager类的直接朋友
    //直接朋友：类成员变量 方法参数  方法返回值
//2：按照迪米特法则，应该避免类中出现这样的非直接朋友关系的耦合
//3：对代码按照迪米特法则进行改进如下：

//学校总部员工
class Employee1{
    private String id;
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
}


//学院员工
class ColleageEmployee1{
    private String id;
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }
}
//管理学院员工的一个类
class ColleageManager1{
    public List<ColleageEmployee1> getAllEmployee(){
        List<ColleageEmployee1> list=new ArrayList<ColleageEmployee1>();
        for(int i=0;i<20;i++){
            ColleageEmployee1 colleageEmployee=new ColleageEmployee1();
            colleageEmployee.setId("学院员工id= "+i);
            list.add(colleageEmployee);
        }
        return list;
    }
    /*新增加的代码，将非直接朋友关系的类放入到这里，解除耦合*/
    public void printAllColleageEmployee1(){
        List<ColleageEmployee1>list=this.getAllEmployee();
        System.out.println("学院员工id如下：");
        for(ColleageEmployee1 e:list){
            System.out.println(e.getId());
        }
    }

}

//管理学校总部的类
//它的直接朋友有哪些？ Employee，ColleageManager
//ColleageEmployee不是它的直接朋友（以局部变量的形式出现在类的内部），违背了迪米特法则
//改进版见类2
class SchoolManager1{
    public List<Employee1>getAllEmployee(){
        List<Employee1> list=new ArrayList<>();
        for(int i=0;i<20;i++){
            Employee1 employee=new Employee1();
            employee.setId("学校员工id="+i);
            list.add(employee);
        }
        return list;
    }

    void printAllEmployee(ColleageManager1 cm){

        //将下面注释的一段代码放入ColleageManager1类中，解除局部类耦合
        /*List<ColleageEmployee1> list2=cm.getAllEmployee();
        System.out.println("学院员工id如下：");
        for(ColleageEmployee1 e:list2){
            System.out.println(e.getId());
        }*/
        //修改后的方法，解除非直接朋友耦合
        cm.printAllColleageEmployee1();
        List<Employee1> list1=this.getAllEmployee();
        System.out.println("学校员工id如下：");
        for(Employee1 e:list1){
            System.out.println(e.getId());
        }

    }
}

//迪米特法则注意事项和细节
//1：迪米特法则的核心是降低类之间的耦合
//2：但是注意：由于每个类都减少了不必要的依赖，因此迪米特法则只是要求降低类之间（对象间）
    //耦合关系，并不是要求完全没有依赖关系
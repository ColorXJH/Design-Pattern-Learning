package com.master.chapter008;

/**
 * @author ColorXJH
 * @version 1.0
 * @description UML类图介绍说明
 * @date 2021/2/24 10:05
 */
public class UML {

}
//UML基本介绍
//1：UML--Unified Modeling Language 统一建模语言，是一种用于软件系统分析和设计的语言工具，
    //它用于帮助软件开发人员进行思考和记录思路的结果
//2：UML本身是一套符号的规定，就像数学符号和化学符号一样，这些符号用于描述软件模型中各个元素和他们之间
    //的关系，比如类，接口，实现，泛化(继承，扩展)，依赖，组合，聚合等
//3：使用UML来建模，常用的工具有RationalRose(eclipse)  PlantUML插件(ieda)https://plantuml.com/zh/class-diagram

//relation  dependency【虚线双箭头】(依赖--》使用)  associatiob【虚线无箭头】(关联--》一对多，一对一关系)
          //generalization【实线三箭头】(泛化--》继承，扩展)  realization【虚线三箭头】(实现)
          //aggregation【实线四箭头】(聚合--》成员变量set方法传递)
          //composite【实线四箭头黑色填充】(组合--》成员变量（属性），实例化A时，B也直接被new实例化了，耦合关系强于聚合 )


//画uml图与写文章差不多，都是把自己的思想描述给别人看，关键在于思路和条理，uml类图分为
//1：用例图
//2：静态结构图：类图 对象图 包图 组件图，部署图
//3：动态行为图：交互图（时序图与协作图） 状态图 活动图
//说明：
//1：类图是描述类与类之间关系的，是uml图中最核心的
//2：在说明设计模式时，我们必然会使用类图

//类图：用于描述系统中的类（对象）本身的组成和类（对象）之间的各种静态关系
//类之间的关系：依赖 泛化（继承/扩展） 实现 关联 聚合 组合


//1：依赖关系
//只要是在类中用到了对方，那么他们之间就存在依赖关系，如果没有对方，连编译都通过不了
class PersonServiceBean{
    private PersonDao personDao;//类
    public void save(Person person){}
    public void getIdCard(Integer personId){}
    public void modified(){
        Department department=new Department();
    }
}
class PersonDao{}
class IDCard{}
class Person{}
class Department{}


//2：泛化关系
//泛化关系实际上就是继承关系（扩展关系extension）,它是依赖关系的特例
abstract class DaoSupport{
    void save(Object o){}
    void delete(Object o){}
}

class MyDaoSupport extends  DaoSupport{

}
//如果A类继承了B,我们就说A和B存在泛化关系


//3：实现关系
//实现关系就是A类实现B类，他是依赖关系的特例
interface PersonService{
    void delete();
}
class PersonServiceImpl implements  PersonService{

    @Override
    public void delete() {

    }
}

//4：关联关系
//关联关系实际上就是类与类之间的联系，他是依赖关系的特例
//关联具有导航性，即双向关系或单向关系
//关系具有多重性，如“1”（表示有且仅有一个），“0...(表示0个或多个)”，“0，1（表示0个或者1个）”
//“n...m(表示n到m个都可以)”，“m...*(表示至少m个)”

//单向一对一关系
class Person1{
    private IdCard idCard;
}
class IdCard{

}

//双向一对一关系
class Person2{
    private IDCard2 idCard2;
}
class IDCard2{
    private Person2 person2;
}


//5：聚合关系
//聚合关系表示的是整体和部分的关系，整体与部分可以分开，聚合关系是关联关系的特例，所以它有关联的导航性与多重性
//例如：一台电脑由键盘，显示器，鼠标等组成，组成电脑的各个配件是可以从电脑上分离出来的，使用带空心菱形实线来表示
//电脑
class Computer{
    private Mouse mouse;
    private KeyBoard keyBoard;
    public void setMouse(Mouse mouse){
        this.mouse=mouse;
    }
    public void setKeyBoard(KeyBoard keyBoard){
        this.keyBoard=keyBoard;
    }
}
//鼠标
class Mouse{
    private String name;
}

//键盘
class KeyBoard{
    private String name;

}

//6 组合关系
//如果鼠标，键盘是不可控分离的，则升级为组合关系
class Computer2{
    //创建computer时其鼠标键盘就创建了，销毁computer实例时则鼠标键盘也会被销毁，不可分离，则为组合关系
    private Mouse mouse=new Mouse();
    private KeyBoard keyBoard=new KeyBoard();
}
//组合关系也是整体与部分的关系，但整体与部分不可分开
//person与idcard,head,head和person就是组合，idcard和person就是聚合
//如果在程序中person实体中定义了对idcard进行级联删除，即删除person时对idcard一同删除，那么idcard和person就是组合关系了

//总结 依赖 泛化 实现 关联 组合 聚合
    //泛化 实现 关联  是特殊的依赖关系
    //聚合 组合 是特殊的关联关系


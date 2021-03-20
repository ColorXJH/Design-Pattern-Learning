package com.master.chapter029;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 备忘录模式
 * @date 2021/3/16 12:57
 */
public class MementoPattern {
    public static void main(String[] args) {
        Originator originator=new Originator();
        CareTaker careTaker=new CareTaker();
        originator.setState("状态1");
        careTaker.add(originator.saveStateMemento());
        originator.setState("状态2");
        careTaker.add(originator.saveStateMemento());
        originator.setState("状态3");
        careTaker.add(originator.saveStateMemento());
        System.out.println("当前状态----："+originator.getState());
        //希望恢复到状态1
        originator.getStateFromMemento(careTaker.get(0));
        System.out.println("恢复后的状态----："+originator.getState());

        System.out.println("--------------------------------------");
        //创建游戏角色
        GameRole gameRole=new GameRole();
        gameRole.setAttack(100);
        gameRole.setDef(100);
        System.out.println("------大战前状态-------");
        gameRole.display();
        CareTaker1 careTaker1=new CareTaker1();
        careTaker1.setMemento(gameRole.createMemento());
        System.out.println("------大战boss-------");
        gameRole.setAttack(30);
        gameRole.setDef(30);
        System.out.println("------大战后状态-------");
        gameRole.display();
        System.out.println("------大战后使用备忘录对象恢复状态-------");
        gameRole.recoverGameRoleFromMemento(careTaker1.getMemento());
        gameRole.display();

    }
}
//游戏角色状态恢复问题
    //游戏角色有攻击力和防御力，在大战boss前保存自身的状态（攻击力和防御力），当大战boss后攻击力和防御力都下降了
    //从备忘录对象恢复到大战前的状态

//传统方式的问题：
    //1：一个对象，就对应一个保存对象状态的对象，这样当我们游戏的对象很多时，不利于管理，开销也很大，
    //2：传统的方式是简单的做备份，new出另一个对象来，再把需要备份的数据放到新对象中，但这就暴露了对象内部的细节
    //3：解决方案==》备忘录模式


//备忘录模式的基本介绍
    //1:备忘录模式（memento-pattern）在不破坏封装的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态
        //这样以后就可以将该对象恢复到原先保存的状态
    //2：可以这样理解备忘录模式：现实生活中的备忘录是用来记录某些要去做的事情，或者是记录已经达成的共同意见的事情，
        //以防忘记了，而在软件层面，备忘录模式有着相同意义，备忘录对象主要用来记录一个对象的某种状态，或者某些数据，
        //当要做回退时，可以从备忘录对象里获取原来的数据进行恢复操作
    //3：备忘录模式属于行为型模式

//代码如下
/*原始对象*/
class Originator{
    private String state;//状态信息

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //编写一个方法，可以保存一个状态对象Memento
    public Memento saveStateMemento(){
        return new Memento(state);
    }
    //从备忘录中获取原来的状态
    public void getStateFromMemento(Memento memento){
        state=memento.getState();
    }

}

/*备忘录类*/
class Memento{
    private String state;//状态
    public Memento(String state){
        this.state=state;
    }

    public String getState() {
        return state;
    }
}


/*管理备忘录对象*/
class CareTaker{
    //集合中有很多备忘录对象
    private List<Memento>list=new ArrayList<>();

    public void add(Memento memento){
        list.add(memento);
    }
    //获取到第index个originator的备忘录对象（即状态保存）
    public Memento get(int index){
        return list.get(index);
    }
}

//1：originator:原始对象（需要保存状态的对象）
//2：memento:备忘录对象，负责保存好记录，即保存originator的内部状态
//3：careTaker:守护者对象，负责保存多个备忘录对象，使用集合管理，提高效率
//说明：如果希望保存多个originator对象的不同时间的状态，也可以，只需要使用hashMap<String,集合>


//游戏角色实例代码
class Memento1{
    private int attack;
    private int def;

    public Memento1(int attack, int def) {
        this.attack = attack;
        this.def = def;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

}

class CareTaker1{
    /*只保存一次所以选用单一属性，没有用集合*/
    private Memento1 memento;

    public Memento1 getMemento() {
        return memento;
    }

    public void setMemento(Memento1 memento) {
        this.memento = memento;
    }

}

class GameRole{
    private int attack;
    private int def;

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    //创建备忘录
    public Memento1 createMemento(){
        return new Memento1(attack,def);
    }

    //从备忘录对象回复状态
    public void recoverGameRoleFromMemento(Memento1 memento1){
        attack=memento1.getAttack();
        def=memento1.getDef();
    }
    //显示当前游戏角色状态
    public void display(){
        System.out.println("attack is "+attack+"  and def is "+def);
    }

}


//备忘录模式注意事项和细节
    //1：给用户提供了一种可以恢复状态的机制，可以使用户能够比较方便的回到某个历史状态
    //2：实现了信息的封装，使得用户不需要关系状态的保存细节，
    //3：如果类的成员变量过多，势必会占用比较大的内存资源，而且每一次保存都会消耗一定的内存，这个需要注意
    //4：使用场景：1：后悔药，2：游戏存档，3：windows中的ctrl+z,4:浏览器的后退，5：数据库的事务管理
    //5：为了节约内存，备忘录模式可以和原型模式结合使用




package com.master.chapter024;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 命令模式
 * @date 2021/3/14 13:56
 */
public class CommandPattern {
    public static void main(String[] args) {
        //遥控器控制电灯--命令模式

        //创建电灯接收者
        LightReceiver receiver=new LightReceiver();
        //创建电灯相关命令
        LightOnCommand onCommand=new LightOnCommand(receiver);
        LightOffCommand offCommand=new LightOffCommand(receiver);
        //创建遥控器调用者
        ControllerInvoker invoker=new ControllerInvoker();
        //设置电灯开关位置（设置命令）//假设0位置为电灯开关
        invoker.setCommand(0,onCommand,offCommand);
        System.out.println("--------按下电灯能开的按钮---------");
        invoker.onButtonWasPushed(0);
        System.out.println("--------按下电灯关的按钮---------");
        invoker.offButtonWasPushed(0);
        System.out.println("------撤销上一步操作------");
        invoker.undoButtonWasPushed();

        //扩展性很强，如果需要增加电视机开关，则只需要扩展命令接口，实现命令相应方法，然后设置相应命令位置即可
        //符合开闭原则，对于扩展开放，对调用者修改关闭


        //jdbcTemplate命令模式 --query（）方法
        JdbcTemplate template=null;


    }
}

//智能生活项目需求：具体需求如下：
    //1：我们买了一套智能家电器，有照明灯，风扇，冰箱。洗衣机，我们只要在手机上安装app就可以空着这些家电工作
    //2：这些智能家电来自不同的厂家，我们不想针对每一个厂家都安装一个app,分别控制，我们希望只安装一个app就可以控制全部智能家电
    //3：要实现一个app控制所有智能家电的需求，则每个智能家电厂家都需要提供统一的接口给app调用，这时就可以考虑使用命令模式
    //4：命令模式可以将”动作的请求者“从”动作的执行者“对象中解耦出来
    //5：在我们的例子中，动作的请求者是手机app,动作的执行者是每一个厂商的每一个家电


//命令模式基本介绍
    //1：在软件设计中，我们经常需要向某些对象发送请求，但是并不知道请求的接收者是谁，也不知道被请求的操作是哪个
        //我们只需要在程序运行时指定具体的请求接收者即可，此时可以使用命令模式来进行设计
    //2：命令模式使得请求发送者与请求接收者消除彼此之间的耦合，让对象之间的调用关系更加灵活，实现解耦
    //3：在命令模式中，会将一个请求封装为一个对象，以便使用不同的参数来表示不同的请求（即命令），同时命令模式也支持可撤销的操作
    //4：通俗易懂的理解是：将军发布命令，士兵去执行，其中有几个角色，将军（命令发布者），士兵（命令执行者），命令（连接将军和士兵）
        //invoker是调用者（将军），Receiver是被调用者（士兵），MyCommond是命令，实现了Command接口，持有接收对象

        //invoker:调用者角色
        //Command:是命令角色，需要执行的所有命令都在这里，可以是接口或者抽象类
        //receiver:接收者角色，知道如何实施和执行一个请求相关的操作
        //ConcreteCommand:将一个接收者对象与一个动作绑定。调用接收者相应的操作，实现execute


//代码如下
/*命令接口*/
interface Command{
    void execute();
    void undo();
}
/*电灯接收者*/
class LightReceiver{
    public void on(){
        System.out.println("电灯打开了");
    }
    public void off(){
        System.out.println("电灯关闭了");
    }
}
/*电灯打开命令*/
class LightOnCommand implements Command{
    /*聚合Receiver*/
    LightReceiver light;
    public LightOnCommand(LightReceiver light){
        super();
        this.light=light;
    }

    @Override
    public void execute() {
        //调用接收者方法
        light.on();
    }

    @Override
    public void undo() {
        //调用接收者方法
        light.off();
    }
}
/*电灯关闭命令*/
class LightOffCommand implements Command{
    LightReceiver light;
    public LightOffCommand(LightReceiver light){
        super();
        this.light=light;
    }
    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}
/*空命令（不执行任何操作）*/
/*当调用空命令时，对象什么都不做，这种设计模式可以省略掉对空的应用*/
class NoCommand implements Command{

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}

/*遥控器：调用者*/
class ControllerInvoker{
    //开/关按钮的命令组
    Command[] onCommands;
    Command[] offCommands;

    /*执行撤销命令*/
    Command undoCommand;

    //构造器完成对按钮的初始化
    public ControllerInvoker(){
        onCommands=new Command[5];
        offCommands=new Command[5];
        for(int i=0;i<5;i++){
            onCommands[i]=new NoCommand();
            offCommands[i]=new NoCommand();
        }
    }

    //给按钮设置你需要的命令即可
    public void setCommand(int number,Command oncommand,Command offcommand){
        onCommands[number]=oncommand;
        offCommands[number]=offcommand;
    }

    //按下开的按钮，
    public void onButtonWasPushed(int number){
            //找到你按下的开的按钮，并调用对应方法
            onCommands[number].execute();
            //记录这次操作，用于撤销
            undoCommand=onCommands[number];
    }

    //按下关的按钮，
    public void offButtonWasPushed(int number){
        //找到你按下的关的按钮，并调用对应方法
        offCommands[number].execute();
        //记录这次操作，用于撤销
        undoCommand=offCommands[number];
    }

    //按下撤销按钮
    public void undoButtonWasPushed(){
        //按照目前来看，只能撤销一次
        undoCommand.undo();
    }

    //命令模式在spring框架jdbcTemplate应用的源码分析
    //见main 方法
        //StatementCallBack接口，类似命令接口Command
        //内部类：QueryStatementCallback，实现了命令接口，同时充当了命令接收者的角色
        //命令调用者：是jdbcTemplate, 其中的execute方法接受的就是一个实现了StatementCallBack接口的对象
                //并且调用了该对象的doInStatement方法
        //另外实现StatementCallBack命令接口的子类除了QueryStatementCallback还有，BatchUpdateStatementCallback
            //ExecuteStatementCallback....他们都是匿名内部类，在类中实现了命令接口



    //命令模式使用细节和注意事项
        //1：将发起请求的对象和执行请求的对象解耦，发起请求的对象是调用者，调用者只需要调用命令d对象的execute()方法就可以让接收者工作
            //而不必知道具体的接收者对象是谁，是如何实现的，命令对象会负责让接收者执行请求的动作，也就是说：请求者发起和请求者执行
            //之间的解耦是通过命令对象来实现的，
        //2：容易设计一个命令队列，，只要把命令放在队列中，就可以多线程的执行命令
        //3：容易实现对请求的撤销和重做
        //4：命令模式的不足：可能导致某些系统有过多的具体命令类，增加了系统的复杂度
        //5：空命令也是一种设计模式，为我们省去了空的判断
        //6：应用场景： 界面的一个按钮都是一条命令，模拟cmd,(dos命令)，订单的回复撤销，触发-反馈机制

}
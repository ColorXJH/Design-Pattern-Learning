package com.master.chapter031;

import java.util.Random;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 状态模式
 * @date 2021/3/19 15:41
 */
public class StatePattern {
    public static void main(String[] args) {
        //创建活动对象，奖品池有5个奖品
        RaffleActivity activity=new RaffleActivity(5);
        //连续抽奖三次
        for(int i=0;i<30;i++){
            System.out.println("------第"+(i+1)+"次抽奖-------");
            //扣除积分
            activity.deductMoney();
            //抽奖
            activity.raffle();;
        }
    }
}


//app抽奖活动问题
    //1：加入每次参加一次抽奖活动需要口处50积分，中奖概率为10%
    //2：奖品数量固定，抽完就不能抽奖了
    //3：活动有四个状态：可以抽奖，不能抽奖，发放奖品，奖品领完
    //4：状态转换关系如下
        //可以抽奖--》90%不会中奖--》不能抽奖--》扣除50积分--》可以抽奖
        //可以抽奖--》10%中奖--》发放奖品--》奖品数count=0-->奖品领完
        //      。。。。。。          --》奖品数count>不能抽奖

//状态模式借本介绍
    //1：它主要用来解决对象在多种状态转换时，需要对外输出不同的行为的问题在，状态和行为是一一对应的，状态之间可以相互转换
    //2：当一个对象的内在状态改变时，允许改变其行为，这时候对象看起来像是改变了其类

//状态模式角色基本介绍：
    //Context:类为环境角色，用于维护ConcreteState实例，整个实例定义当前状态
    //State:抽象状态角色，定义一个接口封装与Context的一个特定接口相关的行为
    //ConcreteState:具体的状态角色，每个子类实现一个与Context的一个状态相关的行为

//app抽奖代码如下：

/*抽奖活动类*/
class RaffleActivity{
    State state=null;
    int count=0;
    State noRaffleSatae=new NoRaffleState(this);
    State canRaffleState=new CanRaffleState(this);
    State dispensePriceState=new DispensePriceState(this);
    State dispensePriceOutState=new DispensePriceOutState(this);

    public int getCount() {
        //每领取一次奖品，count--;
        int cutCount=count;
        count--;
        return cutCount;
    }

    public void setCount(int count) {
        this.count = count;
    }
    //构造器：
    // 1：初始化当前的状态为noRaffleState（即不能抽奖状态）
    // 2：初始化奖品数量
    public RaffleActivity(int count){
        this.count=count;
        this.state=getNoRaffleState();
    }

    //扣分。调用当前状态的deductMoney
    public void deductMoney(){
        state.deductMoney();
    }

    //抽奖
    public void raffle(){
         if(state.raffle()){
             state.dispensePrice();
         }

    }



    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    public State getNoRaffleState(){
        return noRaffleSatae;
    }
    public State getCanRaffleState(){
        return canRaffleState ;
    }
    public State getDispenseState(){
        return dispensePriceState;
    }
    public State getDispenseOutState(){
        return dispensePriceOutState;
    }


}


/*状态接口*/
interface State{
    //扣钱
    void deductMoney();
    //抽奖
    boolean raffle();
    //领奖
    void dispensePrice();
}

/*各个状态类*/
//不能抽奖
class NoRaffleState implements State{
    RaffleActivity activity;
    public NoRaffleState(RaffleActivity activity){
        this.activity=activity;
    }

    //当前状态可以扣除积分，扣除后，将状态设置成为可抽奖状态
    @Override
    public void deductMoney() {
        System.out.println("扣除50积分，您可以抽奖了---");
        activity.setState(activity.getCanRaffleState());
    }

    @Override
    public boolean raffle() {
        System.out.println("扣除了积分才能抽奖哦");
        return false;
    }

    @Override
    public void dispensePrice() {
        System.out.println("不能发放奖品");
    }
}

//可以抽奖
class CanRaffleState implements State{
    RaffleActivity activity;
    public CanRaffleState(RaffleActivity activity){
        this.activity=activity;
    }
    @Override
    public void deductMoney() {
        System.out.println("已经扣除过积分");
    }

    @Override
    public boolean raffle() {
        System.out.println("正在抽奖，请稍等");
        Random random=new Random();
        int number= random.nextInt(10);
        if(number==0){
            System.out.println("恭喜获取中奖资格");
            //改变活动状态设置为发放奖品
            activity.setState(activity.getDispenseState());
            return true;
        }else{
            System.out.println("很遗憾没有抽中奖品");
            //改变状态为不能抽奖
            activity.setState(activity.getNoRaffleState());
            return false;

        }
    }

    @Override
    public void dispensePrice() {
        System.out.println("没有中奖，不能发放奖品");
    }
}

//发放奖品
class DispensePriceState implements  State{
    RaffleActivity activity;
    public DispensePriceState(RaffleActivity activity){
        this.activity=activity;
    }

    @Override
    public void deductMoney() {
        System.out.println("不能扣除积分");
    }

    @Override
    public boolean raffle() {
        System.out.println("不能抽奖");
        return false;
    }

    @Override
    public void dispensePrice() {
        if(activity.getCount()>0){
            System.out.println("恭喜你中奖了");
            //改变状态为不能抽奖
            activity.setState(activity.getNoRaffleState());
        }else{
            System.out.println("很遗憾，奖品发完了");
            //改变状态为奖品发送完毕
            activity.setState(activity.getDispenseOutState());
        }

    }
}

//奖品领完(抽奖活动结束)
class DispensePriceOutState implements State{
    RaffleActivity activity;
    public DispensePriceOutState(RaffleActivity activity){
        this.activity=activity;
    }
    @Override
    public void deductMoney() {
        System.out.println("奖品发完了，请下次参与");
    }

    @Override
    public boolean raffle() {
        System.out.println("奖品发完了，请下次参与");
        return false;
    }

    @Override
    public void dispensePrice() {
        System.out.println("奖品发完了，请下次参与");
    }
}



//状态模式注意事项和细节
    //1：代码有很强的可读性，状态模式将每个状态的行为封装到对应的一个类中
    //2：方便维护，将容易产生问题的if-else语句删除了，如果把每个状态的行为都放到一个类中，每次调用方法时
        //都需要判断当前是什么状态，不但会产生很多if-else语句，而且容易出错
    //3：负荷开闭原则，容易增删状态
    //4；会产生很多类，每个状态都要一个对应的类，当状态过多时会产生很多类，加大维护难度
    //5：当一个事件或者对象有很多种状态，状态之间会相互转换，对不同的状态要求有不同的行为的时候，可以考虑使用状态模式

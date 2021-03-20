package com.master.chapter030;

import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author ColorXJH
 * @version 1.0
 * @description 解释器模式
 * @date 2021/3/16 17:26
 */
public class InterpreterPattern {

    public static String getExpStr() throws IOException {

        System.out.println("请输入表达式：");
        return (new BufferedReader(new InputStreamReader(System.in))).readLine();

    }

    public static HashMap<String,Integer>getValue(String str) throws IOException {
        HashMap<String,Integer>map=new HashMap<>();
        //遍历表达式
        for(char ch:str.toCharArray()){
            if(ch!='+'&&ch!='-'){
                if(!map.containsKey(String.valueOf(ch))){
                    System.out.println("请输入"+String.valueOf(ch)+"的值：");
                    String in =(new BufferedReader(new InputStreamReader(System.in))).readLine();
                    map.put(String.valueOf(ch),Integer.parseInt(in));
                }
            }
        }
        return map;
    }


    public static void main(String[] args) throws IOException {
        /*解释器模式*/
        String expStr=getExpStr();//a+b
        HashMap<String,Integer>var=getValue(expStr);//{a=10.b=20}
        Calculator calculator=new Calculator(expStr);
        System.out.println("运算结果："+expStr+" = "+calculator.run(var));

        /*解释器设计模式在spring框架中的运用*/
        //spring框架中，SpelExpressionParser就使用到了解释器模式
        SpelExpressionParser parser=null;
        parser=new SpelExpressionParser();
        org.springframework.expression.Expression expression=parser.parseExpression("10*(2+1)*1+66");
        int result=(Integer) expression.getValue();
        System.out.println(result);
        //说明：Expression接口：表达式接口
            //下面有不同的实现类，比如SpelExpression,或者CompositeStringExpression,LiteralExpression
            //使用的时候，根据你创建的不同的parser对象，返回不同的Expression对象
    }
}

//四则运算问题
//通过解释器模式来实现四则运算，比如a+b-c，具体要求
    //1：先输入表达式的形式，比如a+b+c-d+e,要求表达式的字母不能重复
    //2：再分别输入a,b,c,d,e的值
    //3：最后求出结果


//传统方法
    //1：编写一个方法，接收表达式的形式，然后根据用户输入的数值进行解析，得到结果
    //2：问题分析：如果加入新的运算符，比如*/（等等，不利于扩展，另外让一个方法来解析会造成程序结构混乱，不够清晰
    //3：解决方案：可以考虑使用解释器模式，即：表达式-》解释器（可以有多种）-》结果


//解释器模式基本介绍
    //1：在编译原理中，一个算数表达式通过词法分析器形成词法单元，而后这些词法单元再通过语法分析器构建语法分析树
        //最终形成一颗抽象的语法分析树，这里的词法分析器和语法分析器都可以看做是解析器
    //2：解释器模式（InterpreterPattern）是指给定一个语言（表达式），定义它的文法的一种表示，并定义一个解释器
        //使用该解释器来解释语言中的句子（表达式）
    //3：应用场景：1：应用可以将一个需要解释执行的语言中的句子表示为一个抽象语法树
                //2：一些重复出现的问题可以用一种简单的语言来表达
                //3：一个简单语法需要解释的场景
    //4：这样的例子还有:比如编译器，运算表达式计算，正则表达式，机器人等


//解释器模式的原理类图
    //1：Context:是环境角色，含有解释器之外的全局信息
    //2：abstractExpression: 抽象表达式，声明一个抽象的解释操作,这个方法为抽象语法树中所有的节点所共享
    //3：terminalExpression:终结符表达式，实现与文法中的终结符相关的解释操作，
    //4：noneTerminalExpression: 非终结符表达式，为文法中的非终结符实现解释操作
    //说明：输入context和terminalExpression信心通过client输入即可


//案例代码如下：
/*抽象类表达式
* 通过hashmap键值对，可以获取到各个变量的值
* */
abstract  class Expression{
    /*解释公示和数值，key就是公式（表达式）参数（a,b,c,d），value就是具体值*/
    //{a=10,b=20},具体的值
    abstract int intercept(HashMap<String,Integer>var);
}


/*符号*/
//抽象运算符号解析器，这里，每个运算符号都只和自己左右两个数字有关
//但是左右两个数字有可能也是一个解析的结果，无论何种类型，都是Expression类的实现类
class SymbolExpression extends Expression{
    /*左右表达式*/
    protected Expression left;
    protected Expression right;

    public SymbolExpression(Expression left,Expression right){
        this.left=left;
        this.right=right;
    }

    //因为 SymbolExpression 是让其子类来实现的，因此intercept是一个默认空实现
    @Override
    int intercept(HashMap<String, Integer> var) {
        return 0;
    }
}

/*变量的解释器
*
* */
class VarExpression extends Expression{
    private String key;//key=a,key=b,key=c
    public VarExpression(String key){
        this.key=key;
    }


    //var 就是{a=10,b=20}
    //intercept  根据变量的名称，返回对应的值
    @Override
    int intercept(HashMap<String, Integer> var) {
        return var.get(this.key);
    }
}

/*减号*/
class SubExpression extends SymbolExpression{

    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }
    //求出left和right表达式相减后的结果
    public int intercept(HashMap<String, Integer> var){
        return super.left.intercept(var)-super.right.intercept(var);
    }
}

/*加号解释器
*
* */
class AddExpression extends SymbolExpression{


    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }


    //处理相加，var={a=10,b=20}...
    @Override
    int intercept(HashMap<String, Integer> var) {
        //返回left表达式对应的值 a=10       返回right表达式对应的值 b=20
        //这些值不停的往栈里压
        return super.left.intercept(var)+super.right.intercept(var);
    }
}


/*计算器*/
class Calculator{
    //定义表达式
    private Expression expression;

    /*构造参数传参，并解析*/
    public Calculator(String expStr){
        //安排运算先后顺序
        Stack<Expression>stack=new Stack<>();
        //expStr= a+b
        char[] charArray=expStr.toCharArray();  //[a,+,b]
        Expression left=null;
        Expression right=null;
        //遍历字符数组，即[a,+,b]
        //针对不同的情况做相应的处理
        for(int i=0;i<charArray.length;i++){
            switch (charArray[i]){
                case '+':
                    //先弹出左表达式
                    left=stack.pop();
                    //将右边的值取出，并构造变量解释器（构造的时候已经将key值传入）
                    right=new VarExpression(String.valueOf(charArray[++i]));
                    //构造一个新的符号解释器，并将其压入栈中，现在栈中变成了一个符号表达式了（它包含了两个变量表达式（左，右）），
                    stack.push(new AddExpression(left,right));

                    break;
                case '-':
                    //
                    left=stack.pop();
                    right=new VarExpression(String.valueOf(charArray[++i]));
                    stack.push(new SubExpression(left,right));
                    break;
                default:
                    //字母：构建变量解释器，并压栈
                    stack.push(new VarExpression(String.valueOf(charArray[i])));
                    break;
            }
        }
        //遍历完整个charArray数组后，stack就得到一个最后的expression,它是一层包一层的，会递归调用
        this.expression=stack.pop();
    }

    public  int run(HashMap<String,Integer>var){
        //最后将整个表达式(a+b)和var {a=10,b=20}绑定
        //然后传递给expression的interpret进行处理，递归调用intercept方法，var（hashmap是我们后来初始化好后传入的）
        return this.expression.intercept(var);
    }

}


//解释器设计模式在spring框架中的运用
    //spring框架中，SpelExpressionParser就使用到了解释器模式
    //详情间main方法


//解释器模式注意事项和细节
    //1；当有一个语言需要解释执行，可将语言中的句子表示为一个抽象语法树，就可以考虑使用解释器模式，让程序具有良好的扩展性
    //2：应用场景：编译器，运算表达式，正则表达式，机器人
    //3：可能带来的问题：解释器模式会引起类膨胀，，它采用递归调用的方法，将导致调试十分复杂，效率可能降低
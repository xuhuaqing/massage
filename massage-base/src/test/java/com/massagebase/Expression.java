package com.massagebase;

import java.util.Stack;


import java.util.ArrayList;
/**
 * @author:WuShuang
 * @date:2020/6/10
 * @ver:1.0
 **/
public class Expression {



    /**
     * 将字符串转化成List
     * @param str
     * @return
     */
    private ArrayList<String> getStringList(String str){
        ArrayList<String> result = new ArrayList<String>();
        String num = "";
        //获取到所有长度
        for (int i = 0; i < str.length(); i++) {
            //判断是否为数字
            if(Character.isDigit(str.charAt(i))){
                num = num + str.charAt(i);
            }else{
                //获取到符号
                if(num != ""){
                    //如果不为空  把刚刚获取到的符号前面的数字放到list中
                    result.add(num);
                }
                //放入符号
                result.add(str.charAt(i) + "");
                //获取下一批
                num = "";
            }
        }
        if(num != ""){
            result.add(num);
        }
        return result;
    }

    /**
     * 将中缀表达式转化为后缀表达式
     * @param inOrderList
     * @return
     */
    private ArrayList<String> getPostOrder(ArrayList<String> inOrderList){
        ArrayList<String> result = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < inOrderList.size(); i++) {
            if(Character.isDigit(inOrderList.get(i).charAt(0))){
                result.add(inOrderList.get(i));
            }else{
                switch (inOrderList.get(i).charAt(0)) {
                    case '(':
                        stack.push(inOrderList.get(i));
                        break;
                    case ')':
                        while (!stack.peek().equals("(")) {
                            result.add(stack.pop());
                        }
                        stack.pop();
                        break;
                    default:
                        //stack.peek() 返回当前栈顶的值 保留栈顶的值
                        //stack.pop() 返回当前栈顶的值 但是不保留栈顶的值
                        while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))){
                            result.add(stack.pop());
                        }
                        stack.push(inOrderList.get(i));
                        break;
                }
            }
        }
        while(!stack.isEmpty()){
            result.add(stack.pop());
        }
        return result;
    }

    /**
     * 计算后缀表达式
     * @param postOrder
     * @return
     */
    private Integer calculate(ArrayList<String> postOrder){
        Stack stack = new Stack();
        for (int i = 0; i < postOrder.size(); i++) {
            if(Character.isDigit(postOrder.get(i).charAt(0))){
                stack.push(Integer.parseInt(postOrder.get(i)));
            }else{
                Integer back = (Integer)stack.pop();
                Integer front = (Integer)stack.pop();
                Integer res = 0;
                switch (postOrder.get(i).charAt(0)) {
                    case '+':
                        res = front + back;
                        break;
                    case '-':
                        res = front - back;
                        break;
                    case '*':
                        res = front * back;
                        break;
                    case '/':
                        res = front / back;
                        break;
                }
                stack.push(res);
            }
        }
        return (Integer)stack.pop();
    }

    /**
     * 比较运算符等级
     * @param peek
     * @param cur
     * @return
     */
    private static boolean compare(String peek, String cur){
        if("*".equals(peek) && ("/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals(cur))){
            return true;
        }else if("/".equals(peek) && ("/".equals(cur) || "*".equals(cur) ||"+".equals(cur) ||"-".equals(cur))){
            return true;
        }else if("+".equals(peek) && ("+".equals(cur) || "-".equals(cur))){
            return true;
        }else if("-".equals(peek) && ("+".equals(cur) || "-".equals(cur))){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
//        Expression calculate = new Expression();
//        String s = "5+6+abs(-789)*7+(2*10+1)*8";
//        CustomOperator customOperator = new CustomOperatorImpl();
//        //进行自定义操作符转换
//        s = customOperator.abs(s, "abs");
//        //String转换为List   获取到所有的 数字  和 字符
//        ArrayList result = calculate.getStringList(s);
//        //转为后缀表达式
//        result = calculate.getPostOrder(result);
//        //计算后缀表达式
//        int i = calculate.calculate(result);
//        System.out.println(i);


        String orderType = "0";
        String give = null;

        if(orderType.equals("1") && give.equals("1")){
            System.err.println("123");
        }

    }






}
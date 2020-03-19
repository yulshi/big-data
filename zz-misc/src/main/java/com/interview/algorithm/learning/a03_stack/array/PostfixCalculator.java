package com.interview.algorithm.learning.a03_stack.array;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 后缀表达式（又叫逆波兰表达式）实现计算器
 *
 * TODO: 中缀表达式转后缀表达式还没做，具体步骤请参考截图
 *
 @author yulshi
 @create 2020/02/24 14:29
 */
public class PostfixCalculator {

  private final Stack<Integer> stack = new Stack<>();

  private int calculate(String expression) {

    String[] split = expression.split(" ");
    List<String> list = Arrays.asList(split);

    for (String item : list) {
      if (item.matches("\\d+")) {
        stack.push(Integer.parseInt(item));
      } else {
        int right = stack.pop();
        int left = stack.pop();
        char operator = item.charAt(0);
        stack.push(calculateSimple(left, right, operator));
      }
    }

    return stack.pop();

  }

  private boolean isOperator(String item) {
    if (item.trim().length() == 1) {
      char ch = item.trim().charAt(0);
      if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
        return true;
      }
    }
    return false;
  }

  private int calculateSimple(int left, int right, char operator) {
    int result = 0;
    switch (operator) {
      case '+':
        result = left + right;
        break;
      case '-':
        result = left - right;
        break;
      case '*':
        result = left * right;
        break;
      case '/':
        result = left / right;
        break;
      default:
        throw new RuntimeException("There some problems in the expression");
    }
    return result;
  }

  public static void main(String[] args) {

    PostfixCalculator calculator = new PostfixCalculator();
    System.out.println(calculator.calculate("3 4 + 5 * 6 -"));
    System.out.println(calculator.calculate("4 5 * 8 - 60 + 8 2 / +"));
  }

}

package com.interview.algorithm.learning.a03_stack.array;

import java.util.HashMap;
import java.util.Map;

/**
 * 思路
 * 1）创建两个栈，一个负责压入数字（numStack），一个负责压入操作符（oprStack）
 * 2）遍历expression中的每个字符，并创建一个String的缓存，用于一次append每个数字的字符。
 *   2.1）如果是数字，则直接将之append到缓存中，等遍历到不是数字的字符时，将这个缓存用的String转成Int，
 *        并压入到numStack中。
 *   2.2）如果是操作符
 *       a）如果oprStack为空，则直接入栈
 *       b）如果oprStack不为空
 *         b1）如果当前的操作符的优先级小于或等于栈中的操作符，则从numStack中弹出两个数，
 *             并从oprStack中弹出操作符，进行运算，然后把结果压入numStack。再将当前的
 *             运算符入栈。
 *         b2）如果当前的操作副的优先级大于栈顶的操作符，则直接入栈
 * 3）当表达式（expression）扫描完毕，就顺序从数字栈（numStack）和符号栈（oprStack）
 *    pop出相应的数字和符号，进行计算，把结果压入numStack。
 * 4）当numStack中就只剩下一个数字的时候，将之返回。
 *
 * @author yulshi
 * @create 2020/02/24 10:34
 */
public class Calculator {

  private final ArrayStack<Integer> numStack = new ArrayStack<>(32);
  private final ArrayStack<Character> oprStack = new ArrayStack<>(16);

  private final Map<Character, Integer> priority = new HashMap<>();

  private final StringBuilder numBuff = new StringBuilder(); // 用于缓存遍历到的数字字符

  public Calculator() {
    priority.put('+', 1);
    priority.put('-', 1);
    priority.put('*', 2);
    priority.put('/', 2);
  }

  /**
   * @param expression
   */
  public int calculate(String expression) {

    for (char ch : expression.toCharArray()) {

      if (isOperator(ch)) {
        // 把缓存的num取出并压入numStack
        pushNumber();

        // If oprStack is empty, push it.
        if (oprStack.isEmpty()) {
          oprStack.push(ch);
        } else {
          if (priority.get(ch) <= priority.get(oprStack.peek())) {
            // 如果当前的操作符的优先级小于或等于栈中的操作符，则从numStack中弹出两个数，
            // 并从oprStack中弹出操作符，进行运算，然后把结果压入numStack。
            calculateInStack();
          }
          oprStack.push(ch);
        }
      } else {
        // If ch is number, cache it first
        numBuff.append(ch);
      }
    }

    // After the scan, we also need to push the number to numStack
    pushNumber();

    while (!oprStack.isEmpty()) {
      calculateInStack();
    }

    return numStack.pop();

  }

  private void calculateInStack() {
    int right = numStack.pop();
    int left = numStack.pop();
    char operator = oprStack.pop();
    int result = calculateSimple(left, right, operator);
    numStack.push(result);
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
    }
    return result;
  }

  private void pushNumber() {
    int num = Integer.parseInt(numBuff.toString().trim());
    numStack.push(num);
    numBuff.setLength(0);
  }

  private boolean isOperator(char ch) {
    switch (ch) {
      case '+':
      case '-':
      case '*':
      case '/':
        return true;
      default:
        return false;
    }
  }

  public static void main(String[] args) {

    Calculator calculator = new Calculator();
    System.out.println(calculator.calculate("30 + 2 * 6 - 2"));
    System.out.println(calculator.calculate("7*2*22-5+1-5-3-4"));
  }

}

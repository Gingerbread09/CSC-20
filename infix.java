/**********************
 * Lab Number: 10     *
 * Ashwani Panicker   *
 * Section Number: 01 *
 **********************/

// infix.java
//This program implememts the conversion from infix to postfix expressions and evaluate the postfix expressions and 
//also implememts generic classes, enums, for-each loops and the Java class library..

import java.io.*;
import java.util.*;

class infix {
    static LinkedList<Token> infixToPostfix(String s) throws Exception {
        LinkedList<Token> q = new LinkedList<>();
        Stack<Operator> MyStack = new Stack<>();
        Tokenizer T = new Tokenizer(s);
        MyStack.push(new Operator('#'));

        while (T.moreTokens()) {
            Token Tkn = T.nextToken();
            if (Tkn instanceof Operand) {
                q.addLast(Tkn);
            } else if (Tkn instanceof Operator) {
                Operator Opr = (Operator) Tkn;
                if (Opr.operator == '(') {
                    MyStack.push(Opr);
                } else if (Opr.operator == ')') {
                    try {
                        while (!MyStack.isEmpty() && MyStack.peek().operator != '(') {
                            q.addLast(MyStack.pop());
                        }
                        if (MyStack.isEmpty()) {
                            throw new infixException(errorType.ExcessRightParenthesis);
                        }
                        MyStack.pop(); // Remove '(' from stack
                    } catch (EmptyStackException e) {
                        throw new infixException(errorType.ExcessRightParenthesis);
                    }
                } else {
                    while (!MyStack.isEmpty() && MyStack.peek().precedence() >= Opr.precedence() && MyStack.peek().operator != '(') {
                        q.addLast(MyStack.pop());
                    }
                    MyStack.push(Opr);
                }
            }
        }
        while (!MyStack.isEmpty() && MyStack.peek().operator != '#') {
            if (MyStack.peek().operator == '(')
                throw new infixException(errorType.ExcessLeftParenthesis);
            q.addLast(MyStack.pop());
        }
        return q;
    }

    static int evaluatePostfix(LinkedList<Token> Post) throws Exception {
        Stack<Operand> MyStack = new Stack<>();

        while (!Post.isEmpty()) {
            Token Tkn = Post.removeFirst();
            if (Tkn instanceof Operand) {
                MyStack.push((Operand) Tkn);
            } else {
                if (MyStack.size() < 2) {
                    throw new infixException(errorType.ExcessOperator);
                }
                int opnd2 = MyStack.pop().operand;
                int opnd1 = MyStack.pop().operand;
                int result;

                switch (((Operator) Tkn).operator) {
                    case '+':
                        result = opnd1 + opnd2;
                        break;
                    case '-':
                        result = opnd1 - opnd2;
                        break;
                    case '*':
                        result = opnd1 * opnd2;
                        break;
                    case '/':
                        if (opnd2 == 0)
                            throw new ArithmeticException("Division by zero");
                        result = opnd1 / opnd2;
                        break;
                    default:
                        throw new infixException(errorType.ExcessOperator);
                }
                MyStack.push(new Operand(result));
            }
        }
        if (MyStack.size() != 1) {
            throw new infixException(errorType.ExcessOperand);
        }
        return MyStack.pop().operand;
    }

    public static void main(String[] args) throws IOException {
        LinkedList<Token> Post;
        while (true) {
            System.out.print("Enter infix: ");
            System.out.flush();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s = br.readLine();
            if (s.equals("")) break;
            try {
                Post = infixToPostfix(s);
                System.out.print("Postfix is ");
                for (Token T : Post) {
                    System.out.print(T + " ");
                }
                System.out.println();
                int result = evaluatePostfix(Post);
                System.out.println("Result is " + result + '\n');
            } catch (Exception e) {
                System.out.println("\n***** " + e.toString() + " *****\n");
            }
        }
    }
}

enum errorType {ExcessLeftParenthesis, ExcessRightParenthesis, ExcessOperator, ExcessOperand}

class infixException extends Exception {
    private final errorType etype;

    public infixException(errorType et) {
        etype = et;
    }

    public String toString() {
        return etype.name();
    }
}

// Token.java
abstract class Token {}

// Operand.java
class Operand extends Token {
    int operand;
    
    public Operand(int value) {
        this.operand = value;
    }

    @Override
    public String toString() {
        return Integer.toString(operand);
    }
}

// Operator.java
class Operator extends Token {
    char operator;

    public Operator(char operator) {
        this.operator = operator;
    }

    /**
     * @return
     */
    public int precedence() {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '(' -> 0;
            default -> -1; // Invalid operator case
        };
    }

    @Override
    public String toString() {
        return Character.toString(operator);
    }
}

// Tokenizer.java
class Tokenizer {
    private final String input;
    private int index = 0;

    public Tokenizer(String input) {
        this.input = input.replaceAll("\\s", ""); // Remove spaces
    }

    public boolean moreTokens() {
        return index < input.length();
    }

    public Token nextToken() {
        char ch = input.charAt(index++);
        
        if (Character.isDigit(ch)) {
            int number = ch - '0';
            while (index < input.length() && Character.isDigit(input.charAt(index))) {
                number = number * 10 + (input.charAt(index++) - '0');
            }
            return new Operand(number);
        }
        
        if ("+-*/()".indexOf(ch) != -1) {
            return new Operator(ch);
        }

        throw new IllegalArgumentException("Invalid character in input: " + ch);
    }
}



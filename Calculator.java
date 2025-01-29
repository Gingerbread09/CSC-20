/*
 * Lab Number: 09
 * Ashwani Panicker
 * Section Number: 04
 */

// This program implements the use of a Java GUI widget toolkit to develop a GUI interface for a simple calculator.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener {
    // Declare calculator components
    static JButton number[] = new JButton[10];
    static JTextField tf = new JTextField("0", 30);
    static JButton div = new JButton("/");
    static JButton mul = new JButton("*");
    static JButton sub = new JButton("-");
    static JButton add = new JButton("+");
    static JButton sqr = new JButton("sqrt");
    static JButton per = new JButton("%");
    static JButton fra = new JButton("1/x");
    static JButton addsub = new JButton("+/-");
    static JButton bks = new JButton("←");
    static JButton ce = new JButton("CE");
    static JButton clear = new JButton("C");
    static JButton dot = new JButton(".");
    static JButton equal = new JButton("=");
    
    // Control variables
    static boolean newNumber = true;
    static char operator;
    static double opnd1;
    static double opnd2;
    static double res;

    public static void main(String[] args) {
        // Create the main JFrame
        JFrame frm = new JFrame("Simple Calculator");
        Container contentPane = frm.getContentPane();
        contentPane.setLayout(new FlowLayout());

        // Set up text field
        tf.setHorizontalAlignment(JTextField.RIGHT);
        JLabel title = new JLabel("Ashwani's First Sim Project", JLabel.CENTER);

        // Add action listener to buttons
        ActionListener AL = new Calculator();
        mul.addActionListener(AL);
        div.addActionListener(AL);
        sub.addActionListener(AL);
        add.addActionListener(AL);
        equal.addActionListener(AL);
        sqr.addActionListener(AL);
        per.addActionListener(AL);
        fra.addActionListener(AL);
        addsub.addActionListener(AL);
        bks.addActionListener(AL);
        ce.addActionListener(AL);
        clear.addActionListener(AL);
        dot.addActionListener(AL);

        for (int i = 0; i < 10; i++) {
            number[i] = new JButton("" + i);
            number[i].addActionListener(AL);
        }

        // Add components to the main frame
        JPanel panel1 = new JPanel(new GridLayout(4, 5, 2, 2));
        contentPane.add(title);
        contentPane.add(tf);

        panel1.add(bks);
        panel1.add(ce);
        panel1.add(clear);
        panel1.add(div);
        panel1.add(sqr);
        panel1.add(number[7]);
        panel1.add(number[8]);
        panel1.add(number[9]);
        panel1.add(mul);
        panel1.add(per);
        panel1.add(number[4]);
        panel1.add(number[5]);
        panel1.add(number[6]);
        panel1.add(sub);
        panel1.add(fra);
        panel1.add(number[1]);
        panel1.add(number[2]);
        panel1.add(number[3]);
        panel1.add(add);
        panel1.add(addsub);

        contentPane.add(panel1);

        JPanel panel2 = new JPanel(new GridLayout(1, 3, 2, 2));
        panel2.add(number[0]);
        panel2.add(dot);
        panel2.add(equal);
        contentPane.add(panel2);

        // Configure the main frame
        frm.pack();
        frm.setSize(360, 300);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle number button clicks
        for (int i = 0; i <= 9; ++i) {
            if (e.getSource() == number[i]) {
                if (newNumber) {
                    tf.setText("" + i);
                    newNumber = false;
                } else {
                    tf.setText(tf.getText() + i);
                }
                return;
            }
        }

        // Handle operator button clicks
        if (e.getSource() == add || e.getSource() == sub || e.getSource() == mul || e.getSource() == div) {
            opnd1 = Double.parseDouble(tf.getText());
            newNumber = true;
            operator = e.getActionCommand().charAt(0);
            return;
        }

        if (e.getSource() == equal) {
            opnd2 = Double.parseDouble(tf.getText());
            switch (operator) {
                case '/': res = opnd1 / opnd2; break;
                case '*': res = opnd1 * opnd2; break;
                case '-': res = opnd1 - opnd2; break;
                case '+': res = opnd1 + opnd2; break;
            }
            tf.setText("" + res);
            newNumber = true;
            return;
        }

        // Additional functionality
        if (e.getSource() == clear) {
            tf.setText("0");
            opnd1 = opnd2 = res = 0;
            newNumber = true;
            return;
        }

        if (e.getSource() == ce) {
            tf.setText("0");
            newNumber = true;
            return;
        }

        if (e.getSource() == bks) {
            String text = tf.getText();
            if (text.length() > 1) {
                tf.setText(text.substring(0, text.length() - 1));
            } else {
                tf.setText("0");
            }
            return;
        }
    }
}

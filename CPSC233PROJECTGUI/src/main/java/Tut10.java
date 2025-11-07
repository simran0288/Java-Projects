import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.*;
import java.awt.*;

public class Tut10{
    public static void main(String[] args) {
        add();
}
    public static void add(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));
        JLabel first = new JLabel("Num 1");
        JLabel second = new JLabel("Num 2");
        JLabel blank = new JLabel();
        JLabel output = new JLabel("Result");
        JTextField firstNum = new JTextField();
        JTextField secondNum = new JTextField();
        JButton button = new JButton("Enter");
        JTextField result = new JTextField();
        panel.add(first);
        panel.add(firstNum);
        panel.add(second);
        panel.add(secondNum);
        panel.add(blank);
        panel.add(button);
        panel.add(output);
        panel.add(result);
        button.addActionListener(e-> {
            result.setText(String.valueOf(Double.parseDouble(firstNum.getText()) + Double.parseDouble(secondNum.getText())));
        });

    }

}


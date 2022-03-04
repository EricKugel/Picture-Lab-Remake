package test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;

public class Test extends JFrame {
    private JPanel output = new JPanel();
    private JPanel input = new JPanel();

    public Test() {
        setTitle("Test");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGUI();
        setVisible(true);
        pack();

        output("Hello world");
        output("Hi " + input("Input a name"));
    }

    private void initGUI() {
        output.setPreferredSize(new Dimension(200, 200));
        JScrollPane outputPane = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(outputPane, BorderLayout.WEST);

        input.setPreferredSize(new Dimension(200, 200));
        JScrollPane inputPane = new JScrollPane(input, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(inputPane, BorderLayout.EAST);
    }

    private void addJLabel(JPanel panel, String s) {
        panel.add(new JLabel("<html>" + s + "</html>"));
    }

    private void output(String s) {
        addJLabel(output, s);
    }

    private String input(String prompt) {
        addJLabel(input, prompt);
        JTextField inputField = new JTextField(100);
        input.add(inputField);
        pack();

        CountDownLatch latch = new CountDownLatch(1);
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return inputField.getText().strip();
    }

    public static void main(String[] arg0) {
        new Test();
    }
}

class InputField extends Thread {
    private CountDownLatch latch;
    private JTextField textField;

    public InputField(CountDownLatch latch, JTextField textField) {
        this.latch = latch;
        this.textField = textField;
    }

    public void run() {
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                latch.countDown();
            }
        });
    }   
}
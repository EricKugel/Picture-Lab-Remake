package test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;

public class Test extends JFrame {
    private JPanel output = new JPanel();
    private JTextArea input = new JTextArea(10, 100);

    private CountDownLatch latch = new CountDownLatch(1);
    private boolean inputActive = false;

    public Test() {
        setTitle("Test");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGUI();
        setVisible(true);
        pack();

        input.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (inputActive) {
                        latch.countDown();
                    }
                }
            }
        });

        output("Hello world");
        System.out.println(input("Input a name"));
        output("Hi " + input("Input a name"));
        pack();
    }

    private void initGUI() {
        output.setPreferredSize(new Dimension(200, 200));
        JScrollPane outputPane = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(outputPane, BorderLayout.WEST);

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
        input.append(prompt);
        input.append("\n");
        input.setCaretPosition(input.getText().length());
        pack();

        inputActive = true;

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch = new CountDownLatch(1);

        inputActive = false;
        return input.getText().substring(input.getText().lastIndexOf("\n"));
    }

    public static void main(String[] arg0) {
        new Test();
    }
}
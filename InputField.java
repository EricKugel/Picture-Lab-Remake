import java.util.concurrent.CountDownLatch;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.*;

public class InputField extends Thread {
    private CountDownLatch latch;
    private JTextField textField;

    public InputField(CountDownLatch latch, JTextField textField) {
        this.latch = latch;
        this.textField = textField;
        textField.setMaximumSize(new Dimension(200, 20));
    }

    public String getText() {
        return textField.getText().trim();
    }

    public JTextField getTextField() {
        return textField;
    }
    
    public void run() {
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                latch.countDown();
            }
        });
    }
}

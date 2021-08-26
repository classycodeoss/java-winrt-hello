package com.classycode.helloexample;

import com.classycode.helloexample.jna.JHello;
import com.classycode.helloexample.jna.UserConsentAvailability;
import com.sun.jna.Native;

import javax.swing.*;
import java.awt.*;

public class Main {

    private JHello nativeLib;
    private JButton button;
    private JLabel label;

    private void run() {
        JHello nativeLib = Native.load("JHello", JHello.class);
        final JFrame frame = new JFrame("Windows Hello Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 100);

        button = new JButton("Authenticate");
        button.addActionListener(e -> {
            button.setEnabled(false);
            label.setText("");
            // nativeLib.touchid_authenticate("use Touch ID from Java", touchIDCallback);
        });
        label = new JLabel();

        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(button);
        panel.add(label);

        final UserConsentAvailability availability = nativeLib.user_consent_verifier_check_availability();
        switch (availability) {
            case Available:
            case DeviceBusy:
                button.setEnabled(true);
                label.setText("Touch ID is supported");
                break;
            default:
                button.setEnabled(false);
                label.setText("Hello is not available on this device");
                break;
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

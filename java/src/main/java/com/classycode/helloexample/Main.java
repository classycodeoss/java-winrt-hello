package com.classycode.helloexample;

import com.classycode.helloexample.jna.JHello;
import com.classycode.helloexample.jna.UserConsentAvailability;
import com.classycode.helloexample.jna.UserConsentVerificationResult;
import com.sun.jna.Native;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.*;

public class Main {

    private JButton button;
    private JLabel label;

    private JHello.RequestVerificationCallback requestVerificationCallback = new JHello.RequestVerificationCallback() {
        @Override
        public void callback(UserConsentVerificationResult result) {
            SwingUtilities.invokeLater(() -> {
                label.setText(result.toString());
                button.setEnabled(true);
            });
        }
    };

    private void run() {
        final JHello lib = Native.load("JHello", JHello.class);
        final JFrame frame = new JFrame("Windows Hello Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 100);

        button = new JButton("Authenticate");
        button.addActionListener(e -> {
            button.setEnabled(false);
            label.setText("");

            final WinDef.HWND hWnd = new WinDef.HWND(Native.getWindowPointer(frame));
            lib.user_consent_verifier_request_verification(
                    "Hello from Java",
                    hWnd,
                    requestVerificationCallback
            );
        });
        label = new JLabel();

        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(button);
        panel.add(label);

        final UserConsentAvailability availability = lib.user_consent_verifier_check_availability();
        switch (availability) {
            case Available:
            case DeviceBusy:
                button.setEnabled(true);
                label.setText(availability.toString());
                break;
            default:
                button.setEnabled(false);
                label.setText(availability.toString());
                break;
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

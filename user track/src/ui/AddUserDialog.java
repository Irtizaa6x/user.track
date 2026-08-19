package ui;

import manager.SessionManager;
import util.TimeUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserDialog extends JDialog {
    private SessionManager manager;
    private JTextField usernameField;
    private JTextField ipField;
    private JButton addButton;
    private JButton closeButton;

    public AddUserDialog(JFrame parent, SessionManager manager) {
        super(parent, "Add User", true);
        this.manager = manager;

        setLayout(new BorderLayout(10, 10));
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("IP Address:"));
        ipField = new JTextField();
        formPanel.add(ipField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = new JButton("Add");
        closeButton = new JButton("Close");
        buttonPanel.add(addButton);
        buttonPanel.add(closeButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event listeners
        addButton.addActionListener(e -> addUser());
        closeButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void addUser() {
        String username = usernameField.getText().trim();
        String ip = ipField.getText().trim();
        if (username.isEmpty() || ip.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Both fields are required.",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Add session with current time
        manager.addSession(username, ip, TimeUtil.getCurrentTimestamp());
        JOptionPane.showMessageDialog(this,
                "User '" + username + "' added successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        usernameField.setText("");
        ipField.setText("");
        usernameField.requestFocus();
        // Optionally, we could close after adding, but we'll keep it open for multiple adds.
    }
}
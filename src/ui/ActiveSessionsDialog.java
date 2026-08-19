package ui;

import manager.SessionManager;
import model.Session;
import auth.AdminAuthenticator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ActiveSessionsDialog extends JDialog {
    private SessionManager sessionManager;
    private JTable sessionTable;
    private DefaultTableModel tableModel;
    private JButton removeButton;
    private JButton refreshButton;
    private JButton closeButton;

    public ActiveSessionsDialog(JFrame parent, SessionManager sessionManager) {
        super(parent, "Active Users", true);
        this.sessionManager = sessionManager;

        // Re‑login required
        if (!showReLogin()) {
            dispose();
            return;
        }

        setSize(850, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Table setup
        String[] columns = {"#", "Username", "IP Address", "Login Time", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        sessionTable = new JTable(tableModel);
        sessionTable.setRowHeight(25);
        sessionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        sessionTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
        sessionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Color rows based on status
        sessionTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = (String) table.getValueAt(row, 4);
                if (!isSelected) {
                    if ("Online".equals(status)) {
                        c.setBackground(new Color(200, 255, 200));
                    } else {
                        c.setBackground(new Color(255, 200, 200));
                    }
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(sessionTable);
        scroll.setBorder(BorderFactory.createTitledBorder("Active Sessions"));

        // Buttons (only Remove, Refresh, Close)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        removeButton = new JButton("Remove Selected User");
        removeButton.setBackground(new Color(244, 67, 54));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);

        refreshButton = new JButton("🔄 Refresh");
        refreshButton.setBackground(new Color(33, 150, 243));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);

        closeButton = new JButton("Close");
        closeButton.setBackground(new Color(33, 150, 243));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);

        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        // Status label
        JLabel statusLabel = new JLabel("Total: 0 users");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Combine button panel + status label in a nested panel
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(statusLabel, BorderLayout.SOUTH);

        add(scroll, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // Action listeners
        removeButton.addActionListener(e -> removeSelectedUser(statusLabel));
        refreshButton.addActionListener(e -> randomizeAndRefresh(statusLabel));
        closeButton.addActionListener(e -> dispose());

        // Initial load – no randomisation
        refreshTable(statusLabel);
        setVisible(true);
    }

    // ---------- Re‑login dialog ----------
    private boolean showReLogin() {
        JDialog loginDialog = new JDialog(this, "Re-login Required", true);
        loginDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);
        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");

        gbc.gridx = 0; gbc.gridy = 0; loginDialog.add(userLabel, gbc);
        gbc.gridx = 1; loginDialog.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; loginDialog.add(passLabel, gbc);
        gbc.gridx = 1; loginDialog.add(passField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.add(loginBtn);
        btnPanel.add(cancelBtn);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        loginDialog.add(btnPanel, gbc);

        loginDialog.pack();
        loginDialog.setLocationRelativeTo(this);

        final boolean[] authenticated = {false};

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (AdminAuthenticator.authenticate(user, pass)) {
                authenticated[0] = true;
                loginDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(loginDialog,
                        "Invalid credentials!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                userField.setText("");
                passField.setText("");
                userField.requestFocus();
            }
        });

        cancelBtn.addActionListener(e -> {
            authenticated[0] = false;
            loginDialog.dispose();
        });

        loginDialog.setVisible(true);
        return authenticated[0];
    }

    // ---------- Randomisation ----------
    private void randomizeSessionStatuses() {
        List<Session> sessions = sessionManager.getSessions();
        for (Session s : sessions) {
            s.setStatus(Math.random() < 0.6 ? "Online" : "Offline");
        }
    }

    // ---------- Refresh table without changing data ----------
    private void refreshTable(JLabel statusLabel) {
        tableModel.setRowCount(0);
        List<Session> sessions = sessionManager.getSessions();
        int serial = 1;
        for (Session s : sessions) {
            tableModel.addRow(new Object[]{
                    serial++,
                    s.getUsername(),
                    s.getIp(),
                    s.getLoginTime(),
                    s.getStatus()
            });
        }
        statusLabel.setText("Total: " + sessions.size() + " users (Online: " +
                sessionManager.getOnlineCount() + ", Offline: " +
                sessionManager.getOfflineCount() + ")");
    }

    // ---------- Randomise and then refresh ----------
    private void randomizeAndRefresh(JLabel statusLabel) {
        randomizeSessionStatuses();
        refreshTable(statusLabel);
        statusLabel.setText("Refreshed – statuses randomized");
    }

    // ---------- Remove selected user ----------
    private void removeSelectedUser(JLabel statusLabel) {
        int selectedRow = sessionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to remove.");
            return;
        }
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Remove user '" + username + "'?",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            sessionManager.removeSession(username);
            refreshTable(statusLabel);
            JOptionPane.showMessageDialog(this, "User '" + username + "' removed.");
        }
    }
}
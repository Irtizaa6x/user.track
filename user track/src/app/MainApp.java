package app;

import manager.SessionManager;
import manager.RequestManager;
import ui.AddUserDialog;
import ui.ActiveSessionsDialog;
import auth.AdminAuthenticator;
import util.RandomDataGenerator;
import model.Request;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainApp extends JFrame {
    private SessionManager sessionManager;
    private RequestManager requestManager;
    private JTable requestTable;
    private DefaultTableModel requestTableModel;
    private JLabel onlineLabel;
    private JLabel offlineLabel;
    private JLabel pendingLabel;
    private JLabel statusLabel;
    private JButton approveButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton viewActiveButton;
    private JButton addUserButton;
    private JButton logoutButton;

    public MainApp() {
        sessionManager = new SessionManager();
        requestManager = new RequestManager();

        // Generate 15-20 initial requests from famous people
        int initialCount = 15 + (int)(Math.random() * 6);
        RandomDataGenerator.generateRandomRequests(initialCount, requestManager);

        // Login
        if (!showLoginDialog()) {
            System.exit(0);
        }

        setupUI();
        refreshRequests();
        updateDashboard();
        setVisible(true);
    }

    private boolean showLoginDialog() {
        JDialog loginDialog = new JDialog(this, "🔐 Security Admin Authentication", true);
        loginDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("User ID:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Access Key:");
        JPasswordField passField = new JPasswordField(15);
        JButton loginBtn = new JButton("Authenticate");
        JButton cancelBtn = new JButton("Abort");

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
                        "⚠️ Authentication failed! Access denied.",
                        "Security Alert",
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

    private void setupUI() {
        setTitle("🌐 Cyber Access Monitoring System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // TOP: Project name + dashboard
        JPanel topPanel = new JPanel(new BorderLayout(10, 5));
        topPanel.setBorder(new EmptyBorder(10, 10, 5, 10));

        JLabel projectLabel = new JLabel("🌐 Cyber Access Monitoring System", SwingConstants.CENTER);
        projectLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(projectLabel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBorder(BorderFactory.createTitledBorder("📊 Security Dashboard"));

        onlineLabel = new JLabel("🟢 Active Sessions: 0", SwingConstants.CENTER);
        onlineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        onlineLabel.setForeground(new Color(0, 150, 0));

        offlineLabel = new JLabel("🔴 Idle Sessions: 0", SwingConstants.CENTER);
        offlineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        offlineLabel.setForeground(Color.RED);

        pendingLabel = new JLabel("⏳ Pending Authorizations: 0", SwingConstants.CENTER);
        pendingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pendingLabel.setForeground(new Color(200, 120, 0));

        statsPanel.add(onlineLabel);
        statsPanel.add(offlineLabel);
        statsPanel.add(pendingLabel);

        topPanel.add(statsPanel, BorderLayout.CENTER);

        // CENTER: Pending Requests Table
        String[] columns = {"#", "Requester", "Source IP", "Request Time"};
        requestTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        requestTable = new JTable(requestTableModel);
        requestTable.setRowHeight(25);
        requestTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        requestTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
        requestTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        requestTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        requestTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        requestTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tableScroll = new JScrollPane(requestTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("📋 Pending Access Requests"));

        // BOTTOM: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        approveButton = new JButton("🔐 Grant Access");
        deleteButton = new JButton("🗑 Revoke Access");
        refreshButton = new JButton("⟳ Refresh Security Logs");
        viewActiveButton = new JButton("👥 View Active Sessions");
        addUserButton = new JButton("➕ Provision User");
        logoutButton = new JButton("⏻ Terminate Session");

        approveButton.setBackground(new Color(76, 175, 80));
        approveButton.setForeground(Color.WHITE);
        approveButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        refreshButton.setBackground(new Color(33, 150, 243));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);

        viewActiveButton.setBackground(new Color(255, 152, 0));
        viewActiveButton.setForeground(Color.WHITE);
        viewActiveButton.setFocusPainted(false);

        addUserButton.setBackground(new Color(0, 150, 136));
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setFocusPainted(false);

        logoutButton.setBackground(new Color(244, 67, 54));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);

        buttonPanel.add(approveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(viewActiveButton);
        buttonPanel.add(addUserButton);
        buttonPanel.add(logoutButton);

        // STATUS BAR
        statusLabel = new JLabel("🟢 System Ready");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));

        // ASSEMBLE
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Action Listeners
        approveButton.addActionListener(e -> approveSelected());
        deleteButton.addActionListener(e -> deleteSelected());
        refreshButton.addActionListener(e -> {
            simulateRequestChanges();      // add/remove random requests
            refreshRequests();
            updateDashboard();
            statusLabel.setText("🔄 Security logs refreshed at " + util.TimeUtil.getCurrentTimestamp() +
                    " (new requests simulated)");
        });
        viewActiveButton.addActionListener(e -> viewActiveUsers());
        addUserButton.addActionListener(e -> new AddUserDialog(this, sessionManager));
        logoutButton.addActionListener(e -> logout());
    }

    /**
     * Simulates real-time network activity by randomly adding and removing
     * pending requests each time the Refresh button is clicked.
     */
    private void simulateRequestChanges() {
        int addCount = (int)(Math.random() * 4);    // 0 to 3 new requests
        int removeCount = (int)(Math.random() * 3); // 0 to 2 removals

        for (int i = 0; i < addCount; i++) {
            requestManager.addRandomRequest();
        }
        for (int i = 0; i < removeCount; i++) {
            requestManager.removeRandomRequest();
        }
    }

    private void approveSelected() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a pending access request first.");
            return;
        }
        String username = (String) requestTableModel.getValueAt(selectedRow, 1);
        String ip = (String) requestTableModel.getValueAt(selectedRow, 2);

        String newUsername = JOptionPane.showInputDialog(this,
                "Enter User ID for " + ip + ":",
                "Grant Access",
                JOptionPane.QUESTION_MESSAGE);
        if (newUsername == null || newUsername.trim().isEmpty()) {
            return;
        }
        newUsername = newUsername.trim();

        sessionManager.addSession(newUsername, ip, util.TimeUtil.getCurrentTimestamp());
        requestManager.removeRequest(username);
        refreshRequests();
        updateDashboard();
        statusLabel.setText("✅ Access granted to: " + newUsername);
        JOptionPane.showMessageDialog(this, "✅ Access request approved. User '" + newUsername + "' provisioned.");
    }

    private void deleteSelected() {
        int selectedRow = requestTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a pending access request first.");
            return;
        }
        String username = (String) requestTableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Revoke access request from " + username + "?",
                "Confirm Revocation",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            requestManager.removeRequest(username);
            refreshRequests();
            updateDashboard();
            statusLabel.setText("❌ Access request revoked for: " + username);
            JOptionPane.showMessageDialog(this, "Request revoked.");
        }
    }

    private void refreshRequests() {
        requestTableModel.setRowCount(0);
        List<Request> requests = requestManager.getRequests();
        int serial = 1;
        for (Request r : requests) {
            requestTableModel.addRow(new Object[]{
                    serial++,
                    r.getUsername(),
                    r.getIp(),
                    r.getRequestTime()
            });
        }
    }

    private void updateDashboard() {
        int online = sessionManager.getOnlineCount();
        int offline = sessionManager.getOfflineCount();
        int pending = requestManager.getRequestCount();
        onlineLabel.setText("🟢 Active Sessions: " + online);
        offlineLabel.setText("🔴 Idle Sessions: " + offline);
        pendingLabel.setText("⏳ Pending Authorizations: " + pending);
        statusLabel.setText("📊 Active: " + online + " | Idle: " + offline + " | Pending: " + pending);
    }

    private void viewActiveUsers() {
        new ActiveSessionsDialog(this, sessionManager);
        updateDashboard();
        statusLabel.setText("👥 Viewed active sessions");
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to terminate your session?",
                "Terminate Session",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp());
    }
}
package main.java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * SessionTableFrame - Displays active sessions in a JTable
 * Demonstrates:
 * - JTable and DefaultTableModel
 * - JScrollPane
 * - Dynamic data refresh
 * - GUI event handling
 */
public class SessionTableFrame extends JFrame {
    // GUI Components
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel buttonPanel;
    private JTable sessionTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private JLabel countLabel;
    private JButton refreshButton;
    private JButton closeButton;
    private JButton logoutButton;
    
    // Application references
    private SessionManager sessionManager;
    
    // Table columns
    private static final String[] COLUMNS = {
        "Session ID", "User", "IP Address", "Login Time", "Status"
    };
    
    /**
     * Constructor
     * @param sessionManager Reference to session manager
     */
    public SessionTableFrame(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        
        // Setup frame
        setTitle("User Session Tracker - Active Sessions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize components
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        refreshTable();
        
        setVisible(true);
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header panel
        headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBackground(new Color(236, 240, 241));
        
        titleLabel = new JLabel("📋 Active Sessions");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(41, 128, 185));
        
        countLabel = new JLabel("Sessions: 0");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        countLabel.setForeground(new Color(52, 73, 94));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(countLabel, BorderLayout.EAST);
        
        // Table setup
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        sessionTable = new JTable(tableModel);
        sessionTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sessionTable.setRowHeight(30);
        sessionTable.setShowGrid(true);
        sessionTable.setGridColor(new Color(220, 220, 220));
        
        // Set column widths
        sessionTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        sessionTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        sessionTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        sessionTable.getColumnModel().getColumn(3).setPreferredWidth(170);
        sessionTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        
        // Selection mode: single selection
        sessionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Scroll pane
        scrollPane = new JScrollPane(sessionTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        // Button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        refreshButton = new JButton("🔄 Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(120, 35));
        
        logoutButton = new JButton("🚪 End Session");
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(120, 35));
        
        closeButton = new JButton("✖ Close");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        closeButton.setBackground(new Color(149, 165, 166));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(closeButton);
        
        // Add to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Set up event handlers
     */
    private void setupEventHandlers() {
        // Refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        
        // Close button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Logout/End Session button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endSelectedSession();
            }
        });
    }
    
    /**
     * Refresh the session table with current data
     */
    public void refreshTable() {
        // Clear existing rows
        tableModel.setRowCount(0);
        
        // Get active sessions
        ArrayList<Session> sessions = sessionManager.getActiveSessions();
        
        // Add rows
        for (Session session : sessions) {
            if (session.isActive()) {
                String[] row = {
                    session.getSessionId(),
                    session.getUser().getUsername(),
                    session.getIpAddress(),
                    session.getLoginTime(),
                    "🟢 Active"
                };
                tableModel.addRow(row);
            }
        }
        
        // Update count
        countLabel.setText("Sessions: " + tableModel.getRowCount());
    }
    
    /**
     * End the selected session
     */
    private void endSelectedSession() {
        int selectedRow = sessionTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a session to end.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get username from selected row
        String username = (String) tableModel.getValueAt(selectedRow, 1);
        
        // Confirm
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to end session for user '" + username + "'?",
            "Confirm End Session",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = sessionManager.logoutUser(username);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Session ended for user '" + username + "'", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to end session for user '" + username + "'", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

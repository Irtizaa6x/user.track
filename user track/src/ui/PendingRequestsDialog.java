package ui;

import manager.RequestManager;
import manager.SessionManager;
import model.Request;
import util.TimeUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Displays pending requests with Approve and Delete buttons.
 * For each request, the admin can approve (enter a username) or delete.
 */
public class PendingRequestsDialog extends JDialog {
    private RequestManager requestManager;
    private SessionManager sessionManager;
    private JPanel requestsPanel;
    private JButton refreshButton;
    private JButton closeButton;
    private JLabel statusLabel;

    public PendingRequestsDialog(JFrame parent, RequestManager requestManager, SessionManager sessionManager) {
        super(parent, "Pending Connection Requests", true);
        this.requestManager = requestManager;
        this.sessionManager = sessionManager;

        setLayout(new BorderLayout(10, 10));
        setSize(700, 500);
        setLocationRelativeTo(parent);

        // Title and controls
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Pending Requests", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshButton = new JButton("Refresh");
        closeButton = new JButton("Close");
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Requests panel (scrollable)
        requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(requestsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Requests"));

        // Status label
        statusLabel = new JLabel("Ready");
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Layout
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // Event listeners
        refreshButton.addActionListener(e -> refreshRequests());
        closeButton.addActionListener(e -> dispose());

        // Initial load
        refreshRequests();

        setVisible(true);
    }

    /**
     * Refreshes the request list by rebuilding the UI.
     */
    private void refreshRequests() {
        requestsPanel.removeAll();
        List<Request> requests = requestManager.getRequests();

        if (requests.isEmpty()) {
            JLabel emptyLabel = new JLabel("No pending requests.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            requestsPanel.add(emptyLabel);
        } else {
            for (Request request : requests) {
                requestsPanel.add(createRequestPanel(request));
                requestsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        statusLabel.setText("Total requests: " + requests.size());
        requestsPanel.revalidate();
        requestsPanel.repaint();
    }

    /**
     * Creates a panel for a single request with Approve and Delete buttons.
     */
    private JPanel createRequestPanel(Request request) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                new EmptyBorder(5, 10, 5, 10)
        ));
        panel.setBackground(new Color(250, 250, 250));

        // Request details
        JLabel detailsLabel = new JLabel(request.toString());
        detailsLabel.setFont(new Font("Monospaced", Font.PLAIN, 13));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        JButton approveButton = new JButton("Approve");
        JButton deleteButton = new JButton("Delete");

        approveButton.setBackground(new Color(76, 175, 80));
        approveButton.setForeground(Color.WHITE);
        approveButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        // Approve action
        approveButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(this,
                    "Enter username for " + request.getIp() + ":",
                    "Approve Request",
                    JOptionPane.QUESTION_MESSAGE);

            if (username != null && !username.trim().isEmpty()) {
                // Add session with current timestamp
                sessionManager.addSession(
                        username.trim(),
                        request.getIp(),
                        TimeUtil.getCurrentTimestamp()
                );
                // Remove request
                requestManager.removeRequest(request.getUsername());
                refreshRequests();
                JOptionPane.showMessageDialog(this,
                        "Request approved! User '" + username.trim() + "' added.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Delete action
        deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Delete request from " + request.getUsername() + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                requestManager.removeRequest(request.getUsername());
                refreshRequests();
                JOptionPane.showMessageDialog(this,
                        "Request deleted.",
                        "Deleted",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        buttonPanel.add(approveButton);
        buttonPanel.add(deleteButton);

        panel.add(detailsLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }
}
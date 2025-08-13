import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class Job {
    String title;
    String description;
    String company;
    List<String> applicants = new ArrayList<>();

    public Job(String title, String description, String company) {
        this.title = title;
        this.description = description;
        this.company = company;
    }

    public String toString() {
        return "<html><b>" + title + "</b> at " + company + "<br/>" + description + "</html>";
    }
}

public class JobPortalGUI extends JFrame {
    List<Job> jobList = new ArrayList<>();

    public JobPortalGUI() {
        setTitle("JobPortalLite");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Menu
        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton postJobBtn = new JButton("Post a Job");
        JButton viewJobsBtn = new JButton("View & Apply");
        postJobBtn.setFont(new Font("Arial", Font.BOLD, 14));
        viewJobsBtn.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(postJobBtn);
        topPanel.add(viewJobsBtn);
        add(topPanel, BorderLayout.NORTH);

        // Card layout for switching views
        JPanel mainPanel = new JPanel(new CardLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Post Job Panel
        JPanel postPanel = new JPanel(new GridBagLayout());
        postPanel.setBorder(BorderFactory.createTitledBorder("Post a Job"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField titleField = new JTextField(20);
        JTextField descField = new JTextField(20);
        JTextField companyField = new JTextField(20);
        JButton postBtn = new JButton("Post Job");
        postBtn.setFont(new Font("Arial", Font.BOLD, 14));
        postBtn.setBackground(new Color(0, 153, 76));
        postBtn.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        postPanel.add(new JLabel("Job Title:"), gbc);
        gbc.gridx = 1;
        postPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        postPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        postPanel.add(descField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        postPanel.add(new JLabel("Company:"), gbc);
        gbc.gridx = 1;
        postPanel.add(companyField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        postPanel.add(postBtn, gbc);

        // View Jobs Panel
        JPanel viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBorder(BorderFactory.createTitledBorder("View & Apply for Jobs"));

        DefaultListModel<Job> jobModel = new DefaultListModel<>();
        JList<Job> jobJList = new JList<>(jobModel);
        jobJList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int i, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, i, isSelected, cellHasFocus);
                if (value instanceof Job) {
                    setText(((Job) value).toString());
                }
                return c;
            }
        });

        JScrollPane jobScroll = new JScrollPane(jobJList);
        jobScroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField(20);
        JButton applyBtn = new JButton("Apply");
        applyBtn.setFont(new Font("Arial", Font.BOLD, 14));
        applyBtn.setBackground(new Color(0, 102, 204));
        applyBtn.setForeground(Color.WHITE);

        JPanel applyPanel = new JPanel(new BorderLayout());
        applyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        applyPanel.add(new JLabel("Your Name:"), BorderLayout.NORTH);
        applyPanel.add(nameField, BorderLayout.CENTER);
        applyPanel.add(applyBtn, BorderLayout.SOUTH);

        viewPanel.add(jobScroll, BorderLayout.CENTER);
        viewPanel.add(applyPanel, BorderLayout.SOUTH);

        // Add to main panel
        mainPanel.add(postPanel, "Post");
        mainPanel.add(viewPanel, "View");

        CardLayout cl = (CardLayout) (mainPanel.getLayout());

        // Button Actions
        postJobBtn.addActionListener(e -> cl.show(mainPanel, "Post"));
        viewJobsBtn.addActionListener(e -> {
            jobModel.clear();
            if (jobList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No jobs available. Please post a job first.");
            } else {
                for (Job job : jobList) jobModel.addElement(job);
                cl.show(mainPanel, "View");
            }
        });

        postBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String desc = descField.getText().trim();
            String company = companyField.getText().trim();

            if (!title.isEmpty() && !desc.isEmpty() && !company.isEmpty()) {
                jobList.add(new Job(title, desc, company));
                JOptionPane.showMessageDialog(this, "Job posted successfully!");
                titleField.setText("");
                descField.setText("");
                companyField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required to post a job.");
            }
        });

        applyBtn.addActionListener(e -> {
            Job selectedJob = jobJList.getSelectedValue();
            String name = nameField.getText().trim();
            if (selectedJob != null && !name.isEmpty()) {
                selectedJob.applicants.add(name);
                JOptionPane.showMessageDialog(this, "Applied to " + selectedJob.title + " successfully!");
                nameField.setText("");
            } else if (selectedJob == null) {
                JOptionPane.showMessageDialog(this, "Please select a job to apply.");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your name to apply.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JobPortalGUI().setVisible(true));
    }
}
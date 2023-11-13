package greg.minitwitter.GUI;

import greg.minitwitter.admin.AdminHandler;
import greg.minitwitter.entity.Group;
import greg.minitwitter.entity.Root;
import greg.minitwitter.entity.User;
import greg.minitwitter.user.UserHandler;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class AdminPage extends JFrame{
    private JPanel AdminPanel;
    private JTree UserGroupTree;
    private JScrollPane TreePanel;
    private JTextField addUserTextField;
    private JTextField addGroupTextField;
    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton showUserTotalButton;
    private JButton showGroupTotalButton;
    private JButton showMessagesTotalButton;
    private JButton showPositivePercentageButton;
    private JButton switchToUserViewButton;
    private JLabel TreeLabel;
    private JTextPane textStatisticsPane;
    private JPanel ControlPanel;
    private JPanel MainPanel;
    private JLabel ControlLabel;
    private final AdminHandler admin;
    private final DefaultTreeModel treeModel = (DefaultTreeModel) UserGroupTree.getModel();
    private final DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
    public AdminPage() {
        // Set the Admin panel window
        setContentPane(AdminPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(960, 720);
        setVisible(true);
        setTitle("Admin Page");

        // Initial states
        admin = AdminHandler.getInstance();
        UserHandler.getInstance().setRoot((Root) root.getUserObject());
        updateAddButtonState();
        switchToUserViewButton.setEnabled(false);

        // Set tree to listen for selection
        UserGroupTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        UserGroupTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) UserGroupTree.getLastSelectedPathComponent();
            if (node == null)
                return;

            addUserButton.setEnabled(node.isRoot() || node.getAllowsChildren());
            addGroupButton.setEnabled(node.isRoot() || node.getAllowsChildren());
            switchToUserViewButton.setEnabled(!node.getAllowsChildren());
        });

        addUserButton.addActionListener(e -> {
            if(addUserTextField.getText().compareTo("") != 0)
                addUser();
        });

        addGroupButton.addActionListener(e -> {
            if(addGroupTextField.getText().compareTo("") != 0)
                addGroup();
        });

        showUserTotalButton.addActionListener(e -> {
            getUserTotal();
        });
        showGroupTotalButton.addActionListener(e -> {
            getGroupTotal();
        });
        showMessagesTotalButton.addActionListener(e -> {
            getTotalMessages();
        });
        showPositivePercentageButton.addActionListener(e -> {
            getPositivePercentage();
        });
        switchToUserViewButton.addActionListener((e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) UserGroupTree.getLastSelectedPathComponent();
            Object nodeInfo = node.getUserObject();
            new UserPage((User)nodeInfo);
        }));
    }

    private void Display(){
        root.removeAllChildren();
        admin.Display(root);
        treeModel.reload();
    }
    private void addUser(){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) UserGroupTree.getLastSelectedPathComponent();
        Object nodeInfo = node.getUserObject();
        if(admin.addUser(addUserTextField.getText(), (Group) nodeInfo)) {
            addUserTextField.setText("");
            updateAddButtonState();
            Display();
        }
    }
    private void getUserTotal(){
        String result = "User Total: ";
        result += admin.getTotalUser().toString();
        textStatisticsPane.setText(result);
    }
    private void getGroupTotal(){
        String result = "Group Total: ";
        result += admin.getTotalGroup().toString();
        textStatisticsPane.setText(result);
    }
    private void addGroup(){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) UserGroupTree.getLastSelectedPathComponent();
        Object nodeInfo = node.getUserObject();
        if(admin.addGroup(addGroupTextField.getText(), (Group) nodeInfo)) {
            addGroupTextField.setText("");
            updateAddButtonState();
            Display();
        }
    }
    private void updateAddButtonState(){
        addUserButton.setEnabled(false);
        addGroupButton.setEnabled(false);
    }
    private void getTotalMessages(){
        textStatisticsPane.setText("Total Messages: " + admin.getTotalMessages().toString());
    }
    private void getPositivePercentage(){
        textStatisticsPane.setText("Total Positive Messages in Percentage: "
                + admin.getPositiveMessagePercentage().toString() + "%");
    }
    private void createUIComponents() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(AdminHandler.getRoot());
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        treeModel.setAsksAllowsChildren(true);
        UserGroupTree = new JTree(treeModel);
    }
}

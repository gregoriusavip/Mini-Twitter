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

    /**
     * AdminPage constructor
     * Creates the admin page UI using Java Swing
     */
    public AdminPage() {
        // Set the Admin panel window
        setContentPane(AdminPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(960, 720);
        setVisible(true);
        setTitle("Admin Page");

        // Initial states
        admin = AdminHandler.getInstance(); // singular instance of admin handler to handle method calls
        UserHandler.getInstance().setRoot((Root) root.getUserObject()); // setting the user handler have the same root
        updateAddButtonState();
        switchToUserViewButton.setEnabled(false);

        // Set tree to listen for selection
        UserGroupTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        UserGroupTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) UserGroupTree.getLastSelectedPathComponent();
            if (node == null)
                return;

            // enable both buttons to add new user or new group if the node is either root or allows children (a group)
            addUserButton.setEnabled(node.isRoot() || node.getAllowsChildren());
            addGroupButton.setEnabled(node.isRoot() || node.getAllowsChildren());

            // enable switching to user view if the selected node is a leaf node (a user node)
            switchToUserViewButton.setEnabled(!node.getAllowsChildren());
        });

        // Implement action listener for adding user
        addUserButton.addActionListener(e -> {
            if(addUserTextField.getText().compareTo("") != 0)   // ignore case when the inputted userID is empty
                addUser();
            else
                JOptionPane.showMessageDialog(null, "The input field cannot be empty");
        });

        // Implement action listener for adding user
        addGroupButton.addActionListener(e -> {
            if(addGroupTextField.getText().compareTo("") != 0)  // ignore case when the inputted groupID is empty
                addGroup();
            else
                JOptionPane.showMessageDialog(null, "The input field cannot be empty");
        });

        // Implement action listener for showing the total amount of user registered
        showUserTotalButton.addActionListener(e -> {
            getUserTotal();
        });

        // Implement action listener for showing the total amount of group registered
        showGroupTotalButton.addActionListener(e -> {
            getGroupTotal();    //method does not count root object as a group
        });

        // Implement action listener for showing the total amount messages on each user's timeline
        showMessagesTotalButton.addActionListener(e -> {
            getTotalMessages();
        });

        // Implement action listener for showing the percentage of messages that contains positive words
        showPositivePercentageButton.addActionListener(e -> {
            getPositivePercentage();
        });

        // Implement action listener for switching to user's view
        switchToUserViewButton.addActionListener((e -> {
            // node is guaranteed to be a user object if it is a user object
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) UserGroupTree.getLastSelectedPathComponent();
            Object nodeInfo = node.getUserObject();
            new UserPage((User)nodeInfo);
        }));
    }

    /**
     * method to display the tree view of groups and users
     */
    private void Display(){
        root.removeAllChildren();   // resets the tree UI
        admin.Display(root);        // display the tree hierarchy starting from the root
        treeModel.reload();         // reload the tree UI with the groups and users placed
    }

    /**
     * method to add new user
     * the group this user will be added will depend on the last selected node of the tree view
     * last selected node should only be a valid group node (root or other group objects)
     * gets userID from addUserTextField
     */
    private void addUser(){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) UserGroupTree.getLastSelectedPathComponent();
        Object nodeInfo = node.getUserObject();
        if(admin.addUser(addUserTextField.getText(), (Group) nodeInfo)) {
            addUserTextField.setText("");
            updateAddButtonState();
            Display();  //reset the tree view with the newly added user node
        }
        else
            JOptionPane.showMessageDialog(null, "The input user id is not valid");
    }

    /**
     * method to add new group
     * this group will be added depending on the last selected node of the tree view
     * last selected node should only be a valid group node (root or other group objects
     * gets groupID from addGroupTextField
     */
    private void addGroup(){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) UserGroupTree.getLastSelectedPathComponent();
        Object nodeInfo = node.getUserObject();
        if(admin.addGroup(addGroupTextField.getText(), (Group) nodeInfo)) {
            addGroupTextField.setText("");
            updateAddButtonState();
            Display();
        }
        else
            JOptionPane.showMessageDialog(null, "The input group id is not valid");
    }

    /**
     * method to get the total amount of users and display it to UI
     */
    private void getUserTotal(){
        String result = "User Total: ";
        result += admin.getTotalUser().toString();
        textStatisticsPane.setText(result);
    }
    /**
     * method to get the total amount of groups and display it to UI
     */
    private void getGroupTotal(){
        String result = "Group Total: ";
        result += admin.getTotalGroup().toString();
        textStatisticsPane.setText(result);
    }
    /**
     * method to disable the states of both adding user and group buttons
     */
    private void updateAddButtonState(){
        addUserButton.setEnabled(false);
        addGroupButton.setEnabled(false);
    }

    /**
     * method to get the total amount of messages for within every user's timeline
     */
    private void getTotalMessages(){
        textStatisticsPane.setText("Total Messages: " + admin.getTotalMessages().toString());
    }

    /**
     * method to get the percentage of positive messages for contained in a user's tweet
     * if a tweet contains multiple positive words, it will only be counted as a single message
     */
    private void getPositivePercentage(){
        textStatisticsPane.setText("Total Positive Messages in Percentage: "
                + String.format("%.2f", admin.getPositiveMessagePercentage()) + "%");
    }

    /**
     * method to create and display the initial tree view
     */
    private void createUIComponents() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(AdminHandler.getRoot());
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        treeModel.setAsksAllowsChildren(true);
        UserGroupTree = new JTree(treeModel);
    }
}

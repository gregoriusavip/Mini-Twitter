package greg.minitwitter.GUI;

import greg.minitwitter.admin.AdminHandler;
import greg.minitwitter.user.entity.Group;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class AdminPage extends JFrame{
    private JPanel panel1;
    private JTree tree1;
    private JScrollPane TreePanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton showUserTotalButton;
    private JButton showGroupTotalButton;
    private JButton stat3Button;
    private JButton stat4Button;
    private JButton switchToUserViewButton;
    private JLabel TreeLabel;
    private JTextPane textStatisticsPane;
    private JPanel ControlPanel;
    private JPanel MainPanel;
    private JLabel ControlLabel;
    private final AdminHandler admin;
    private final DefaultTreeModel treeModel = (DefaultTreeModel)tree1.getModel();
    private final DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
    public AdminPage() {
        // Set the Admin panel window
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(960, 720);
        setVisible(true);

        // Initialize AdminView
        admin = AdminHandler.getInstance();

        updateButtonState();

        // Set tree to listen for selection
        tree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree1.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
            if (node == null)
                return;

            addUserButton.setEnabled(node.isRoot() || node.getAllowsChildren());
            addGroupButton.setEnabled(node.isRoot() || node.getAllowsChildren());
        });

        addUserButton.addActionListener(e -> {
            if(textField1.getText().compareTo("") != 0)
                addUser();
        });

        addGroupButton.addActionListener(e -> {
            if(textField2.getText().compareTo("") != 0)
                addGroup();
        });

        showUserTotalButton.addActionListener(e -> {
            getUserTotal();
        });

        showGroupTotalButton.addActionListener(e -> {
            getGroupTotal();
        });
    }

    private void Display(){
        root.removeAllChildren();
        admin.Display((Group) root.getUserObject(), root);
        treeModel.reload();
    }

    private void addUser(){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
        Object nodeInfo = node.getUserObject();
        if(admin.addUser(textField1.getText(), (Group) nodeInfo)) {
            textField1.setText("");
            updateButtonState();
            Display();
        }
    }

    private void getUserTotal(){
        String result = "User Total: ";
        result += admin.getTotalUser((Group) root.getUserObject()).toString();
        textStatisticsPane.setText(result);
    }
    private void getGroupTotal(){
        String result = "Group Total: ";
        result += admin.getTotalGroup((Group) root.getUserObject()).toString();
        textStatisticsPane.setText(result);
    }
    private void addGroup(){
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
        Object nodeInfo = node.getUserObject();
        if(admin.addGroup(textField2.getText(), (Group) nodeInfo)) {
            textField2.setText("");
            updateButtonState();
            Display();
        }
    }

    private void updateButtonState(){
        addUserButton.setEnabled(false);
        addGroupButton.setEnabled(false);
    }

    private void createUIComponents() {
        Group rootGroup = new Group("Root", null);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootGroup);
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        treeModel.setAsksAllowsChildren(true);
        tree1 = new JTree(treeModel);
    }
}

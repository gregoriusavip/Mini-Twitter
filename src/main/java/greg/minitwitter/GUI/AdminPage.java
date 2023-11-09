package greg.minitwitter.GUI;

import greg.minitwitter.admin.AdminView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class AdminPage extends JFrame{
    private JPanel panel1;
    private JTree tree1;
    private JScrollPane treePane;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addUserButton;
    private JButton addGroupButton;
    private JButton stat1Button1;
    private JButton stat2Button;
    private JButton stat3Button;
    private JButton stat4Button;
    private JButton switchToUserViewButton;
    private JLabel userGroupListLabel;
    private final AdminView view;
    private final DefaultTreeModel treeModel = (DefaultTreeModel)tree1.getModel();
    private final DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
    public AdminPage() {
        // Set the Admin panel window
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(960, 720);
        setVisible(true);

        // Initialize AdminView
        view = AdminView.getInstance();

        // Set tree to listen for selection
        tree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree1.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
            if (node == null)
                return;

            Object nodeInfo = node.getUserObject();
            addUserButton.setEnabled(node.isRoot() || node.getAllowsChildren());
        });
        // Display initial tree
        Display();

        addUserButton.addActionListener(e -> {
            if(textField1.getText().compareTo("") != 0)
                addUser();
        });

        addGroupButton.addActionListener(e -> {
            if(textField2.getText().compareTo("") != 0)
                addGroup();
        });
    }

    private void Display(){
        root.removeAllChildren();
        view.Display(root);
        treeModel.reload();
    }

    private void addUser(){
        if(view.addUser(textField1.getText())) {
            textField1.setText("");
            Display();  //just add the node to the root
        }
    }

    //TODO: maybe overload addUser for if a group that isn't root is clicked

    private void addGroup(){
        if(view.addGroup(textField2.getText())) {
            textField2.setText("");
            Display();
        }
    }

    private void createUIComponents() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        treeModel.setAsksAllowsChildren(true);
        tree1 = new JTree(treeModel);
    }
}

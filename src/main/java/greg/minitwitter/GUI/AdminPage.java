package greg.minitwitter.GUI;

import greg.minitwitter.admin.AdminView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class AdminPage extends JFrame{
    private JPanel panel1;
    private JTree tree1;
    private JScrollPane treePane;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JLabel userGroupListLabel;
    private final AdminView view;
    private final DefaultTreeModel treeModel = (DefaultTreeModel)tree1.getModel();
    private final DefaultMutableTreeNode root = (DefaultMutableTreeNode)treeModel.getRoot();
    public AdminPage() {
        // Set the Admin panel window
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setVisible(true);

        // Initialize AdminView
        view = AdminView.getInstance();

        Display();
    }

    private void Display(){
        view.Display(root);
        treeModel.reload(root);
    }

    private void createUIComponents() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        treeModel.setAsksAllowsChildren(true);
        tree1 = new JTree(treeModel);
    }
}

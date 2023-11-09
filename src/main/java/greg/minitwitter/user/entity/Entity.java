package greg.minitwitter.user.entity;

import javax.swing.tree.DefaultMutableTreeNode;

public interface Entity {
    void Display(DefaultMutableTreeNode node);
    String getID();
    String toString();
}

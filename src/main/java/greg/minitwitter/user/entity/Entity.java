package greg.minitwitter.user.entity;

import javax.swing.tree.DefaultMutableTreeNode;

public interface Entity {
    public void Display(DefaultMutableTreeNode node);
    public String getID();
    public String toString();
}

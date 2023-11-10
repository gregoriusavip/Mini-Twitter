package greg.minitwitter.user.entity;

import greg.minitwitter.user.entity.visitor.EntityVisitor;

import javax.swing.tree.DefaultMutableTreeNode;

public interface Entity {
    void Display(DefaultMutableTreeNode node);
    String getID();
    String toString();
    int accept(EntityVisitor visitor);
}

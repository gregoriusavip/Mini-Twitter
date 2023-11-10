package greg.minitwitter.entity;

import greg.minitwitter.entity.visitor.EntityVisitor;

import javax.swing.tree.DefaultMutableTreeNode;

public interface Entity {
    void Display(DefaultMutableTreeNode node);
    String getID();
    String toString();
    int accept(EntityVisitor visitor);
}

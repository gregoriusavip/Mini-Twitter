package greg.minitwitter.entity;

import greg.minitwitter.entity.visitor.EntityVisitor;
import greg.minitwitter.entity.visitor.EntityVisitorString;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * interface for any Entity object
 */
public interface Entity {
    void Display(DefaultMutableTreeNode node);  // method to display any object as a tree node in the UI
    String getID(); // method to get the id for any entity objects
    String toString();  // method to display any entity object as its id in the tree UI
    long accept(EntityVisitor visitor);  // method for visitor to operate and calculate on any Entity object
    Entity acceptString(EntityVisitorString visitor);  // method for visitor to operate with String data
    long getCreationTime();
}

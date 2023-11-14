package greg.minitwitter.entity;

import greg.minitwitter.entity.visitor.EntityVisitor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for holding Entity type objects (Group, or User)
 * This class utilizes EqualsBuilder and HashCodeBuilder to override the default functionality
 * ---------------------------------------------------------------------------------------
 * Group has a unique groupID that cannot be reused by any other Entity object
 * Each group can add more user or subgroup
 */
public class Group implements Entity {
    private final String groupID;
    private final Group parentGroup;
    private final Set<Entity> userGroupSet; // set containing Entity objects

    /**
     * Constructor for Group object
     * @param groupID ID for this group
     * @param parentGroup the parent for this group
     */
    public Group(String groupID, Group parentGroup){
        this.groupID = groupID;
        this.parentGroup = parentGroup;
        userGroupSet = new HashSet<>();
    }

    /**
     * method to add new user to this group
     * the method is package-private because the entry of this method is handled by Root
     * @param user User to be added
     * @return true if user is created successfully, false if an error occurred
     */
    boolean addUser(User user) {
        if(!userGroupSet.contains(user) && user.getID().compareTo(groupID) != 0) {
            userGroupSet.add(user);
            return true;
        }
        return false;
    }

    /**
     * method to add new group to this group
     * the method is package-private because the entry of this method is handled by Root
     * @param group Group to be added
     * @return true if group is created successfully, false if an error occurred
     */
    boolean addGroup(Group group){
        if(!userGroupSet.contains(group) && !group.equals(this)) {
            userGroupSet.add(group);
            return true;
        }
        return false;
    }

    /**
     * method to find a user within this group
     * the method is package-private because the entry of this method is handled by Root
     * @param userID user to be searched
     * @return the user object if it existed, otherwise null
     */
    User findUser(String userID){
        User user = null;
        for (Entity entity : userGroupSet){
            if (entity instanceof User && userID.compareTo(entity.getID()) == 0)
                return (User) entity;
            else if (entity instanceof Group){
                user = ((Group) entity).findUser(userID);
                if (user != null)
                    return user;
            }
        }
        return user;
    }

    /**
     * parent group getters
     * the method is package-private because no operation need this method other than Root
     * @return parentGroup
     */
    Group getParentGroup(){
        return parentGroup;
    }

    /**
     * userGroup getters
     * @return userGroupSet; contains all users and groups references for this group
     */
    public Set<Entity> getSet(){
        return userGroupSet;
    }

    /**
     * getters for total group for this group
     * this method only counts itself
     * @return 1
     */
    public int getTotalGroup(){
        return 1;
    }

    /**
     * accept method for visitor
     * allows visitor to do counting operation by getting the needed data from this group
     * @param visitor any Class that implements EntityVisitor
     * @return operation results from visiting this group
     */
    @Override
    public int accept(EntityVisitor visitor){
        return visitor.visitGroup(this);
    }

    /**
     * getters of groupID
     * @return groupID
     */
    @Override
    public String getID(){
        return groupID;
    }

    /**
     * method to display this group node to the tree
     * subsequently creating child nodes for any user or subgroup member of this group
     * @param node the parent pointer node tree of this group
     */
    @Override
    public void Display(DefaultMutableTreeNode node){
        DefaultMutableTreeNode group = new DefaultMutableTreeNode(this);
        node.add(group);
        for (Entity entity: userGroupSet){
            entity.Display(group);
        }
    }

    /**
     * a method to override the default hashCode implementation
     * utilizing HashCodeBuilder to build the hashCode
     * User hash code will depend on the groupID
     * @return hash code of this object
     */
    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 31).
                append(groupID).
                toHashCode();
    }

    /**
     * a method to override the default equals implementation
     * any object will be considered as equal to this Group only if:
     * it is an instance of Entity (Group/Root or User) and either:
     * - it references the same object in memory
     * - or it has the same ID, which can be a groupID or userID
     * utilizing EqualsBuilder to build the equals
     * @param object the object to be compared
     * @return the equals definition
     */
    @Override
    public boolean equals(Object object){
        if(!(object instanceof Entity other))
            return false;
        if (object == this)
            return true;

        return new EqualsBuilder().
                append(groupID, other.getID()).isEquals();
    }

    /**
     * toString method using this Group's groupID
     * toString is for Java Swing tree node to correctly display the group ID in the tree
     * @return String of userID
     */
    @Override
    public String toString(){
        return groupID;
    }
}


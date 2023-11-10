package greg.minitwitter.entity;

import greg.minitwitter.entity.visitor.EntityVisitor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashSet;
import java.util.Set;

public class Group implements Entity {
    private final String groupID;
    private final Group parentGroup;
    private final Set<Entity> userGroupSet;
    public Group(String groupID, Group parentGroup){
        this.groupID = groupID;
        this.parentGroup = parentGroup;
        userGroupSet = new HashSet<>();
    }
    public boolean addUser(User user) {
        if(!userGroupSet.contains(user) && user.getID().compareTo(groupID) != 0) {
            userGroupSet.add(user);
            return true;
        }

        return false;
    }
    public boolean addGroup(Group group){
        if(!userGroupSet.contains(group) && !group.equals(this)) {
            userGroupSet.add(group);
            return true;
        }
        return false;
    }
    public Group getParentGroup(){
        return parentGroup;
    }
    public Set<Entity> getSet(){
        return userGroupSet;
    }
    public int getTotalGroup(){
        return 1;
    }
    @Override
    public int accept(EntityVisitor visitor){
        return visitor.visitGroup(this);
    }
    @Override
    public String getID(){
        return groupID;
    }
    @Override
    public void Display(DefaultMutableTreeNode node){
        DefaultMutableTreeNode group = new DefaultMutableTreeNode(this);
        node.add(group);
        for (Entity entity: userGroupSet){
            entity.Display(group);
        }
    }
    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 31).
                append(groupID).
                toHashCode();
    }
    @Override
    public boolean equals(Object object){
        if(!(object instanceof Entity other))
            return false;
        if (object == this)
            return true;

        return new EqualsBuilder().
                append(groupID, other.getID()).isEquals();
    }

    @Override
    public String toString(){
        return groupID;
    }
}


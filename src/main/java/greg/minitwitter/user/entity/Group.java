package greg.minitwitter.user.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashSet;
import java.util.Set;

public class Group implements Entity {
    private final String groupID;
    private final Group group;
    private final Set<Entity> userGroupSet;
    public Group(String groupID, Group group){
        this.groupID = groupID;
        this.group = group;
        userGroupSet = new HashSet<>();
    }
    public boolean addUser(User user) {
        userGroupSet.add(user);
        return true;
    }
    public boolean addGroup(Group group){
        userGroupSet.add(group);
        return true;
    }
    public Group getGroup(){
        return group;
    }

    public Set<Entity> getSet(){
        return userGroupSet;
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


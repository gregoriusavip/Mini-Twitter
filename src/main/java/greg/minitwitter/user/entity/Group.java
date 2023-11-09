package greg.minitwitter.user.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashSet;
import java.util.Set;

public class Group implements Entity, UserGroup {
    private final String groupID;
    private final Set<User> userSet;
    public Group(String groupID){
        this.groupID = groupID;
        userSet = new HashSet<>();
    }
    public boolean addUser(User user) {
        if (!userSet.contains(user)){
            userSet.add(user);
            return true;
        }
        return false;
    }
    @Override
    public String getID(){
        return groupID;
    }
    @Override
    public void Display(DefaultMutableTreeNode node){
        DefaultMutableTreeNode group = new DefaultMutableTreeNode(this);
        node.add(group);
        for (User user: userSet){
            user.Display(group);
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
        if(!(object instanceof UserGroup other))
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


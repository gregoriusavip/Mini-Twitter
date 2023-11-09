package greg.minitwitter.user.entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashSet;
import java.util.Set;

public class User implements Entity, UserGroup {
    private final String userID;
    private Group group;
    private Set<String> followers;
    private Set<String> following;

    /*TODO:
        implement news
        add news variable
    */
    public User(String userID, Group group){
        this.userID = userID;
        this.group = group;
        followers = new HashSet<>();
        following = new HashSet<>();
    }
    public boolean addFollowers(String followerID){
        if (!followers.contains(followerID)){
            followers.add(followerID);
            return true;
        }
        return false;
    }
    public boolean addFollowing(String followingID){
        if (!following.contains(followingID)){
            following.add(followingID);
            return true;
        }
        return false;
    }
    public Group getGroup(){
        return group;
    }
    @Override
    public String getID(){
        return userID;
    }

    @Override
    public void Display(DefaultMutableTreeNode node){
        node.add(new DefaultMutableTreeNode(this, false));
    }
    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 31).
                append(userID).
                toHashCode();
    }
    @Override
    public boolean equals(Object object){
        if(!(object instanceof UserGroup other))
            return false;
        if (object == this)
            return true;
        return new EqualsBuilder().
                append(userID, other.getID()).isEquals();
    }

    @Override
    public String toString(){
        return userID;
    }
}

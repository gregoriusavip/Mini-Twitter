package greg.minitwitter.user.entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashSet;
import java.util.Set;

public class User implements UserEntity {
    private final String userID;
    private String groupID;
    private Set<String> followers;
    private Set<String> following;

    /*TODO:
        implement news
        add news variable
    */
    public User(String userID, String groupID){
        this.userID = userID;
        this.groupID = groupID;
        followers = new HashSet<>();
        following = new HashSet<>();
    }
    public boolean setGroupID(String groupID){
        if (this.groupID.equals("root")) {
            this.groupID = groupID;
            return true;
        }
        return false;
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

    @Override
    public void Display(DefaultMutableTreeNode node){
        node.add(new DefaultMutableTreeNode(userID));
    }
    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 31).
                append(userID).
                toHashCode();
    }
    @Override
    public boolean equals(Object object){
        if(!(object instanceof User other))
            return false;
        if (object == this)
            return true;

        return new EqualsBuilder().
                append(userID, other.toString()).isEquals();
    }
}

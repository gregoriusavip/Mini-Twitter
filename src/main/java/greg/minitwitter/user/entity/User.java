package greg.minitwitter.user.entity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

public class User implements UserEntity {
    private final String userid;
    private String groupID;
    private Set<String> followers;
    private Set<String> following;

    /*TODO:
        implement news
        add news variable
    */
    public User(String userid){
        this.userid = userid;
        followers = new HashSet<>();
        following = new HashSet<>();
    }
    public boolean setGroupID(String groupID){
        if (this.groupID == null) {
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
    public int hashCode(){
        return new HashCodeBuilder(17, 31).
                append(userid).
                toHashCode();
    }
    @Override
    public boolean equals(Object object){
        if(!(object instanceof User other))
            return false;
        if (object == this)
            return true;

        return new EqualsBuilder().
                append(userid, other.getUserid()).isEquals();
    }

    public String getUserid(){
        return userid;
    }

    public User getInstance(){
        return this;
    }

    @Override
    public void Display(){
        System.out.println(userid);
    }
}

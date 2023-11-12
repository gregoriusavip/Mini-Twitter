package greg.minitwitter.entity;
import greg.minitwitter.entity.observer.UserObserver;
import greg.minitwitter.entity.subject.UserSubject;
import greg.minitwitter.entity.visitor.EntityVisitor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.tree.DefaultMutableTreeNode;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.time.LocalTime;

public class User extends UserSubject implements Entity, UserObserver {
    private final String userID;
    private final Group group;
    private final Set<String> following;
    private final Set<String> followers;
    private final List<String> newsFeed;
    private final DateTimeFormatter formatter;

    public User(String userID, Group group){
        this.userID = userID;
        this.group = group;
        following = new HashSet<>();
        followers = new HashSet<>();
        newsFeed = new LinkedList<>();
        formatter = DateTimeFormatter.ofPattern("HH:mm");
    }
    private boolean addFollowers(String userID, User user){
        if (user == this){
            System.out.println("Cannot follow self");
            return false;
        }
        if (!followers.contains(userID)){
            followers.add(userID);
            this.attach(user);
            return true;
        }
        return false;
    }
    public boolean addFollowing(String userID, User user){
        if (user == this){
            System.out.println("Cannot follow self");
            return false;
        }
        if (!following.contains(userID) && user.addFollowers(this.userID, this)){
            following.add(userID);
            System.out.println("Followed user");
            return true;
        }
        System.out.println("User has already been followed");
        return false;
    }
    public void postTweet(String tweet){
        LocalTime currentTime = LocalTime.now();
        newsFeed.addFirst("@" + userID + " (" + currentTime.format(formatter) + ") " + ":" + tweet);
        notifyObservers();
    }
    public List<String> getNewsFeed(){
        return newsFeed;
    }
    public int getTotalUser(){
        return 1;
    }
    public Group getGroup(){
        return group;
    }
    @Override
    public void update(UserSubject userSubject){
        this.newsFeed.addFirst(((User) userSubject).getNewsFeed().getFirst());
    }
    @Override
    public int accept(EntityVisitor visitor){
        return visitor.visitUser(this);
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
        if(!(object instanceof Entity other))
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

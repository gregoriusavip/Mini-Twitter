package greg.minitwitter.entity;
import greg.minitwitter.entity.observer.Info;
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
import java.util.regex.*;

public class User extends UserSubject implements Entity, UserObserver {
    private final String userID;
    private final Group group;
    private final Set<String> following;
    private final Set<String> followers;
    private final List<String> newsFeed;
    private final DateTimeFormatter formatter;
    private final String regex = "(?i)\\bgood\\b|\\bgreat\\b|\\bexcellent\\b";
    private final Pattern pattern = Pattern.compile(regex);
    private Matcher matcher;
    private int totalPositiveMessage;
    private String newestFollowing;

    public User(String userID, Group group){
        this.userID = userID;
        this.group = group;
        following = new HashSet<>();
        followers = new HashSet<>();
        newsFeed = new LinkedList<>();
        formatter = DateTimeFormatter.ofPattern("HH:mm");
    }
    private boolean addFollowers(String userID, User newFollower){
        if (newFollower == this){
            System.out.println("Cannot follow self");
            return false;
        }
        if (!followers.contains(userID)){
            followers.add(userID);
            this.attach(newFollower);
            return true;
        }
        return false;
    }
    public boolean addFollowing(String userID, User userToFollow){
        if (userToFollow == this){
            System.out.println("Cannot follow self");
            return false;
        }
        if (!following.contains(userID) && userToFollow.addFollowers(this.userID, this)){
            following.add(userID);
            newestFollowing = userID;
            notifyPanelNewFollowing();
            System.out.println("Followed user");
            return true;
        }
        System.out.println("User has already been followed");
        return false;
    }
    public void postTweet(String tweet){
        matcher = pattern.matcher(tweet);
        if (matcher.find())
            totalPositiveMessage += 1;
        LocalTime currentTime = LocalTime.now();
        newsFeed.addFirst("@" + userID + " (" + currentTime.format(formatter) + ") " + ":" + tweet);
        notifyFollowers();
        notifyPanelTweet();
    }
    public List<String> getNewsFeed(){
        return newsFeed;
    }
    public String getNewestTweet() { return newsFeed.getFirst(); }
    public Set<String> getFollowing() { return following; }
    public String getNewestFollowing() { return newestFollowing; }
    public int getTotalUser(){
        return 1;
    }
    public Group getGroup(){
        return group;
    }
    public int getTotalMessages() { return newsFeed.size(); }
    public int getTotalPositiveMessage() { return totalPositiveMessage; }
    @Override
    public void update(UserSubject userSubject, Info info){
        if(info != Info.NEWTWEET){
            System.out.println("A Fatal Error Occurred");
            return;
        }
        String tweet = ((User) userSubject).getNewsFeed().getFirst();
        matcher = pattern.matcher(tweet);
        if (matcher.find())
            totalPositiveMessage += 1;
        this.newsFeed.addFirst(tweet);
        notifyPanelTweet();
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

package greg.minitwitter.entity;
import greg.minitwitter.entity.observer.Info;
import greg.minitwitter.entity.observer.UserObserver;
import greg.minitwitter.entity.subject.UserSubject;
import greg.minitwitter.entity.visitor.EntityVisitor;
import greg.minitwitter.entity.visitor.EntityVisitorString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.swing.tree.DefaultMutableTreeNode;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.time.LocalTime;
import java.util.regex.*;

/**
 * User class that contains data for each user
 * This class utilizes EqualsBuilder and HashCodeBuilder to override the default functionality
 * ---------------------------------------------------------------------------------------
 * User class will have unique userID, a pointer to a parent group
 * Each user also can follow or being followed by unique users based of the userID
 * newsFeed will contain the list of tweets that this user or other followed user has posted
 * ---------------------------------------------------------------------------------------
 * Extending UserSubject to being observed by other users and their UI panels/pages
 * implementing Entity for user and group hierarchy
 * implementing UserObserver to observe other user's changes, particularly changes in newsFeed from new tweet
 */
public class User extends UserSubject implements Entity, UserObserver {
    private final String userID;
    private final Group group;

    // following and followers set should not contain the actual User's reference, only the ID to maintain uniqueness
    private final Set<String> following;
    private final Set<String> followers;
    private final LinkedList<String> newsFeed;
    private final DateTimeFormatter formatter;  // formatter for timestamps of a tweet
    private final long creationTime;
    private long lastUpdateTime = 0;    // 0 when no tweet has been posted in their timeline

    // defining regex for positive words pattern
    private final String regex = "(?i)\\bgood\\b|\\bgreat\\b|\\bexcellent\\b";
    private final Pattern pattern = Pattern.compile(regex);
    private Matcher matcher;
    private int totalPositiveMessage;
    private String newestFollowing;

    /**
     * constructor to create a User object
     * @param userID this User's unique ID
     * @param group this User's parent group
     */
    public User(String userID, Group group){
        this.userID = userID;
        this.group = group;
        following = new HashSet<>();
        followers = new HashSet<>();
        newsFeed = new LinkedList<>();
        formatter = DateTimeFormatter.ofPattern("HH:mm");
        creationTime = System.currentTimeMillis();
    }

    /**
     * a private method to add a new followers
     * this user should not call addFollowers other than if other user is following this user
     * in other words, addFollowers will be called when another user called addFollowing to this userID
     * @param userID the id of the new follower to be added to the HashSet of followers
     * @param newFollower the User object of this new follower to attach as a subject to be notified
     * @return true upon completion of the operation, false if an error occurred
     */
    private boolean addFollowers(String userID, User newFollower){
        if (newFollower == this){   // reject if the newFollower is this user
            System.out.println("Cannot follow self");
            return false;
        }
        if (!followers.contains(userID)){   // reject if this new follower has followed this user already
            followers.add(userID);
            this.attach(newFollower);   // attach this newFollower to be notified for certain operation (posting tweet)
            return true;
        }
        return false;
    }

    /**
     * method to follow a user
     * @param userID the ID of the user to be followed
     * @param userToFollow the object reference for followed user to add this user to their set of followers
     * @return true upon completion, false if an error occurred
     */
    public boolean addFollowing(String userID, User userToFollow){
        if (userToFollow == this){  // reject following its own user object
            System.out.println("Cannot follow self");
            return false;
        }
        // when the user to be followed has not been followed yet
        // add this current user object as a new follower for the target user
        if (!following.contains(userID) && userToFollow.addFollowers(this.userID, this)){
            following.add(userID);
            newestFollowing = userID;
            notifyPanelNewFollowing();  // if a panel observing this user object exist, update the following field
            System.out.println("Followed user");
            return true;
        }
        System.out.println("User has already been followed");
        return false;
    }

    /**
     * method to tweet a post
     * will notify any users that followed this user account and update their newsFeed
     * will also notify for any panel that is observing this user account to update their newsFeed UI
     * @param tweet the body message for this user to tweet
     */
    public void postTweet(String tweet){
        matcher = pattern.matcher(tweet);
        if (matcher.find()) // find if this tweet will contain any positive messages
            totalPositiveMessage += 1;
        LocalTime currentTime = LocalTime.now();
        newsFeed.addFirst("@" + userID + " (" + currentTime.format(formatter) + ") " + ":" + tweet);
        lastUpdateTime = System.currentTimeMillis();
        notifyFollowers();  // update newsFeed for any user following this account
        notifyPanelTweet(); // update newsFeed UI for any panel that is observing this account
    }

    /**
     * getters for the newsFeed list
     * newsFeed will be sorted from newest at 0 index to the latest
     * @return (List)LinkedList of newsFeed
     */
    public LinkedList<String> getNewsFeed(){
        return newsFeed;
    }

    /**
     * getters for the newest tweet
     * @return String of the newest tweet
     */
    public String getNewestTweet() { return newsFeed.getFirst(); }

    /**
     * getters for users following set
     * @return (Set)HashSet of the following set
     */
    public Set<String> getFollowing() { return following; }

    /**
     * getters for the newest followed user
     * special String variable newestFollowing because Set have an unordered structure
     * @return String of the newest followed user's id
     */
    public String getNewestFollowing() { return newestFollowing; }

    /**
     * getters for the total amount of user this object holds
     * @return 1 as itself
     */
    public int getTotalUser(){
        return 1;
    }

    /**
     * getters for the parent of this group
     * @return the reference pointer to this user's parent group
     */
    public Group getGroup(){
        return group;
    }

    /**
     * getters for getting the total messages posted in the newsFeed
     * @return newsFeed size as the total messages of this user
     */
    public int getTotalMessages() { return newsFeed.size(); }

    /**
     * getters for the total messages contains the positive regex
     * each message will only be counted once
     * @return total positive messages
     */
    public int getTotalPositiveMessage() { return totalPositiveMessage; }

    public long getLastUpdateTime(){ return lastUpdateTime; }

    @Override
    public long getCreationTime(){
        return creationTime;
    }

    /**
     * Update method for updating newsFeed if any changes occur within it
     * Upon completion, if there is any UserPage panel observing this user:
     * notify that panel to update the newsFeed UI
     * @param userSubject the user that created the new tweet
     * @param info the type of update, of which has to be New Tweet
     */
    @Override
    public void update(UserSubject userSubject, Info info){
        if(info != Info.TWEET){
            System.out.println("A Fatal Error Occurred");
            return;
        }
        String tweet = ((User) userSubject).getNewsFeed().getFirst();   // get the newly created tweet
        matcher = pattern.matcher(tweet);
        if (matcher.find()) // find if it contains positive word
            totalPositiveMessage += 1;  // update the positive word counter for the observers
        this.newsFeed.addFirst(tweet);  // update the newsFeed because this object follows userSubject
        this.lastUpdateTime = System.currentTimeMillis();
        notifyPanelTweet(); // notify for any panel about these changes and update their UI
    }

    /**
     * Visitor pattern that takes EntityVisitor for counting specific operations
     * @param visitor any Class that implements EntityVisitor
     * @return result of any operations as long
     */
    @Override
    public long accept(EntityVisitor visitor){
        return visitor.visitUser(this);
    }

    /**
     * Visitor pattern that takes EntityVisitor for String return operation
     * @param visitor any Class that implements EntityVisitor
     * @return result of any operations as String
     */
    @Override
    public Entity acceptString(EntityVisitorString visitor){
        return visitor.visitUser(this);
    }

    /**
     * getters of this user ID
     * @return userID
     */
    @Override
    public String getID(){
        return userID;
    }

    /**
     * method to create a child of a parent node.
     * node should come from the parent group of this user
     * @param node pointer to the parent node
     * results in a new child leaf node containing this user object
     */
    @Override
    public void Display(DefaultMutableTreeNode node){
        node.add(new DefaultMutableTreeNode(this, false));
    }

    /**
     * a method to override the default hashCode implementation
     * utilizing HashCodeBuilder to build the hashCode
     * User hash code will depend on the userID
     * @return hash code of this object
     */
    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 31).
                append(userID).
                toHashCode();
    }

    /**
     * a method to override the default equals implementation
     * any object will be considered as equal to this User only if:
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
                append(userID, other.getID()).isEquals();
    }

    /**
     * toString method using this User's userID
     * toString is for Java Swing tree node to correctly display user ID in the tree node
     * @return String of userID
     */
    @Override
    public String toString(){
        return userID;
    }
}

package greg.minitwitter.entity.subject;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.observer.Info;
import greg.minitwitter.entity.observer.UserObserver;

import java.util.LinkedList;
import java.util.List;

/**
 * abstract base class for User object
 * this abstract class allows a user object to observe either another user object or a panel UI
 * observe any user object that follows this user
 * observe any panel UI that is handling the display of this user's data
 */
public abstract class UserSubject {
    private final List<UserObserver> followers = new LinkedList<>();
    private final List<UserObserver> panelList = new LinkedList<>();

    /**
     * method to add a userObserver type of object for this user to observe
     * @param userObserver the object to observe
     */
    public void attach(UserObserver userObserver){
        if (userObserver instanceof User)   // add any User object to the followers list
            followers.add(userObserver);
        else                                // add any panel UserPage object to the panelList
            panelList.add(userObserver);
    }

    /**
     * method to remove any object that no longer need to be observed
     * @param userObserver userObserver type of object to remove from the list
     */
    public void detach(UserObserver userObserver){
        if (userObserver instanceof User)
            followers.remove(userObserver); // to remove target user that no longer follow this user
        else
            panelList.remove(userObserver); // to remove any UI panel that has closed its UI
    }

    /**
     * method to notify any followers of this user
     * designed to notify any followers of current user's new tweet
     */
    public void notifyFollowers() {
        for(UserObserver user : followers){
            user.update(this, Info.TWEET);
        }
    }

    /**
     * method to notify any observable panel for a new tweet
     */
    public void notifyPanelTweet() {
        for(UserObserver panel : panelList){
            panel.update(this, Info.TWEET);
        }
    }

    /**
     * method to notify any observable panel when this user follows a user
     */
    public void notifyPanelNewFollowing() {
        for(UserObserver panel : panelList){
            panel.update(this, Info.FOLLOWING);
        }
    }
}

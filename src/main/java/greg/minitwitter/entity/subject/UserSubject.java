package greg.minitwitter.entity.subject;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.observer.Info;
import greg.minitwitter.entity.observer.UserObserver;

import java.util.LinkedList;
import java.util.List;

public abstract class UserSubject {
    private final List<UserObserver> followers = new LinkedList<>();
    private final List<UserObserver> panelList = new LinkedList<>();
    public void attach(UserObserver userObserver){
        if (userObserver instanceof User)
            followers.add(userObserver);
        else
            panelList.add(userObserver);
    }

    public void detach(UserObserver userObserver){
        if (userObserver instanceof User)
            followers.remove(userObserver);
        else
            panelList.remove(userObserver);
    }
    public void notifyFollowers() {
        for(UserObserver user : followers){
            user.update(this, Info.TWEET);
        }
    }
    public void notifyPanelTweet() {
        for(UserObserver panel : panelList){
            panel.update(this, Info.TWEET);
        }
    }
    public void notifyPanelNewFollowing() {
        for(UserObserver panel : panelList){
            panel.update(this, Info.FOLLOWING);
        }
    }
}

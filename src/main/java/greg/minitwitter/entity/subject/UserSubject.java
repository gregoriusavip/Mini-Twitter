package greg.minitwitter.entity.subject;

import greg.minitwitter.entity.observer.UserObserver;

import java.util.LinkedList;
import java.util.List;

public abstract class UserSubject {
    private final List<UserObserver> followers = new LinkedList<>();
    public void attach(UserObserver userObserver){
        followers.add(userObserver);
    }

    public void detach(UserObserver userObserver){
        followers.remove(userObserver);
    }
    public void notifyObservers() {
        for(UserObserver user : followers){
            user.update(this);
        }
    }
}

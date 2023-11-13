package greg.minitwitter.entity.observer;

import greg.minitwitter.entity.subject.UserSubject;

public interface UserObserver {
    public void update(UserSubject userSubject, Info info);
}

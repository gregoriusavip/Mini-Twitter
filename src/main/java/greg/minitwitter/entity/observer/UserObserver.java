package greg.minitwitter.entity.observer;

import greg.minitwitter.entity.subject.UserSubject;

/**
 * interface for any UserObserver class
 * For any class that wants to be observed by User and notified from any changes
 */
public interface UserObserver {
    /**
     * method to implement within a subject
     * @param userSubject the subject that wants to update from changes occurred within the User
     * @param info Enum object that indicates the type of update needed
     */
    public void update(UserSubject userSubject, Info info);
}

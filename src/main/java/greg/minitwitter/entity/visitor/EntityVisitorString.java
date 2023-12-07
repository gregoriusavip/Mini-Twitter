package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;
import greg.minitwitter.entity.User;

/**
 * interface for any visitor for entity objects doing String operations
 */
public interface EntityVisitorString {
    Entity visitUser(User user);   // method to visit User base object
    Entity visitGroup(Group group); // method to visit Group base object
}

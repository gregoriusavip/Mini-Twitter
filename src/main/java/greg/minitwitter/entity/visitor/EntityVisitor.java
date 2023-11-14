package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Group;

/**
 * interface for any visitor for entity objects
 */
public interface EntityVisitor
{
    int visitUser(User user);   // method to visit User base object
    int visitGroup(Group group); // method to visit Group base object
}

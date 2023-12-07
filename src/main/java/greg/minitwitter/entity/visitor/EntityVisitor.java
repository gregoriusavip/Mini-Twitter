package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Group;

/**
 * interface for any visitor for entity objects
 */
public interface EntityVisitor
{
    long visitUser(User user);   // method to visit User base object
    long visitGroup(Group group); // method to visit Group base object
}

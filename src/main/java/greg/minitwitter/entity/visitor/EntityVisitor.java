package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Group;

public interface EntityVisitor
{
    int visitUser(User user);
    int visitGroup(Group group);
}

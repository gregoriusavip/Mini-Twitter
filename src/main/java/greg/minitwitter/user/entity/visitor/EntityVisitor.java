package greg.minitwitter.user.entity.visitor;

import greg.minitwitter.user.entity.Group;
import greg.minitwitter.user.entity.User;

public interface EntityVisitor
{
    int visitUser(User user);
    int visitGroup(Group group);
}

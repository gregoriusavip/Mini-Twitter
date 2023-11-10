package greg.minitwitter.user.entity.visitor;

import greg.minitwitter.user.entity.Entity;
import greg.minitwitter.user.entity.Group;
import greg.minitwitter.user.entity.User;

public class CountUserVisitor implements EntityVisitor{
    @Override
    public int visitUser(User user) {
        return user.getTotalUser();
    }

    @Override
    public int visitGroup(Group group) {
        int totalUser = 0;
        for (Entity entity : group.getSet()){
            if (entity instanceof Group){
                totalUser += visitGroup((Group) entity);
                continue;
            }
            totalUser += visitUser((User) entity);
        }
        return totalUser;
    }
}

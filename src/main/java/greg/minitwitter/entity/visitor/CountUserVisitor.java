package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;

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

package greg.minitwitter.user.entity.visitor;

import greg.minitwitter.user.entity.Entity;
import greg.minitwitter.user.entity.Group;
import greg.minitwitter.user.entity.User;

public class CountGroupVisitor implements EntityVisitor{
    @Override
    public int visitUser(User user){
        // not used
        return 0;
    }

    @Override
    public int visitGroup(Group group) {
        int totalGroup = 0;
        for (Entity entity : group.getSet()){
            if (entity instanceof Group){
                totalGroup += ((Group) entity).getTotalGroup();
                totalGroup += visitGroup((Group) entity);
            }
        }
        return totalGroup;
    }
}

package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;

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

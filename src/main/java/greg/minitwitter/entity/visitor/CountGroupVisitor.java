package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;

/**
 * Class to visit Groups
 * Calculate the total amount of subgroups within a group
 */
public class CountGroupVisitor implements EntityVisitor{
    @Override
    public long visitUser(User user){
        // does not need to visit user
        return 0;
    }

    /**
     * method to visit a group and traverse through its group set
     * count the total amount of subgroups within this group set
     * ignore User instances
     * @param group the group to visit
     * @return the total amount of subgroups within this group set
     */
    @Override
    public long visitGroup(Group group) {
        long totalGroup = 0;
        for (Entity entity : group.getSet()){
            if (entity instanceof Group){
                totalGroup += ((Group) entity).getTotalGroup();
                totalGroup += visitGroup((Group) entity);
            }
        }
        return totalGroup;
    }
}

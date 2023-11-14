package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;

/**
 * Class to visit Users and Groups
 * Calculate the total amount of users
 */
public class CountUserVisitor implements EntityVisitor{
    /**
     * method to get the total amount of user for each User object
     * @param user the user to visit
     * @return 1 for each User
     */
    @Override
    public int visitUser(User user) {
        return user.getTotalUser();
    }

    /**
     * method to visit a group and traverse through its group set
     * Count the total amount of users within this group set
     * @param group the group to visit
     * @return total amount of users of this group
     */
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

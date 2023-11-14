package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;

/**
 * Class to visit Users and Groups
 * Calculate the total amount of messages for all users within a group
 */
public class CountTotalMessages implements EntityVisitor{
    /**
     * method to visit a user and get the total messages
     * @param user the user to visit
     * @return total messages of this user
     */
    @Override
    public int visitUser(User user){
        return user.getTotalMessages();
    }

    /**
     * method to visit a group and traverse through its group set
     * count the total messages for each user within this group
     * @param group the group to visit
     * @return the total messages for all users within this group set
     */
    @Override
    public int visitGroup(Group group) {
        int totalMessages = 0;
        for (Entity entity : group.getSet()){
            if (entity instanceof User){
                totalMessages += visitUser((User) entity);
            }
            else{
                totalMessages += visitGroup((Group) entity);
            }
        }
        return totalMessages;
    }
}

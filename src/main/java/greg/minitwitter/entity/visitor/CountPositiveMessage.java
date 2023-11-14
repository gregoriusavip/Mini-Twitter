package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;

/**
 * Class to visit Users and Groups
 * Calculate the total amount of positive messages marked in a User
 */
 public class CountPositiveMessage implements EntityVisitor{
    /**
     * Visit a user and get their total messages containing positive words
     * @param user user to visit
     * @return total amount of messages containing positive words
     */
    @Override
    public int visitUser(User user){
        return user.getTotalPositiveMessage();
    }

    /**
     * Visit a group and go through each member
     * If member is a user, visit that user and get their total messages containing positive words
     * if member is a group, recursively visit that group to get the total messages for all users within that subgroup
     * @param group group to visit
     * @return total amount of positive messages within a subgroup
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

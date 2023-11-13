package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;
public class CountPositiveMessage implements EntityVisitor{
    @Override
    public int visitUser(User user){
        return user.getTotalPositiveMessage();
    }

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

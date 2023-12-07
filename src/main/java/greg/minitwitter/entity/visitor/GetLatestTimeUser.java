package greg.minitwitter.entity.visitor;

import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;
import greg.minitwitter.entity.User;

public class GetLatestTimeUser implements EntityVisitorString{
    @Override
    public Entity visitUser(User user){
        return user;
    }

    @Override
    public Entity visitGroup(Group group){
        long latest = 0;
        User latestUser = null;
        for(Entity entity: group.getSet()){
            if (entity instanceof Group){
                Entity latestVisitedUser = visitGroup((Group) entity);
                if(latestVisitedUser != null && ((User)latestVisitedUser).getLastUpdateTime() > latest){
                    latest = ((User)latestVisitedUser).getLastUpdateTime();
                    latestUser = ((User)latestVisitedUser);
                }
            }
            else if (((User)entity).getLastUpdateTime() > latest){
                latest = ((User)entity).getLastUpdateTime();
                latestUser = ((User)entity);
            }
        }
        return latestUser;
    }
}

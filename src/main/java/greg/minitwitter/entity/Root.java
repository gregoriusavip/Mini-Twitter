package greg.minitwitter.entity;

import java.util.HashSet;
import java.util.Set;

public class Root extends Group{
    private final Set<String> ids;

    public Root() {
        super("Root", null);
        ids = new HashSet<>();
        ids.add("Root");
    }
    public boolean addGroup(String groupID, Group group){
        Group newGroup = new Group(groupID, group);
        if (!ids.contains(newGroup.getID())) {
            System.out.println("Group not found");   //debug
            ids.add(newGroup.getID());
            return newGroup.getParentGroup().addGroup(newGroup);
        }
        return false;
    }
    public boolean addUser(String userID, Group group){
        User user = new User(userID, group);
        if(!ids.contains(user.getID())){
            ids.add(user.getID());
            return user.getGroup().addUser(user);
        }
        return false;
    }
    public User findUser(String userID){
        return super.findUser(userID);
    }

    @Override
    public Set<Entity> getSet(){
        return new HashSet<>(super.getSet());
    }
}
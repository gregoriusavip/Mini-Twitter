package greg.minitwitter.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Root is the base group that contains reference for any group and user objects
 */
public class Root extends Group{
    private final Set<String> ids;  //set containing all used ids

    /**
     * Constructor
     */
    public Root() {
        super("Root", null);
        ids = new HashSet<>();
        ids.add("Root");
    }

    /**
     * method to add a new group
     * @param groupID the id of the new group
     * @param group the parent group pointer for the new group
     * @return true on success and false on error
     */
    public boolean addGroup(String groupID, Group group){
        Group newGroup = new Group(groupID, group);
        if (!ids.contains(newGroup.getID())) {  //check if id has been used before
            System.out.println("Group not found");   //debug
            ids.add(newGroup.getID());
            return newGroup.getParentGroup().addGroup(newGroup);
        }
        return false;
    }

    /**
     * method to add a new user to a group
     * @param userID the id for new user
     * @param group the parent group for this new user
     * @return true if success and false on error
     */
    public boolean addUser(String userID, Group group){
        User user = new User(userID, group);
        if(!ids.contains(user.getID())){
            ids.add(user.getID());
            return user.getGroup().addUser(user);
        }
        return false;
    }

    /**
     * the public method to find a user given a string of userID
     * functions as the entry point of finding any user
     * @param userID the userID to be found
     * @return the reference object to this user if existed, null otherwise
     */
    public User findUser(String userID){
        return super.findUser(userID);
    }

    /**
     * getters for Set of Entity
     * Set of Entity only contains reference point to user/group in Root's layer
     * any reference point for user/group inside other subgroup need to be grabbed from within that subgroup
     * @return all user/group objects inside root's layer
     */
    @Override
    public Set<Entity> getSet(){
        return new HashSet<>(super.getSet());
    }
}
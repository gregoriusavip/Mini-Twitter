package greg.minitwitter.admin;

import greg.minitwitter.user.entity.Group;
import greg.minitwitter.user.entity.User;
import greg.minitwitter.user.entity.Entity;

import java.util.HashSet;
import java.util.Set;

class AdminHandler  {

    private static volatile AdminHandler instance;
    private final Set<Entity> root = new HashSet<>();
    private final Set<Entity> userPool = new HashSet<>();

    private AdminHandler() {
    }

    public static AdminHandler getInstance(){
        if (instance == null){
            synchronized (AdminHandler.class) {
                if (instance == null) {
                    instance = new AdminHandler();
                }
            }
        }
        return instance;
    }
    public boolean addUser(User user){
        if(!userPool.contains(user) && (!root.contains(user))){
            System.out.println("User not found");   //debug
            userPool.add(user);
            root.add(user);
            return true;
        }
        return false;
    }

    public boolean addGroup(Group group) {
        if (!userPool.contains(group) && !root.contains(group)) {
            System.out.println("Group not found");   //debug
            root.add(group);
            return true;
        }
        return false;
    }

    public Set<Entity> getRoot(){
        return new HashSet<>(root);
    }
}
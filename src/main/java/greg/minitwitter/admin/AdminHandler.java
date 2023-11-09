package greg.minitwitter.admin;

import greg.minitwitter.user.entity.Group;
import greg.minitwitter.user.entity.User;
import greg.minitwitter.user.entity.Entity;

import java.util.HashSet;
import java.util.Set;

class AdminHandler {

    private static volatile AdminHandler instance;
    private final Set<Entity> entities = new HashSet<>();

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
        if(!entities.contains(user)){
            System.out.println("User not found");   //debug
            entities.add(user);
            return user.getGroup().addUser(user);
        }
        return false;
    }

    public boolean addGroup(Group group) {
        if (!entities.contains(group)) {
            System.out.println("Group not found");   //debug
            entities.add(group);
            return group.getGroup().addGroup(group);
        }
        return false;
    }
}
package greg.minitwitter.admin;

import greg.minitwitter.user.entity.Group;
import greg.minitwitter.user.entity.User;
import greg.minitwitter.user.entity.UserEntity;

import java.util.HashSet;
import java.util.Set;

class AdminHandler  {

    private static volatile AdminHandler instance;
    private final Set<UserEntity> root = new HashSet<>();
    private final Set<User> userPool = new HashSet<>();

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
        if(!userPool.contains(user)){
            System.out.println("User not found");   //debug
            userPool.add(user);
            root.add(user);
            return true;
        }
        return false;
    }

    public boolean addGroup(Group group) {
        if (!root.contains(group)) {
            root.add(group);
            return true;
        }
        return false;
    }

    public Set<UserEntity> getRoot(){
        return new HashSet<>(root);
    }
}
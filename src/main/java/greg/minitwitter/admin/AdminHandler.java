package greg.minitwitter.admin;

import greg.minitwitter.user.entity.Entity;
import greg.minitwitter.user.entity.Group;
import greg.minitwitter.user.entity.User;
import greg.minitwitter.user.entity.visitor.CountGroupVisitor;
import greg.minitwitter.user.entity.visitor.CountUserVisitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashSet;
import java.util.Set;

public class AdminHandler {

    private static volatile AdminHandler instance;
    private final Set<String> entities = new HashSet<>();

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
    public boolean addUser(String userID, Group group){
        User user = new User(userID, group);
        if(!entities.contains(user.getID())){
            entities.add(user.getID());
            return user.getGroup().addUser(user);
        }
        return false;
    }
    public Integer getTotalUser(Group root){
        return root.accept(new CountUserVisitor());
    }
    public Integer getTotalGroup(Group root){
        return root.accept(new CountGroupVisitor());
    }
    public boolean addGroup(String groupID, Group group){
        Group newGroup = new Group(groupID, group);
        if (!entities.contains(newGroup.getID())) {
            System.out.println("Group not found");   //debug
            entities.add(newGroup.getID());
            return newGroup.getGroup().addGroup(newGroup);
        }
        return false;
    }

    public void Display(Group root, DefaultMutableTreeNode node){
        for(Entity entity : root.getSet()){
            entity.Display(node);
        }
    }
}
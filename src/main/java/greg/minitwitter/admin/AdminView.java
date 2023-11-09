package greg.minitwitter.admin;

import greg.minitwitter.user.entity.Group;
import greg.minitwitter.user.entity.User;
import greg.minitwitter.user.entity.Entity;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Set;

public class AdminView {

    private static volatile AdminView instance;
    private static final AdminHandler admin = AdminHandler.getInstance();

    private AdminView() {
    }

    public static AdminView getInstance(){
        if (instance == null){
            synchronized (AdminView.class) {
                if (instance == null) {
                    instance = new AdminView();
                }
            }
        }
        return instance;
    }
    public boolean addUser(String userID, Group group){
        if(admin.addUser(new User(userID, group))){
            System.out.println("Successfully added user");
            return true;
        }
        System.out.println("User already exist");
        return false;
    }

    public boolean addGroup(String groupID, Group group){
        if(admin.addGroup(new Group(groupID, group))){
            System.out.println("Successfully added group");
            return true;
        }
        System.out.println("Group already exist");
        return false;
    }
    public void Display(Group root, DefaultMutableTreeNode node){
        for(Entity entity : root.getSet()){
            entity.Display(node);
        }
    }
}

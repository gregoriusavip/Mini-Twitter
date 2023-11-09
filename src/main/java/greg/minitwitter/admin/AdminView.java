package greg.minitwitter.admin;

import greg.minitwitter.user.entity.Group;
import greg.minitwitter.user.entity.UserBuilder;
import greg.minitwitter.user.entity.Entity;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Set;

public class AdminView implements Entity {

    private static volatile AdminView instance;
    private static final AdminHandler admin = AdminHandler.getInstance();
    private Set<Entity> root;

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
    public boolean addUser(String userID){
        if(admin.addUser(new UserBuilder(userID).build())){
            System.out.println("Successfully added user");
            Update();
            return true;
        }
        System.out.println("User already exist");
        return false;
    }
    public boolean addUser(String userID, String groupID){
        if(admin.addUser(new UserBuilder(userID).setGroupID(groupID).build())){
            System.out.println("Successfully added user");
            Update();
            return true;
        }
        System.out.println("User already exist");
        return false;
    }

    public boolean addGroup(String groupID){
        if(admin.addGroup(new Group(groupID))){
            System.out.println("Successfully added group");
            Update();
            return true;
        }
        System.out.println("Group already exist");
        return false;
    }
    @Override
    public void Display(DefaultMutableTreeNode node){
        Update();
        for(Entity entity : root){
            entity.Display(node);
        }
    }

    private void Update(){
        root = admin.getRoot();
    }
}

package greg.minitwitter.admin;

import greg.minitwitter.user.entity.UserBuilder;
import greg.minitwitter.user.entity.UserEntity;

import java.util.Set;

public class AdminView implements UserEntity {

    private static volatile AdminView instance;
    private static final AdminHandler admin = AdminHandler.getInstance();
    private Set<UserEntity> root;

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
    public void addUser(String userID){
        if(admin.addUser(new UserBuilder(userID).build())){
            System.out.println("Successfully added user");
            Update();
            return;
        }
        System.out.println("User already exist");
    }
    public void addUser(String userID, String groupID){
        if(admin.addUser(new UserBuilder(userID).setGroupID(groupID).build())){
            System.out.println("Successfully added user");
            Update();
            return;
        }
        System.out.println("User already exist");
    }
    @Override
    public void Display(){
        Update();
        System.out.println("Displaying User Tree List");
        for(UserEntity entity : root){
            entity.Display();
        }
    }

    private void Update(){
        root = admin.getRoot();
    }
}

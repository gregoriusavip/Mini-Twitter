package greg.minitwitter.admin;

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

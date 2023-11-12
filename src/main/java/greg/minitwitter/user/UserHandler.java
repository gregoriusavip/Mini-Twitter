package greg.minitwitter.user;

import greg.minitwitter.entity.Root;
import greg.minitwitter.entity.User;

public class UserHandler {
    private static volatile UserHandler instance;
    private Root root;

    private UserHandler(){
    }
    public static UserHandler getInstance(){
        if (instance == null){
            synchronized (UserHandler.class) {
                if (instance == null) {
                    instance = new UserHandler();
                }
            }
        }
        return instance;
    }
    public boolean addFollowingHandler(User currentUser, String userID){
        User userToFollow = root.findUser(userID);
        if(userToFollow != null){
            return currentUser.addFollowing(userID, userToFollow);
        }
        System.out.println("User is not found");
        return false;   //user is not found
    }

    public void postTweetHandler(User currentUser, String tweet){
        currentUser.postTweet(tweet);
    }

    public void setRoot(Root root){
        this.root = root;
    }
}

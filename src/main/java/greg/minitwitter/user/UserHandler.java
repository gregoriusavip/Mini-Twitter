package greg.minitwitter.user;

import greg.minitwitter.entity.Root;
import greg.minitwitter.entity.User;

public class UserHandler {
    // Class to handle user's function call
    private static volatile UserHandler instance;
    private Root root;

    private UserHandler(){
    }
    public static UserHandler getInstance(){
        // Create a singleton object
        if (instance == null){
            synchronized (UserHandler.class) {
                if (instance == null) {
                    instance = new UserHandler();
                }
            }
        }
        return instance;
    }
    /**
     * This method provides a method call to follow a valid user
     *
     * @param currentUser the current user
     * @param userID the userID to be followed
     *
     * @return true if the userID is found and successfully added, false if the user doesn't exist or not valid
     */
    public boolean addFollowingHandler(User currentUser, String userID){
        User userToFollow = root.findUser(userID);
        if(userToFollow != null){
            return currentUser.addFollowing(userID, userToFollow);
        }
        System.out.println("User is not found");
        return false;   //user is not found
    }

    /**
     * This method provides a method call to let a user post a tweet
     *
     * @param currentUser the current user to tweet a post
     * @param tweet the body message to be posted
     */
    public void postTweetHandler(User currentUser, String tweet){
        currentUser.postTweet(tweet);
    }

    /**
     * method to bind the root of group and user objects
     *
     * @param root the Root base object containing the single reference to any user and group objects
     */
    public void setRoot(Root root){
        this.root = root;
    }
}

package greg.minitwitter.GUI;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.observer.Info;
import greg.minitwitter.entity.observer.UserObserver;
import greg.minitwitter.entity.subject.UserSubject;
import greg.minitwitter.user.UserHandler;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.ListIterator;

public class UserPage extends JFrame implements UserObserver {
    private JPanel UserPanel;
    private JButton followButton;
    private JTextField followUserTextField;
    private JTextArea tweetTextArea;
    private JButton tweetButton;
    private JLabel FollowingLabel;
    private JTextArea timelineTextArea;
    private JLabel UserLabel;
    private JTextArea followingUser;
    private JPanel MainPanel;
    private JPanel FollowPanel;
    private final UserHandler userHandler;

    /**
     * UserPage constructor
     * Creates the specific user page UI for any user using Java Swing
     *
     * @param user the user object that will be bound to this User Page panel
     */
    public UserPage(User user){
        // Set the User panel window
        setContentPane(UserPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(960, 720);
        setVisible(true);
        setTitle("User Page");

        // Initialize variables
        userHandler = UserHandler.getInstance();
        UserLabel.setText("Hi: " + user.getID());
        user.attach(this);
        timelineTextArea.setEditable(false);
        followingUser.setEditable(false);
        updatePage(user);

        // Implement action listener for following a user
        followButton.addActionListener(e -> {
            if(followUserTextField.getText().compareTo("") != 0) {
                follow(user);
            }
        });

        // Implement action listener for posting a tweet
        tweetButton.addActionListener(e -> {
            if(tweetTextArea.getText().compareTo("") != 0) {
                userHandler.postTweetHandler(user, tweetTextArea.getText());
                tweetTextArea.setText("");
            }
        });

        // Upon window closing
        // Remove the reference pointer from UserSubject
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                user.detach(UserPage.this);
                super.windowClosing(e);
            }
        });
    }

    /**
     * method to follow a user. Gets the userID from followUserTextField, the ID for the current user to follow
     *
     * @param user the current user
     */
    private void follow(User user){
        if(userHandler.addFollowingHandler(user, followUserTextField.getText())){
            followUserTextField.setText("");
            return;
        }
        System.out.println("An error occurred");    // debug
    }

    /**
     * method to update the page upon first time this User Page is being created
     *
     * @param user the user object that will be displayed
     */
    private void updatePage(User user){
        for (String userID : user.getFollowing()){
            followingUser.setText("* " + userID + "\n" + followingUser.getText());
        }
        LinkedList<String> newsFeed = user.getNewsFeed();
        ListIterator<String> iterator = newsFeed.listIterator(user.getTotalMessages());
        while(iterator.hasPrevious()){
            timelineTextArea.setText(iterator.previous() + "\n\n" + timelineTextArea.getText());
        }
    }

    /**
     * A visitor pattern method that will update part of the page according to the info
     * update will be called upon:
     * current user received notification that a followed user posted a new tweet
     * current user posted a new tweet
     * current user followed a valid user
     *
     * @param userSubject contains the user object to update this page with the relevant data
     * @param info an enum object indicating to update either the timeline space or the following space
     */
    @Override
    public void update(UserSubject userSubject, Info info){
        if(info == Info.TWEET) {
            timelineTextArea.setText(((User) userSubject).getNewestTweet() + "\n\n" + timelineTextArea.getText());
        }
        else{
            String newFollowing = ((User) userSubject).getNewestFollowing();
            followingUser.setText("* " + newFollowing + "\n" + followingUser.getText());
        }
    }
}

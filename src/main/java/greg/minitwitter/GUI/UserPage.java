package greg.minitwitter.GUI;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.observer.UserObserver;
import greg.minitwitter.entity.subject.UserSubject;
import greg.minitwitter.user.UserHandler;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
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

        followButton.addActionListener(e -> {
            if(followUserTextField.getText().compareTo("") != 0) {
                follow(user);
            }
        });

        tweetButton.addActionListener(e -> {
            if(tweetTextArea.getText().compareTo("") != 0) {
                userHandler.postTweetHandler(user, tweetTextArea.getText());
                tweetTextArea.setText("");
            }
        });

        // Upon window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                user.detach(UserPage.this);
                super.windowClosing(e);
            }
        });
    }
    private void follow(User user){
        if(userHandler.addFollowingHandler(user, followUserTextField.getText())){
            followingUser.setText("* " + followUserTextField.getText() + "\n" + followingUser.getText());
            followUserTextField.setText("");
            return;
        }
        System.out.println("An error occurred");
    }
    private void updatePage(User user){
        for (String userID : user.getFollowing()){
            followingUser.setText("* " + userID + "\n" + followingUser.getText());
        }
        List<String> newsFeed = user.getNewsFeed();
        ListIterator<String> iterator = newsFeed.listIterator(user.getTotalMessages());
        while(iterator.hasPrevious()){
            timelineTextArea.setText(iterator.previous() + "\n\n" + timelineTextArea.getText());
        }
    }

    @Override
    public void update(UserSubject userSubject){
        timelineTextArea.setText(((User) userSubject).getNewestTweet() + "\n\n" + timelineTextArea.getText());
    }
}

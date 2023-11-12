package greg.minitwitter.GUI;

import greg.minitwitter.entity.User;
import greg.minitwitter.entity.observer.UserObserver;
import greg.minitwitter.entity.subject.UserSubject;
import greg.minitwitter.user.UserHandler;

import javax.swing.*;

public class UserPage extends JFrame implements UserObserver {
    private JPanel UserPanel;
    private JButton followButton;
    private JTextField followUserTextField;
    private JTextArea tweetTextArea;
    private JButton tweetButton;
    private JLabel FollowingLabel;
    private JList<String> list2;
    private JLabel UserLabel;
    private JList<String> list1;
    private JPanel MainPanel;
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
        UserLabel.setText(user.getID());

        followButton.addActionListener(e -> {
            if(followUserTextField.getText().compareTo("") != 0) {
                userHandler.addFollowingHandler(user, followUserTextField.getText());
            }
        });

        tweetButton.addActionListener(e -> {
            if(tweetTextArea.getText().compareTo("") != 0) {
                userHandler.postTweetHandler(user, tweetTextArea.getText());
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        DefaultListModel<String> listModel = new DefaultListModel<>();
        list2 = new JList<>(listModel);
    }

    @Override
    public void update(UserSubject userSubject){

    }
}

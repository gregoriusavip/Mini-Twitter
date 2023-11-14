package greg.minitwitter.admin;

import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;
import greg.minitwitter.entity.Root;
import greg.minitwitter.entity.visitor.CountGroupVisitor;
import greg.minitwitter.entity.visitor.CountPositiveMessage;
import greg.minitwitter.entity.visitor.CountTotalMessages;
import greg.minitwitter.entity.visitor.CountUserVisitor;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Class to handle admin interface
 */
public class AdminHandler {
    private static volatile AdminHandler instance;  // thread safe instance
    private static final Root root = new Root();    // established only one root upon creation

    private AdminHandler() {    // private so other classes cannot create a new instance
    }
    public static AdminHandler getInstance(){
        if (instance == null){
            synchronized (AdminHandler.class) { // handle multi-threading
                if (instance == null) {
                    instance = new AdminHandler();  // create an instance when there is no instance created before
                }
            }
        }
        return instance;    // reference to this instance
    }

    /**
     * method for AdminPage to call for adding users
     * detail of addUser is implemented in the root object
     * @param userID the new user's ID
     * @param group the parent for this new user, referenced by the last selected tree node
     * @return true if operation succeed, false otherwise
     */
    public boolean addUser(String userID, Entity group){
        return root.addUser(userID, (Group) group);
    }

    /**
     * method for AdminPage to call for adding group
     * detail of addGroup is implemented in the root object
     * @param groupID the new group's ID
     * @param group the parent for this new group, referenced by the last selected tree node
     * @return true if operation succeed, false otherwise
     */
    public boolean addGroup(String groupID, Entity group){
        return root.addGroup(groupID, (Group) group);
    }

    /**
     * method for AdminPage to call for getting the total amount of users
     * detail of getTotalUser is implemented in the CountUserVisitor class
     * uses root as the base group to traverse
     * @return the total amount of users
     */
    public Integer getTotalUser(){
        return root.accept(new CountUserVisitor());
    }

    /**
     * method for AdminPage to call for getting the total amount of groups
     * detail of getTotalGroup is implemented in the CountGroupVisitor class
     * uses root as the base group to traverse
     * @return the total amount of groups
     */
    public Integer getTotalGroup(){
        return root.accept(new CountGroupVisitor());
    }

    /**
     * method for AdminPage to call for getting the total amount of messages for all users
     * detail of getTotalMessages is implemented in the CountTotalMessages class
     * uses root as the base group to traverse
     * @return the total amount of messages for all users
     */
    public Integer getTotalMessages() { return root.accept(new CountTotalMessages()); }

    /**
     * private method to handle getting the total amount of positive words for all messages
     * detail of getTotalPositiveMessages is implemented in the CountPositiveMessage class
     * uses root as the base group to traverse
     * @return the total amount of positive words for all messages
     */
    private Integer getTotalPositiveMessages() { return root.accept(new CountPositiveMessage()); }

    /**
     * method for AdminPage to call for getting the total amount of positive messages as percentage
     * the method will call both getTotalMessages and getTotalPositiveMessages for calculation
     * @return the percentage of positive messages
     */
    public Double getPositiveMessagePercentage() {
        int denominator = getTotalMessages();
        if (denominator == 0){
            return 0.0;
        }
        int numerator = getTotalPositiveMessages();
        double result = (double) numerator / denominator;
        result *= 100;
        return result;
    }

    /**
     * method for AdminPage to call for displaying the tree representation of all groups and users
     * it will go and traverse the root and call display for each entity
     * @param node the root node of the tree UI
     */
    public void Display(DefaultMutableTreeNode node){
        for(Entity entity : root.getSet()){
            entity.Display(node);
        }
    }

    /**
     * method to get the root
     * @return root object
     */
    public static Root getRoot() {
        return root;
    }
}
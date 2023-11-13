package greg.minitwitter.admin;

import greg.minitwitter.entity.Entity;
import greg.minitwitter.entity.Group;
import greg.minitwitter.entity.Root;
import greg.minitwitter.entity.visitor.CountGroupVisitor;
import greg.minitwitter.entity.visitor.CountPositiveMessage;
import greg.minitwitter.entity.visitor.CountTotalMessages;
import greg.minitwitter.entity.visitor.CountUserVisitor;

import javax.swing.tree.DefaultMutableTreeNode;

public class AdminHandler {

    private static volatile AdminHandler instance;
    private static final Root root = new Root();

    private AdminHandler() {
    }
    public static AdminHandler getInstance(){
        if (instance == null){
            synchronized (AdminHandler.class) {
                if (instance == null) {
                    instance = new AdminHandler();
                }
            }
        }
        return instance;
    }
    public boolean addUser(String userID, Entity group){
        return root.addUser(userID, (Group) group);
    }
    public boolean addGroup(String groupID, Entity group){
        return root.addGroup(groupID, (Group) group);
    }
    public Integer getTotalUser(){
        return root.accept(new CountUserVisitor());
    }
    public Integer getTotalGroup(){
        return root.accept(new CountGroupVisitor());
    }
    public Integer getTotalMessages() { return root.accept(new CountTotalMessages()); }
    private Integer getTotalPositiveMessages() { return root.accept(new CountPositiveMessage()); }
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
    public void Display(DefaultMutableTreeNode node){
        for(Entity entity : root.getSet()){
            entity.Display(node);
        }
    }
    public static Root getRoot() {
        return root;
    }
}
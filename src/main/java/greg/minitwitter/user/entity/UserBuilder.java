package greg.minitwitter.user.entity;

public class UserBuilder {
    private final String userID;
    private String groupID = "root";

    public UserBuilder(String id){
        userID = id;
    }

    public UserBuilder setGroupID(String id){
        groupID = id;
        return this;
    }

    public User build() {
        return new User(userID, groupID);
    }
}

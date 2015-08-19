package edu.acc.j2ee.hubbub1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User implements java.io.Serializable {
    private String userName;
    private Date joinDate;
    private String password;
    private final List<Post> posts = new ArrayList<>();
    
    public User(String userName, String password, Date joinDate) {
        this.userName = userName;
        this.password = password;
        this.joinDate = joinDate;
    }
    
    public User() {}

    public String getUserName() {
        return userName;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
    
    public List<Post> getPosts() {
        return posts;
    }
    
    @Override
    public String toString() {
        return userName;
    }
}

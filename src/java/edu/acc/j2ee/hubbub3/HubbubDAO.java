package edu.acc.j2ee.hubbub3;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class HubbubDAO {
    private final List<User> users = new ArrayList<>();
    private Connection connection = null;
    private PreparedStatement selectUserAndPosts = null;
    private PreparedStatement selectUser = null;
    private PreparedStatement insertPost = null;
    private static final String URL_CONNECT = "jdbc:derby://localhost:1527/sample;user=app;password=app";
    public HubbubDAO(){
        try {
            Connection conn = DriverManager.getConnection(URL_CONNECT);
            //select user and their posts
            selectUserAndPosts = conn.prepareStatement("SELECT u.username, u.joindate, u.userid, p.content, p.postdate " +
                                                           "FROM users AS u " + 
                                                                "INNER JOIN posts AS p " +
                                                                     "ON u.USERID = p.POSTID " +
                                                                        "ORDER BY p.POSTDATE DESC");
            //select user for login authentication
            selectUser = conn.prepareStatement("SELECT * " +
                                                "FROM users AS u " +
                                                    "WHERE u.USERNAME = ? AND u.PASSWORD = ?");
            
            //insert a users post
            //postid key field is autoincremented
            insertPost = conn.prepareStatement("INSERT INTO posts(AUTHOR,CONTENT,POSTDATE) " +
                                                    "VALUES(?,?,CURRENT_TIMESTAMP)");

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            
        }
        
    }
    public void addUser(User user) {
        users.add(user);
    }
    
   // public void addPost(Post post) {
    //    int author = find(post.getAuthor().getUserName());
    //    author.getPosts().add(post);
   // }
    
    public void addPost(String content, User user) {
        Post p = new Post(content, new Date(), user.getUserId());
        user.getPosts().add(p);
    }
    
    public User find(String userName) {
        for (User user : users)
            if (user.getUserName().equals(userName))
                return user;
        return null;
    }
    
    public List<Post> getSortedPosts() {
        List<Post> results = null;
        
        ResultSet resultSet = null;
        try {
            resultSet = selectUserAndPosts.executeQuery();
            results = new ArrayList<Post>();
            
            while(resultSet.next())
            {
                
                User user = new User(
                        resultSet.getString("USERNAME"),
                        resultSet.getString("PASSWORD"),
                        resultSet.getDate("JOINDATE"));
                
                //addUser(user);
                
                Post post = new Post(
                        resultSet.getString("CONTENT"),
                        resultSet.getTimestamp("POSTDATE"),
                        resultSet.getInt("AUTHOR"));
                
                //addPost(post);    
                
                results.addAll(user.getPosts());
            }
        } catch (Exception e) {
        }
        /*
        List<Post> posts = new ArrayList<>();
        for (User user : users)
            posts.addAll(user.getPosts());
        Collections.sort(posts, new PostComparator());
        */
        
        return results;
    }
    
    public User authenticate(String userName, String password) {
        User user = find(userName);
        if (user != null && user.getPassword().equals(password))
            return user;
        return null;
    }
}

package edu.acc.j2ee.hubbub3;

import java.util.Date;

public class Post implements java.io.Serializable {

    private String content;
    private Date postDate;
    //private User author;
    private int author;
    private int postId;

    public Post(String content, Date postDate, int author) {
        this.content = content;
        this.postDate = postDate;
        this.author = author;
    }

    public Post() {
    }

    public String getContent() {
        return content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public int getAuthor() {
        return author;
    }

    public int getPostId() {
        return postId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return String.format("%d characters posted by %s on %s",
                content.length(), author, postDate);
    }

    /**
     * @return the postId
     */
    /**
     * @param postId the postId to set
     */
    public void setPostId(int postId) {
        this.postId = postId;
    }
}

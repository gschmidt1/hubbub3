package edu.acc.j2ee.hubbub2;

import java.util.Comparator;

public class PostComparator implements Comparator<Post> {
    @Override
    public int compare(Post a, Post b) {
        return b.getPostDate().compareTo(a.getPostDate());
    }
}

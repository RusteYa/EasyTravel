package repositories;

import entities.Post;
import entities.User;
import helpers.DbHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rustem.
 */
public class PostRepository {
    private static PostRepository repository;
    private Connection connection;

    private PostRepository() {
        this.connection = DbHelper.getConnection();
    }

    public static PostRepository getRepository() {
        if (repository == null) {
            repository = new PostRepository();
        }
        return repository;
    }

    public int createPost(Post post) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO post (author_id, photo_path, header, content, likes, dislikes, date) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)  RETURNING id");
            st.setInt(1, post.getAuthor().getId());
            st.setString(2, post.getPhoto_path());
            st.setString(3, post.getHeader());
            st.setString(4, post.getContent());
            st.setInt(5, post.getLikes());
            st.setInt(6, post.getDislikes());
            st.setTimestamp(7, Timestamp.from(post.getDate().toInstant()));
            st.executeUpdate();
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Post> getPostsForUser(User user) {
        try {
            int userId = user.getId();
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM post WHERE author_id = ?"
            );
            st.setInt(1, userId);
            ResultSet resultSet = st.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String photoPath = resultSet.getString("photo_path");
                String header = resultSet.getString("header");
                String content = resultSet.getString("content");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                Date date = resultSet.getTimestamp("date");
                Post post = new Post(id, user, photoPath, header, content, likes, dislikes, date);
                posts.add(post);
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

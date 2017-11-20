package repositories;

import entities.Topic;
import entities.User;
import helpers.DbHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rustem.
 */
public class TopicRepository {
    private static TopicRepository repository;
    private Connection connection;

    private TopicRepository() {
        this.connection = DbHelper.getConnection();
    }

    public static TopicRepository getRepository() {
        if (repository == null) {
            repository = new TopicRepository();
        }
        return repository;
    }

    public int createTopic(Topic topic) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO topic (topicstarter_id, content, header, likes, dislikes, date) " +
                            "VALUES (?, ?, ?, ?, ?, ?)  RETURNING id");
            st.setInt(1, topic.getTopicStarter().getId());
            st.setString(2, topic.getContent());
            st.setString(3, topic.getHeader());
            st.setInt(4, topic.getLikes());
            st.setInt(5, topic.getDislikes());
            st.setTimestamp(5, Timestamp.from(topic.getDate().toInstant()));
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Topic getTopicById(int id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM topic WHERE id = ?"
            );
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int topicStarterId = resultSet.getInt("topicstarter_id");
                String content = resultSet.getString("content");
                String header = resultSet.getString("header");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                Date date = resultSet.getTimestamp("date");
                User user = UsersRepository.getRepository().getUserById(topicStarterId);
                return new Topic(id, user, content, date, header, likes, dislikes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Topic> getTopics() {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM topic"
            );
            ResultSet resultSet = st.executeQuery();
            List<Topic> topics = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int topicStarterId = resultSet.getInt("topicstarter_id");
                String content = resultSet.getString("content");
                String header = resultSet.getString("header");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                Date date = resultSet.getTimestamp("date");
                User user = UsersRepository.getRepository().getUserById(topicStarterId);
                Topic topic = new Topic(id, user, content, date, header, likes, dislikes);
                topics.add(topic);
            }
            return topics;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

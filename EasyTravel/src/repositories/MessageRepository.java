package repositories;

import entities.Message;
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
public class MessageRepository {
    private static MessageRepository repository;
    private Connection connection;

    private MessageRepository() {
        this.connection = DbHelper.getConnection();
    }

    public static MessageRepository getRepository() {
        if (repository == null) {
            repository = new MessageRepository();
        }
        return repository;
    }

    public int createMessage(Message message) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO message (author_id, date, header, content, topic_id) " +
                            "VALUES (?, ?, ?, ?, ?)  RETURNING id");
            st.setInt(1, message.getAuthor().getId());
            st.setTimestamp(2, Timestamp.from(message.getDate().toInstant()));
            st.setString(3, message.getHeader());
            st.setString(4, message.getContent());
            st.setInt(5, message.getTopic().getId());
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Message getMessageById(int id) {
        try {
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM message WHERE id = ?"
            );
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("author_id");
                Date date = resultSet.getTimestamp("date");
                String header = resultSet.getString("header");
                String content = resultSet.getString("content");
                int topicId = resultSet.getInt("topic_id");
                User user = UsersRepository.getRepository().getUserById(userId);
                Topic topic = TopicRepository.getRepository().getTopicById(topicId);
                return new Message(id, user, date, header, content, topic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getMessagesForTopic(Topic topic) {
        try {
            int topicId = topic.getId();
            PreparedStatement st = connection.prepareStatement(
                    "SELECT * FROM message WHERE topic_id = ?"
            );
            st.setInt(1, topicId);
            ResultSet resultSet = st.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date date = resultSet.getTimestamp("date");
                String header = resultSet.getString("header");
                String content = resultSet.getString("content");
                int userId = resultSet.getInt("author_id");
                User user = UsersRepository.getRepository().getUserById(userId);
                Message message = new Message(id, user, date, header, content, topic);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

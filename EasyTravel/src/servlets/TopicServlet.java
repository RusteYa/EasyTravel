package servlets;

import entities.Message;
import entities.Topic;
import entities.User;
import helpers.RenderHelper;
import org.json.JSONException;
import org.json.JSONObject;
import repositories.MessageRepository;
import repositories.TopicRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rustem.
 */
@WebServlet(name = "TopicServlet")
public class TopicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("topic_id"));
        System.out.println(id);
        Topic topic = TopicRepository.getRepository().getTopicById(id);
        String content = request.getParameter("message");
        User user = (User) request.getSession().getAttribute("current_user");
        Message msg = new Message(user, content, topic);
        msg.setId(MessageRepository.getRepository().createMessage(msg));
        JSONObject jo = new JSONObject();
        try {
            jo.put("date", new SimpleDateFormat("MMM dd, yyyy h:mm:ss a").format(msg.getDate()));
            jo.put("userName", user.getProfile().getName());
            jo.put("userPhotoPath", user.getProfile().getPhotoPath());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().print(jo.toString());
        response.getWriter().close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> context = new HashMap<>();
        int topicId = Integer.parseInt(request.getParameter("topic_id"));
        Topic topic = TopicRepository.getRepository().getTopicById(topicId);
        List<Message> messages = MessageRepository.getRepository().getMessagesForTopic(topic);
        User user = (User) request.getSession().getAttribute("current_user");
        context.put("topic", topic);
        context.put("messages", messages);
        context.put("user", user);
        RenderHelper.render(response, context, "Topic.ftl");
    }
}

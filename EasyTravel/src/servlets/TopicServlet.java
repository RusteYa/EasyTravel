package servlets;

import entities.Topic;
import entities.User;
import helpers.RenderHelper;
import repositories.TopicRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rustem.
 */
@WebServlet(name = "TopicServlet")
public class TopicServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> context = new HashMap<>();
        int topicId = Integer.parseInt(request.getParameter("topic_id"));
        Topic topic = TopicRepository.getRepository().getTopicById(topicId);
        context.put("topic", topic);
        User user = (User) request.getSession().getAttribute("current_user");
        context.put("user", user);
        RenderHelper.render(response, context, "Topic.ftl");
    }
}

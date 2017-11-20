package servlets;

import entities.Post;
import entities.User;
import helpers.RenderHelper;
import repositories.PostRepository;

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
@WebServlet(name = "PostServlet")
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> context = new HashMap<>();
        int postId = Integer.parseInt(request.getParameter("post_id"));
        Post post = PostRepository.getRepository().getPostById(postId);
        context.put("post", post);
        User user = (User) request.getSession().getAttribute("current_user");
        context.put("user", user);
        RenderHelper.render(response, context, "Post.ftl");
    }
}

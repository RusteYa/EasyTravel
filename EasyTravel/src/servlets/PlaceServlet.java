package servlets;

import entities.Place;
import entities.User;
import helpers.RenderHelper;
import repositories.PlaceRepository;

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
@WebServlet(name = "PlaceServlet")
public class PlaceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> context = new HashMap<>();
        int postId = Integer.parseInt(request.getParameter("post_id"));
        Place place = PlaceRepository.getRepository().getPlaceById(postId);
        context.put("place", place);
        User user = (User) request.getSession().getAttribute("current_user");
        context.put("user", user);
        RenderHelper.render(response, context, "Place.ftl");
    }
}

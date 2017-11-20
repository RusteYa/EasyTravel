package servlets;

import entities.User;
import helpers.RenderHelper;
import repositories.UsersRepository;

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
@WebServlet(name = "ProfileChangeServlet")
public class ProfileChangeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String fathersName = request.getParameter("fathersname");
        String email = request.getParameter("email");
        boolean b = !"".equals(name) && name != null && !"".equals(surname) && surname != null &&
                !"".equals(fathersName) && fathersName != null && !"".equals(email) && email != null;
        if (b) {
            User user = (User) request.getSession().getAttribute("current_user");
            user.getLoginData().setEmail(email);
            user.getProfile().setName(name);
            user.getProfile().setSurname(surname);
            System.out.println(fathersName);
            user.getProfile().setFathersName(fathersName);
            System.out.println(user.getProfile().getFathersName());
            UsersRepository.getRepository().updateUser(user);
            response.sendRedirect("/profile");
        } else {
            response.sendRedirect("/profilechange");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> context = new HashMap<>();
        User user = (User) request.getSession().getAttribute("current_user");
        context.put("user", user);
        RenderHelper.render(response, context, "ProfileChange.ftl");
    }
}

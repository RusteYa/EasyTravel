package servlets;

import entities.User;
import helpers.AuthHelper;
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
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsersRepository usersRepository = UsersRepository.getRepository();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean isRemember = "on".equals(request.getParameter("isRemember"));
        if (usersRepository.hasUser(login, password)) {
            User user = usersRepository.getUserByLogin(login);
            AuthHelper.login(request, user);
            if (isRemember) {
                AuthHelper.rememberUser(response, user);
            }
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> context = new HashMap<>();
        RenderHelper.render(response, context, "Auth.ftl");
    }
}

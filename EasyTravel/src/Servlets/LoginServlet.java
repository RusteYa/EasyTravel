package Servlets;

import Entities.User;
import Helpers.AuthHelper;
import Repositories.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Rustem.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsersRepository usersRepository = UsersRepository.getRepository();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isRemember = Boolean.parseBoolean(request.getParameter("isRemember"));
        if (usersRepository.hasUser(username, password)) {
            if (isRemember) {
                AuthHelper.rememberUser(response, username);
            }
            User user = usersRepository.getUser(username);
            AuthHelper.login(request, user);
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

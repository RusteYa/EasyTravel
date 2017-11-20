package servlets;

import entities.LoginData;
import entities.Profile;
import entities.User;
import helpers.AuthHelper;
import helpers.RenderHelper;
import repositories.LoginDataRepository;
import repositories.ProfileRepository;
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
@WebServlet(name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsersRepository usersRepository = UsersRepository.getRepository();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String fathers_name = request.getParameter("fathers_name");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (!"".equals(name) && name != null && !"".equals(surname) && surname != null &&
                !"".equals(fathers_name) && fathers_name != null && !"".equals(login) && login != null &&
                !"".equals(email) && email != null && !"".equals(password) && password != null) {
            if (!usersRepository.hasLogin(login)) {
                LoginData loginData = new LoginData(login, email, password, "");
                loginData.setId(LoginDataRepository.getRepository().createLoginData(loginData));
                Profile profile = new Profile(name, surname, fathers_name);
                profile.setId(ProfileRepository.getRepository().createProfile(profile));
                User user = new User(profile, loginData);
                user.setId(usersRepository.createUser(user));
                AuthHelper.login(request, user);
                response.sendRedirect("/");
            } else {
                response.sendRedirect("/registration");
            }
        } else {
            response.sendRedirect("/registration");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> context = new HashMap<>();
        RenderHelper.render(response, context, "Registration.ftl");
    }
}
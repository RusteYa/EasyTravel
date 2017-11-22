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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Pattern p = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
            Matcher m = p.matcher(email);
            p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");
            Matcher m2 = p.matcher(password);
            p = Pattern.compile("^[A-Za-z]+$");
            Matcher m3 = p.matcher(name);
            Matcher m4 = p.matcher(surname);
            Matcher m5 = p.matcher(fathers_name);
            System.out.println(m.matches());
            System.out.println(m2.matches());
            System.out.println(m3.matches());
            System.out.println(m4.matches());
            System.out.println(m5.matches());
            if (m.matches() && m2.matches() && m3.matches() && m4.matches() && m5.matches() && !usersRepository.hasLogin(login)) {
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
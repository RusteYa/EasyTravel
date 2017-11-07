package Helpers;

import Entities.User;
import Repositories.UsersRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by Rustem.
 */
public class AuthHelper {
    public static void login(HttpServletRequest request, User user) {
        request.getSession().setAttribute("current_user", user);
    }

    public static boolean isLogin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("current_user");
        return UsersRepository.getRepository().hasUser(user);
    }

    public static void rememberUser(HttpServletResponse response, String username) {
        UUID uuid = UUID.randomUUID();
        addCookie(response, "current_user", username);
        addCookie(response, "token", uuid.toString());
    }

    public static void addCookie(HttpServletResponse response, String name, String value) {
        Cookie c = new Cookie(name, value);
        c.setMaxAge(30 * 24 * 60 * 60);
        c.setPath("/");
        response.addCookie(c);
    }

    public static void removeCookies(HttpServletRequest request,
                                     HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        login(request, null);
        removeCookies(request, response);
    }
}
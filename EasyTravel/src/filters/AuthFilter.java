package filters;

import entities.User;
import helpers.AuthHelper;
import repositories.UsersRepository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Rustem.
 */
@WebFilter(filterName = "filters.AuthFilter")
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (AuthHelper.isLogin(req)) {
            chain.doFilter(request, response);
        } else {
            Cookie loginCookie = AuthHelper.getCookie(req, "current_user");
            Cookie tokenCookie = AuthHelper.getCookie(req, "token");
            if (loginCookie != null && tokenCookie != null &&
                    UsersRepository.getRepository().hasUser(loginCookie, tokenCookie)) {
                User user = UsersRepository.getRepository().getUserByLogin(loginCookie.getValue());
                AuthHelper.login(req, user);
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse) response).sendRedirect("/login");
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }
}

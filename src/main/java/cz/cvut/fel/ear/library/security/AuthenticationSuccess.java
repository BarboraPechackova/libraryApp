package cz.cvut.fel.ear.library.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.ear.library.security.model.LoginStatus;
import cz.cvut.fel.ear.library.security.model.UserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * Writes basic login/logout information into the response.
 *
 * Differs from default implementation in that it returns a custom JSON response.
 */
public class AuthenticationSuccess implements AuthenticationSuccessHandler, LogoutSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationSuccess.class);

    private final ObjectMapper mapper;

    public AuthenticationSuccess(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        final String username = getUsername(authentication);
        if (LOG.isTraceEnabled()) {
            LOG.trace("Successfully authenticated user {}", username);
        }
        final LoginStatus loginStatus = new LoginStatus(true, authentication.isAuthenticated(), username, null);
        mapper.writeValue(httpServletResponse.getOutputStream(), loginStatus);

//        httpServletRequest.getRequestDispatcher("/jakarta.faces.resource" + httpServletRequest.getServletPath())
//                .forward(httpServletRequest, httpServletResponse);
    }

    private String getUsername(Authentication authentication) {
        if (authentication == null) {
            return "";
        }
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Successfully logged out user {}", getUsername(authentication));
        }
//        final LoginStatus loginStatus = new LoginStatus(false, true, null, null);
//        mapper.writeValue(httpServletResponse.getOutputStream(), loginStatus);
        httpServletRequest.getRequestDispatcher("/jakarta.faces.resource" + httpServletRequest.getServletPath())
                .forward(httpServletRequest, httpServletResponse);
    }
}

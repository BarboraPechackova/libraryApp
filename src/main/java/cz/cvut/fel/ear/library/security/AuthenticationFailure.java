package cz.cvut.fel.ear.library.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.ear.library.security.model.LoginStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * Returns info about authentication failure.
 *
 * Differs from default implementation in that it returns a custom JSON response.
 */
public class AuthenticationFailure implements AuthenticationFailureHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFailure.class);

    private final ObjectMapper mapper;

    public AuthenticationFailure(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        LOG.debug("Login failed for user {}.", httpServletRequest.getParameter("username"));
        final LoginStatus status = new LoginStatus(false, false, null, e.getMessage());
        mapper.writeValue(httpServletResponse.getOutputStream(), status);
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Špatné uživatelské jméno nebo heslo!"));
//        httpServletRequest.getRequestDispatcher("/jakarta.faces.resource" + httpServletRequest.getServletPath())
//                .forward(httpServletRequest, httpServletResponse);
    }
}

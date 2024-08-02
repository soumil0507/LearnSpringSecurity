package com.springsecurity.springsecsection1.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        // POPULATE DYNAMIC VALUES
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = (authException!=null && authException.getMessage()!=null) ? authException.getMessage() : "Unauthorized";
        String path = request.getRequestURI();

        response.setHeader("eazybank-error-reason", "Authentication Failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

//        ADDING CUSTOM RESPONSE BODY

//        CONSTRUCT THE JSON RESPONSE
//        SET THE CONTENT TYPE
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse =
                String.format("{\"timestamp\":\"%s\", " +
                                "\"status\":\"%d\", " +
                                "\"error\": \"%s\"," +
                                "\"message\":\"%s\"," +
                                "\"path\":\"%s\"",
                        currentTimeStamp,
                        HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        message, path );
        response.getWriter().write(jsonResponse);


    }
}

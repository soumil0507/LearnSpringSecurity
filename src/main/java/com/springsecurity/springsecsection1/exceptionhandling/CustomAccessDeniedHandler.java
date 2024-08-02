package com.springsecurity.springsecsection1.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        // POPULATE DYNAMIC VALUES
        LocalDateTime currentTimeStamp = LocalDateTime.now();
        String message = (accessDeniedException!=null && accessDeniedException.getMessage()!=null) ? accessDeniedException.getMessage() : "Authorization Failed";
        String path = request.getRequestURI();

        response.setHeader("eazybank-denied-reason", "Authorization Failed");
        response.setStatus(HttpStatus.FORBIDDEN.value());

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
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        message, path );
        response.getWriter().write(jsonResponse);


    }
}

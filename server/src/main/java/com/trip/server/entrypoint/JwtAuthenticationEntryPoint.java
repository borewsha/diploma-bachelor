package com.trip.server.entrypoint;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
        throws IOException {
        log.error("Unauthorized error: {}", exception.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        var body = new HashMap<String, Object>();
        body.put("message", "Невалидный access токен");

        var mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}

package com.trip.server.entrypoint;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import com.fasterxml.jackson.databind.*;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;
import org.springframework.stereotype.*;

@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        var body = new HashMap<String, Object>();
        body.put("message", "Невалидный access токен");

        objectMapper.writeValue(response.getOutputStream(), body);
    }

}

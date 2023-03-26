package com.trip.server.handler;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.access.*;
import org.springframework.security.web.access.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
        HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException
    ) throws IOException {
        log.error("Access error: {}", accessDeniedException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        var body = new HashMap<String, Object>();
        body.put("message", "Доступ запрещен");

        var mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}

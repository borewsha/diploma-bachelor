package com.trip.server.filter;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.trip.server.provider.*;
import com.trip.server.util.*;
import lombok.*;
import org.springframework.lang.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.filter.*;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    private static final String AUTHORITIES_CLAIM = "Authorization";

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var token = getTokenFromRequest((HttpServletRequest) request);
        if (token != null && jwtProvider.validateAccessToken(token)) {
            var claims = jwtProvider.getAccessClaims(token);
            var jwtAuthentication = JwtUtil.generate(claims);
            jwtAuthentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
        }
        chain.doFilter(request, response);
    }

    @Nullable
    private String getTokenFromRequest(HttpServletRequest request) {
        var bearer = request.getHeader(AUTHORITIES_CLAIM);
        if (StringUtils.hasText(bearer) && bearer.startsWith(AUTH_HEADER_PREFIX)) {
            return bearer.substring(AUTH_HEADER_PREFIX.length());
        }
        return null;
    }

}

package com.group308.socialmedia.core.security;


import com.group308.socialmedia.core.security.conf.JwtProperties;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by isaozturk on 19.11.2018
 */
@AllArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenHelper jwtTokenHelper;

    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        if (request.getMethod().equals("POST") && request.getRequestURI().startsWith("/api/v1/auth")){
            chain.doFilter(request, response);
            return;
        }

        if (request.getRequestURI().startsWith("/chat")){  // silinecek
            chain.doFilter(request, response);
            return;
        }



        final String requestHeader = request.getHeader(jwtTokenHelper.getKeyFromProperties(JwtProperties::getHeader));

        String username = null;
        String authToken = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
            try {
                username = jwtTokenHelper.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired", e);
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtTokenHelper.validateToken(authToken, userDetails)) {

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities());

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);

    }
}

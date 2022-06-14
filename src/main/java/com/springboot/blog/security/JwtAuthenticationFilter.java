package com.springboot.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //Get JWT token from http request
        String token = getTokenFromRequest(request);
        System.out.println(token+" Authentication filter from request");

        //Validate the token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){

            //get username from token
            String username = jwtTokenProvider.getUsernameFromJWT(token);

            //load user associate with token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //set spring security context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        }


        // call doi filter method add filter chain
        filterChain.doFilter(request, response);


    }

    public String getTokenFromRequest (HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken+" getting token from request");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){

            return bearerToken.substring(7, bearerToken.length());

        }
        return null;

    }


}

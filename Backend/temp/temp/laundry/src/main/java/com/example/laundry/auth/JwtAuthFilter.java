//package com.example.laundry.auth;
//
//
//import com.example.laundry.entities.User;
//import com.example.laundry.services.JwtService;
//import com.example.laundry.services.UserService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@AllArgsConstructor
//@Data
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    @Autowired
//    JwtService jwtService;
//    @Autowired
//    UserService userService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException, ServletException
//    {
//        String authHeader = request.getHeader("Authorization");
//        String token = null;
//        String username = null;
//        if(authHeader != null && authHeader.startsWith("Bearer ")){
//            token = authHeader.substring(7);
//            username = jwtService.extractUsername(token);
//        }
//
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            User userDetails = userService.userProfile(username).get();
//            if(jwtService.validateToken(token, userDetails)){
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null);
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//
//        }
//        filterChain.doFilter(request, response);
//    }
//}

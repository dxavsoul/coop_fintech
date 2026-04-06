//package com.fintech.banking_core.config.properties;
//
//import com.fintech.banking_core.services.JwtService;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.auth.FirebaseToken;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//    private static final String AUTH_HEADER = "Authorization";
//    private static final String BEARER_PREFIX = "Bearer ";
//    private final JwtService jwtService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String authHeader = request.getHeader(AUTH_HEADER);
//        String idToken = null;
//        String username = null;
//        FirebaseToken decodedToken = null;
//
//        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
//            idToken = authHeader.substring(BEARER_PREFIX.length());
//            username = jwtService.extractUsername(idToken);
//            log.debug("JWT token: {}", idToken);
//        } else {
//            log.warn("No JWT token found in request header");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if (idToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            try {
//                decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//                log.info("JWT token verified successfully ID: {}", decodedToken.getUid());
//
//                if (decodedToken != null) {
//                    UserDetails userDetails = new User(decodedToken.getUid(), "", new ArrayList<>());
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            } catch (FirebaseAuthException e) {
//                log.error("JWT token verification failed: {}", e.getMessage());
//                SecurityContextHolder.clearContext();
//            } catch (Exception e) {
//                log.error("Error processing JWT token: {}", e.getMessage());
//                SecurityContextHolder.clearContext();
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}

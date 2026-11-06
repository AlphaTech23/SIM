package org.example.sim.config.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.sim.config.security.services.JwtService;
import org.example.sim.config.security.services.TokenBlacklistService;
import org.example.sim.config.security.services.UserInfoService;
import org.example.sim.dto.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final TokenBlacklistService tokenBlacklistService;
  private final UserInfoService userInfoService;

  private final ObjectMapper objectMapper;

  @Autowired
  public JwtAuthenticationFilter(JwtService jwtService, TokenBlacklistService tokenBlacklistService, @Lazy UserInfoService userInfoService,
  ObjectMapper objectMapper) {
    this.jwtService = jwtService;
    this.tokenBlacklistService = tokenBlacklistService;
    this.userInfoService = userInfoService;
    this.objectMapper = objectMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
  throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;

    boolean swaggerRequest = request.getRequestURI().contains("/swagger-ui/")
            || request.getRequestURI().contains("/v3/api-docs");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7);
    } else if (swaggerRequest) {
      // Token transmis initialement dans l'URL
      jwt = request.getParameter("token");
      // Sinon, récupération depuis le cookie
      if (jwt == null && request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
          if ("SWAGGER_TOKEN".equals(cookie.getName())) {
            jwt = cookie.getValue();
            break;
          }
        }
      }
    }

    if (jwt != null) {
      // Vérification blacklist
      if (tokenBlacklistService.isTokenInvalidated(jwt)) {
        sendErrorResponse(response,
        HttpStatus.UNAUTHORIZED,
        "Token has been invalidated");
        return;
      }

      try {
        username = jwtService.extractUsername(jwt);
      } catch (Exception e) {
        sendErrorResponse(response,
          HttpStatus.UNAUTHORIZED,
          "Invalid token: " + e.getMessage());
        return;
      }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userInfoService.loadUserByUsername(username);

        try {
            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
              if(swaggerRequest && jwt != null) {
                  // Création d'un cookie pour stocker le token
                  Cookie cookie = new Cookie("SWAGGER_TOKEN", jwt);
                  cookie.setHttpOnly(true);
                  cookie.setSecure(request.isSecure());
                  cookie.setPath("/sim");
                  cookie.setMaxAge(300);
                  cookie.setAttribute("SameSite", "Strict");

                  response.addCookie(cookie);
              }
            }
        } catch (Exception e) {
            sendErrorResponse(response, 
                             HttpStatus.UNAUTHORIZED, 
                             "Token validation failed: " + e.getMessage());
            return;
        }
    }
    filterChain.doFilter(request, response);
  }

  private void sendErrorResponse(HttpServletResponse response, 
                                HttpStatus status, 
                                String message) throws IOException {
      RestResponse<String> errorResponse = RestResponse.buildErrorResponse(
          status,
          message,
          null
      );

      response.setStatus(status.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
  }
}

package com.jewellery.interceptor;


import com.jewellery.AuthUtils.JwtHelper;
import com.jewellery.constant.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class UserAuthorizationInterceptor implements HandlerInterceptor {

    private final JwtHelper jwtHelper;
    private static final Logger logger = Logger.getLogger(UserAuthorizationInterceptor.class.getName());
    public UserAuthorizationInterceptor(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            UserAuthorization userAuthorization = method.getAnnotation(UserAuthorization.class);
            if(userAuthorization != null){
                String authorizationHeader = request.getHeader("Authorization");
                Role[] allowedRoles = userAuthorization.allowedRoles();
                if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
                String token = authorizationHeader.substring(7);
                if(token.isEmpty()){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
                if(!validateToken(token)){
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
               Role[] Roles = getUserRole(token);
                if(Roles != null){
                    if(validateRole(allowedRoles, Roles)){
                        return true;
                    }
                }
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        return true;
    }
    public boolean validateRole(Role[] allowedRoles, Role[] userRoles) {
        for (Role allowedRole : allowedRoles) {
           for(Role haveRole : userRoles){
               if(allowedRole == haveRole){
                   return true;
               }
           }
        }
        return false;
    }
    public boolean validateToken(String token){
        try {
            jwtHelper.validateOnlyToken(token);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred in UserAuthorizationInterceptor due to invalid token ", e);
            return false;
        }
        return true;
    }
    public Role[] getUserRole(String token) {
        try {
            return jwtHelper.getUserRolesFromToken(token).toArray(new Role[0]);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred in UserAuthorizationInterceptor due to invalid role or token ", e);
        }
        return null;
    }
}
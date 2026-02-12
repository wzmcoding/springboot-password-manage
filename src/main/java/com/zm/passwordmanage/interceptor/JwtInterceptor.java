package com.zm.passwordmanage.interceptor;


import com.zm.passwordmanage.common.Result;
import com.zm.passwordmanage.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    public static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("未登录");
            return false;
        }

        try {
            String token = auth.substring(7);
            Long userId = jwtUtil.parseToken(token);
            USER_ID.set(userId);
            return true;

        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            if ("TOKEN_EXPIRED".equals(e.getMessage())) {
                Result.fail("token已过期");
            } else {
                Result.fail("token无效");
            }

            return false;
        }
    }
}

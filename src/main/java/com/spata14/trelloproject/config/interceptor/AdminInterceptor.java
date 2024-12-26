package com.spata14.trelloproject.config.interceptor;

import com.spata14.trelloproject.common.SessionNames;
import com.spata14.trelloproject.user.UserAuth;
import com.spata14.trelloproject.user.exception.UserErrorCode;
import com.spata14.trelloproject.user.exception.UserException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UserException(UserErrorCode.LOGIN_REQUIRED);
        }

        UserAuth userAuth = (UserAuth) session.getAttribute(SessionNames.USER_ROLE);
        if (userAuth != UserAuth.ADMIN) {
            throw new UserException(UserErrorCode.PERMISSION_DENIED);
        }

        return true;
    }
}

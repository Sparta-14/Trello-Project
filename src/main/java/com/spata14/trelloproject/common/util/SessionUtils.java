package com.spata14.trelloproject.common.util;

import com.spata14.trelloproject.common.SessionNames;
import com.spata14.trelloproject.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class SessionUtils {
    private final HttpSession session;

    // 이메일 가져오기
    public String getLoginUserEmail() {
        String email = (String)session.getAttribute(SessionNames.USER_AUTH);

        if (email == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        return email;
    }

    // 동작을 하려는 유저와 현재 로그인 사용자를 비교하여 권한 확인
    public void checkAuthorization(User excuteUser) {
        String loginEmail = getLoginUserEmail(); // 현재 로그인된 사용자 이메일
        String executeEmail = excuteUser.getEmail(); // 동작을 수행하려는 사용자의 이메일

        if (!loginEmail.equals(executeEmail)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }
    }

    //세션 리로드
    public void reloadSession(String email) {
        session.setAttribute(SessionNames.USER_AUTH, email);
    }

    //세션 클리어
    public void clearSession() {
        if (session != null) {
            session.invalidate();
        }
    }
}

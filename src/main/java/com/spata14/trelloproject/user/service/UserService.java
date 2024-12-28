package com.spata14.trelloproject.user.service;

import com.spata14.trelloproject.common.SessionNames;
import com.spata14.trelloproject.common.util.PasswordEncoder;
import com.spata14.trelloproject.common.util.SessionUtils;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.UserStatus;
import com.spata14.trelloproject.user.dto.UserRequestDto;
import com.spata14.trelloproject.user.dto.UserResponseDto;
import com.spata14.trelloproject.user.exception.UserErrorCode;
import com.spata14.trelloproject.user.exception.UserException;
import com.spata14.trelloproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SessionUtils sessionUtils;

    /**
     * 유저 생성
     * @param dto {@link UserRequestDto} - name, email, password, auth = ADMIN or USER
     */
    public void createUser(UserRequestDto dto) {
        boolean duplicated = userRepository.findByEmail(dto.getEmail()).isPresent();
        if (duplicated) {
            throw new UserException(UserErrorCode.EMAIL_DUPLICATE);
        }

        userRepository.save(new User(dto.getName(), dto.getEmail(), PasswordEncoder.encode(dto.getPassword()), dto.getAuth()));
    }

    /**
     * 로그인
     * @param dto {@link UserRequestDto} - email, password
     */
    public void login(UserRequestDto dto, HttpServletRequest request) {
        User findUser = userRepository.findByEmailOrElseThrow(dto.getEmail());

        if (findUser.getStatus().equals(UserStatus.DEACTIVATED)) {
            throw new UserException(UserErrorCode.USER_DEACTIVATED);
        }

        passwordCheck(dto.getPassword(), findUser);

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionNames.USER_AUTH, dto.getEmail());
    }

    /**
     * 유저 상태 변경 (탈퇴 처리)
     * @param id - 유저 ID
     * @param dto {@link UserRequestDto} - password
     * @return String ("회원 탈퇴 성공 ! 세션을 초기화 합니다.")
     */
    public String patchUserStatus(Long id, UserRequestDto dto) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        sessionUtils.checkAuthorization(findUser);
        passwordCheck(dto.getPassword(), findUser);

        if (findUser.getStatus() != UserStatus.DEACTIVATED) {
            findUser.deactivateUser();
            userRepository.save(findUser);
        }

        sessionUtils.clearSession();
        return "회원 탈퇴 성공 ! 세션을 초기화 합니다.";
    }

    /**
     * 유저 조회
     * @param id - 유저 ID
     * @return {@link UserResponseDto} - id, name, email, status, auth, createdAt, modifiedAt
     */
    public UserResponseDto findUser(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);
        return UserResponseDto.toDto(findUser);
    }

    /**
     * 이메일로 유저 조회 (다른 서비스 클래스에서 사용)
     * @param email - 유저 이메일
     * @return {@link User}
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmailOrElseThrow(email);
    }


    /**
     * 비밀번호 검증
     * @param password - 입력받은 비밀번호
     * @param user - 비밀번호 일치 여부를 확인 할 유저
     */
    private void passwordCheck(String password, User user) {
        if (!PasswordEncoder.matches(password, user.getPassword())) {
            throw new UserException(UserErrorCode.PASSWORD_INCORRECT);
        }
    }
}

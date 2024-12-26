package com.spata14.trelloproject.user.dto;

import com.spata14.trelloproject.user.UserAuth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "이름은 필수 입니다.")
    @Size(min = 2, max = 15, message = "최소 2자에서 최대 15자까지 입력해주세요.")
    private final String name;

    @NotBlank(message = "이메일은 필수 입니다.")
    @Pattern(regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$", message = "올바르지 않은 이메일 형식입니다.")
    private final String email;

    @NotBlank(message = "비밀번호 입력은 필수 입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 최소 8자 이상이며, 20자 까지 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "대문자, 소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다.")
    private final String password;

    @NotNull(message = "계정 권한을 설정해주세요. (ADMIN, USER)")
    private final UserAuth auth;
}

package com.gple.backend.domain.user.controller.dto.web.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserProfileRequest {
    @Pattern(regexp = "^[가-힣]{2,5}$\n", message = "한글로 이루어진 성과 이름을 입력해야합니다.")
    private String name;

    @Pattern(regexp = "^[1-3][1-4](0[1-9]|[1-9][0-9])$\n", message = "유효한 학번을 입력해주세요.")
    private String number;

    private MultipartFile file;
}

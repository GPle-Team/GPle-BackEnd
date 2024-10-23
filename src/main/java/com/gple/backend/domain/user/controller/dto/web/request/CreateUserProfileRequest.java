package com.gple.backend.domain.user.controller.dto.web.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateUserProfileRequest {
    @Pattern(regexp = "^[가-힣]+$", message = "한글로 이루어진 본명을 입력해야합니다.")
    private String username;

    @Pattern(regexp = "^[1-3][1-4][0-9]{2}$", message = "형식이 일치하지 않는 학번입니다.")
    private String studentNumber;
}

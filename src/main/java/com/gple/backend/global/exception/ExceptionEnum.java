package com.gple.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    // JWT
    EXPIRED_JWT("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_JWT("형식이 일치하지 않는 토큰입니다.", HttpStatus.FORBIDDEN),
    MALFORMED_JWT("올바르지 않은 구성의 토큰입니다.", HttpStatus.FORBIDDEN),
    INVALID_SIGNATURE("서명을 확인할 수 없는 토큰입니다.", HttpStatus.FORBIDDEN),
    INVALID_REFRESH_TOKEN("유효하지 않은 리프레시 토큰입니다.", HttpStatus.UNAUTHORIZED),

    // 게시글
    NOT_FOUND_POST("해당하는 게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    NOT_FOUND_EMOJI("해당하는 반응을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 회원
    NOT_FOUND_USER("해당하는 회원을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_FORMAT_EMAIL("gsm.hs.kr 도메인의 이메일만 사용할 수 있습니다.", HttpStatus.BAD_REQUEST),

    // 파일
    NOT_FOUND_FILE("해당하는 파일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    MAX_LENGTH_FILE("파일은 최대 3개까지 업로드할 수 있습니다.", HttpStatus.BAD_REQUEST),
    UNEXPECTED_IMAGE_CONVERT("이미지를 변환하는 중에 알 수 없는 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_FILE_FORMAT("해당 파일 확장자를 지원하지 않습니다.", HttpStatus.BAD_REQUEST),

    // AWS
    INVALID_INPUT_OUTPUT("입출력 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_AWS_S3("AWS S3에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_AWS("AWS에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_AWS_SDK_CLIENT("AWS SDK에서 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // GCP
    INVALID_ID_TOKEN("ID 토큰을 받아오는데 실패하였습니다.", HttpStatus.BAD_REQUEST),
    INVALID_GOOGLE_CLIENT_ID("클라이언트 ID가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_GOOGLE_ACCESS_TOKEN("유효하지 않은 구글 엑세스 토큰입니다.", HttpStatus.BAD_REQUEST),

    UNEXPECTED_EXCEPTION("예상치 못한 오류가 발생하였습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}

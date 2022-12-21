package me.songha.tutorial.handler;

import me.songha.tutorial.dto.ErrorDto;
import me.songha.tutorial.exception.DuplicateMemberException;
import me.songha.tutorial.exception.NotFoundMemberException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 중복된 username의 가입 요청 409
     */
    @ResponseStatus(CONFLICT)
    @ExceptionHandler(value = DuplicateMemberException.class)
    @ResponseBody
    protected ErrorDto badRequest(RuntimeException ex, WebRequest request) {
        return new ErrorDto(CONFLICT.value(), ex.getMessage());
    }

    /**
     * 접근 권한이 없을 경우 403
     */
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(value = {NotFoundMemberException.class, AccessDeniedException.class})
    @ResponseBody
    protected ErrorDto forbidden(RuntimeException ex, WebRequest request) {
        return new ErrorDto(FORBIDDEN.value(), ex.getMessage());
    }
}
package ee.kadaja.shop.controller.handler;


import java.util.UUID;

import javax.validation.ConstraintViolationException;

import ee.kadaja.shop.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;


@Slf4j
@ControllerAdvice
public class RestErrorAdvisor {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> processIllegalArgumentException(IllegalArgumentException ex) {
        val uniqueId = UUID.randomUUID();
        val responseBody = ErrorResponseDto.builder().message(ex.getMessage())
                                           .uniqueId(uniqueId.toString()).build();
        log.error("Exception {} occurred", uniqueId, ex);
        return new ResponseEntity<>(responseBody, NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> processConstraintViolationException(
            ConstraintViolationException exception) {
        val uniqueId = UUID.randomUUID();
        val messageBuilder = new StringBuilder();
        exception.getConstraintViolations().forEach(
                cv -> messageBuilder.append(cv.getPropertyPath())
                                    .append(": ")
                                    .append(cv.getMessage())
                                    .append(", invalid value: ")
                                    .append(cv.getInvalidValue())
                                    .append("."));
        val responseBody = ErrorResponseDto.builder().message(messageBuilder.toString())
                                           .uniqueId(uniqueId.toString()).build();
        log.error("Exception {} occurred", uniqueId, exception);
        return new ResponseEntity<>(responseBody, UNPROCESSABLE_ENTITY);
    }
}

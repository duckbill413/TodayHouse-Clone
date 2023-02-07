package server.team_a.todayhouse.src.base.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.src.base.dto.ErrorResponse;
import server.team_a.todayhouse.src.product.ProductController;
import server.team_a.todayhouse.src.base.dto.Error;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// INFO: 전체 Controller Exception handler
//@RestControllerAdvice(basePackageClasses = ProductController.class) // 해당하는 Controller 에만 적용
@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getClass().getName());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity exception(AccessDeniedException e,
                                    HttpServletRequest httpServletRequest) {
        Error error = Error.builder()
                .message(e.getMessage()).build();
        Object ex = e.getLocalizedMessage();
        ErrorResponse errorResponse = new ErrorResponse(BaseResponseStatus.INVALID_METHOD);
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setErrorList(List.of(error));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                                 HttpServletRequest httpServletRequest) {
        String method = e.getMethod();
        String message = e.getMessage();
        String supportedMethod = e.getSupportedMethods()[0];
        Error error = new Error(method, message, supportedMethod);

        ErrorResponse errorResponse = new ErrorResponse(BaseResponseStatus.INVALID_METHOD);
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setErrorList(List.of(error));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity baseException(BaseException e, HttpServletRequest httpServletRequest){
        BaseResponse baseResponse = new BaseResponse(e.getStatus());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                          HttpServletRequest httpServletRequest) {
        List<Error> errorList = new ArrayList<>();
        BindingResult bindingResult = e.getBindingResult();

        System.out.println("MethodArgumentNotValidException Error");
        bindingResult.getAllErrors().forEach(objectError -> {
            FieldError fieldError = (FieldError) objectError;

            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            String value = "";
            if (fieldError.getRejectedValue() != null)
                value = fieldError.getRejectedValue().toString();

            System.out.println(fieldName + "\t" + message + "\t" + value);

            Error error = new Error(fieldName, message, value);
            errorList.add(error);
        });
        ErrorResponse errorResponse = new ErrorResponse(BaseResponseStatus.INVALID_VALIDATION_REQUEST);
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setErrorList(errorList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException e,
                                                       HttpServletRequest httpServletRequest) {
        System.out.println("ConstraintViolationException Error");
        List<Error> errorList = new ArrayList<>();
        e.getConstraintViolations().forEach(constraintViolation -> {
            Stream<Path.Node> stream = StreamSupport.stream(
                    constraintViolation.getPropertyPath().spliterator(), false);
            List<Path.Node> list = stream.collect(Collectors.toList());

            String fieldName = list.get(list.size()-1).getName();
            String message = constraintViolation.getMessage();
            String invalidValue = constraintViolation.getInvalidValue().toString();
            System.out.println(fieldName + "\t" + message + "\t" + invalidValue);

            Error error = new Error(fieldName, message, invalidValue);
            errorList.add(error);
        });
        ErrorResponse errorResponse = new ErrorResponse(BaseResponseStatus.INVALID_VALIDATION_REQUEST);
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setErrorList(errorList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                  HttpServletRequest httpServletRequest) {
        System.out.println("MissingServletRequestParameterException");

        List<Error> errorList = new ArrayList<>();

        String fieldName = e.getParameterName();
        String fieldType = e.getParameterType();
        String invalidValue = e.getMessage();

        System.out.println(fieldName + "\t" + fieldType + "\t" + invalidValue);
        errorList.add(new Error(fieldName, fieldType, invalidValue));

        ErrorResponse errorResponse = new ErrorResponse(BaseResponseStatus.INVALID_VALIDATION_REQUEST);
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setErrorList(errorList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}

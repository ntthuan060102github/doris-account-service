package doris.dorisaccountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import doris.dorisaccountservice.dto.request.RegisterRequest;
import doris.dorisaccountservice.exception.ExistedEmailException;
import doris.dorisaccountservice.service.UserAuthenticationService;
import doris.dorisaccountservice.util.response.RestResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/authentication")
@RestControllerAdvice
public class UserAuthenticationController {
    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping("/register")
    public RestResponse register(@Valid @RequestBody RegisterRequest registerRequest)
    {
        try {
            this.userAuthenticationService.register(registerRequest);
            return new RestResponse();
        }
        catch (ExistedEmailException ex)
        {
            return new RestResponse().definedError("Oops! Email này đã được sử dụng. Hãy thử sử dụng một email khác để tiếp tục cuộc hành trình của bạn!");
        }
        catch (Exception ex)
        {
            return new RestResponse().internalServerError().setData(ex);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        return new RestResponse().validationFailed().setData(ex.getFieldError());
    }

    @ExceptionHandler(Exception.class)
    public RestResponse handleDefaultException(Exception ex)
    {
        return new RestResponse().internalServerError().setData(ex.getMessage());
    }
}

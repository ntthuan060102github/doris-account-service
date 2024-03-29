package doris.dorisaccountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import doris.dorisaccountservice.dto.TokenPairDto;
import doris.dorisaccountservice.dto.request.LoginRequest;
import doris.dorisaccountservice.dto.request.RegisterRequest;
import doris.dorisaccountservice.exception.ExistedEmailException;
import doris.dorisaccountservice.service.AccountService;
import doris.dorisaccountservice.util.response.RestResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/authentication")
@RestControllerAdvice
public class AccountController {
    @Autowired
    private AccountService userAuthenticationService;

    @PostMapping("/register")
    public RestResponse register(@Valid @RequestBody RegisterRequest registerRequest)
    {
        try {
            this.userAuthenticationService.register(registerRequest);
            return new RestResponse().success().setMessage("Đăng ký tài khoản thành công!");
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

    @PostMapping("/login")
    public RestResponse login(@Valid @RequestBody LoginRequest loginRequest)
    {
        try {
            TokenPairDto tokenPair = this.userAuthenticationService.login(loginRequest);

            if (tokenPair == null)
            {
                return new RestResponse().definedError("Email hoặc mật khẩu không chính xác. Hãy thử lại!");
            }
            else 
            {
                return new RestResponse().setData(tokenPair);
            }
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

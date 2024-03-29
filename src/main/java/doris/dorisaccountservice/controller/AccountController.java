package doris.dorisaccountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import doris.dorisaccountservice.dto.request.LoginRequest;
import doris.dorisaccountservice.dto.request.RegisterRequest;
import doris.dorisaccountservice.dto.response.LoginResponse;
import doris.dorisaccountservice.exception.ExistedEmailException;
import doris.dorisaccountservice.service.IAccountService;
import doris.dorisaccountservice.util.response.RestResponse;
import jakarta.validation.Valid;

import doris.dorisaccountservice.enums.LoginResponseStatus;


@RestController
@RequestMapping("/api/v1/authentication")
@RestControllerAdvice
public class AccountController {
    @Autowired
    private IAccountService userAuthenticationService;

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
    }

    @PostMapping("/login")
    public RestResponse login(@Valid @RequestBody LoginRequest loginRequest)
    {
        LoginResponse response = this.userAuthenticationService.login(loginRequest);

        switch (response.getType()) {
            case LoginResponseStatus.OK:
                return new RestResponse().setData(response.getTokenPair());
            case LoginResponseStatus.INVALUE_EMAIL_PASSWORD:
                return new RestResponse().definedError().setMessage("Hmmm... có vẻ như email hoặc mật khẩu bạn nhập không đúng.");
            case LoginResponseStatus.BLOCKED:
                return new RestResponse().definedError().setMessage("Tài khoản này đã bị khóa. Đừng lo lắng, chúng tôi luôn ở đây để giúp đỡ. Hãy liên hệ nếu bạn cần.");
            case LoginResponseStatus.UNVERIFIED:
                return new RestResponse().definedError().setMessage("Opps! Tài khoản của bạn chưa được xác minh. Vui lòng kiểm tra email của bạn và hoàn tất quá trình xác minh để tiếp tục.");
            default:
                return new RestResponse().internalServerError();    
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

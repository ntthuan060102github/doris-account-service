package doris.dorisaccountservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import doris.dorisaccountservice.dto.TokenPairDto;
import doris.dorisaccountservice.dto.UserDetailsImp;
import doris.dorisaccountservice.dto.request.LoginRequest;
import doris.dorisaccountservice.dto.request.RegisterRequest;
import doris.dorisaccountservice.dto.response.LoginResponse;
import doris.dorisaccountservice.enums.AccountStatus;
import doris.dorisaccountservice.enums.LoginResponseStatus;
import doris.dorisaccountservice.exception.ExistedEmailException;
import doris.dorisaccountservice.model.User;
import doris.dorisaccountservice.repository.UserRepository;
import doris.dorisaccountservice.security.JwtTokenProvider;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountService implements IAccountService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    final String otpTemplate = "RegisterEmail.html";

    @Override
    public User register(RegisterRequest registerRequest) throws ExistedEmailException {
        if (userRepository.existsByEmail(registerRequest.getEmail()))
        {
            throw new ExistedEmailException("Email is already taken!");
        }

        User user = registerRequest.toUserWithoutPassword();

        user.setPassword(this.passwordEncoder.encode(registerRequest.getPassword()));
        user = userRepository.save(user);
        
        if (user != null)
        {
            this.sendOtpToEmail(user.getEmail(), "123");
        }

        return user;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest)
    {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        LoginResponse response = new LoginResponse();

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
        {
            response.setType(LoginResponseStatus.INVALUE_EMAIL_PASSWORD);
            return response;
        }
        else 
        {
            UserDetailsImp userDetail = new UserDetailsImp(user);

            if(!userDetail.isEnabled())
            {
                response.setType(LoginResponseStatus.UNVERIFIED);
                return response;
            }
            else if(!userDetail.isAccountNonLocked())
            {
                response.setType(LoginResponseStatus.BLOCKED);
                return response;
            }
            else
            {
                TokenPairDto tokenPair = this.generateTokenPair(userDetail);
                response.setType(LoginResponseStatus.OK);
                response.setTokenPair(tokenPair);
    
                return response;
            }
        }
    }

    private TokenPairDto generateTokenPair(UserDetailsImp userDetail)
    {
        TokenPairDto tokenPair = new TokenPairDto(
            this.jwtTokenProvider.generateToken(userDetail, 3*24*60*60),
            this.jwtTokenProvider.generateToken(userDetail, 30*24*60*60)
        );

        return tokenPair;
    }

    @Async
    private void sendOtpToEmail(String email, String otp)
    {
        this.emailService.sendEmailWithTemple(
            email, 
            "[Doris] - Xác thực tài khoản.", 
            this.otpTemplate, 
            Map.of("otp", otp)
        );
    }
}

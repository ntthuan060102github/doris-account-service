package doris.dorisaccountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import doris.dorisaccountservice.dto.request.RegisterRequest;
import doris.dorisaccountservice.exception.ExistedEmailException;
import doris.dorisaccountservice.model.User;
import doris.dorisaccountservice.repository.UserRepository;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationServiceImp implements UserAuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("null")
    @Override
    public User register(RegisterRequest registerRequest) throws ExistedEmailException {
        if (userRepository.existsByEmail(registerRequest.getEmail()))
        {
            throw new ExistedEmailException("Email is already taken!");
        }

        User user = User
            .builder()
            .email(registerRequest.getEmail())
            .firstName(registerRequest.getFirstName())
            .lastName(registerRequest.getLastName())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .birthday(registerRequest.getBirthday())
            .phoneNumber(registerRequest.getPhoneNumber())
            .gender(registerRequest.getGender())
            .build();
        return userRepository.save(user);
    }

}

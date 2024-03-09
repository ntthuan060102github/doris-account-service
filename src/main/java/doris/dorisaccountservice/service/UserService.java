package doris.dorisaccountservice.service;

import org.springframework.stereotype.Service;

import doris.dorisaccountservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
}

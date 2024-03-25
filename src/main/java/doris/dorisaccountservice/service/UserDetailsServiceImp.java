package doris.dorisaccountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import doris.dorisaccountservice.dto.UserDetailsImp;
import doris.dorisaccountservice.model.User;
import doris.dorisaccountservice.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username);

        if (user == null)
        {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsImp(user);
    }
    
}

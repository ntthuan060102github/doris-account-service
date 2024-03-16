package doris.dorisaccountservice.service;

import doris.dorisaccountservice.dto.request.RegisterRequest;
import doris.dorisaccountservice.exception.ExistedEmailException;
import doris.dorisaccountservice.model.User;

public interface UserAuthenticationService {
    public User register(RegisterRequest registerRequest) throws ExistedEmailException;
}

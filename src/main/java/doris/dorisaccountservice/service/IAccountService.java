package doris.dorisaccountservice.service;

import doris.dorisaccountservice.dto.TokenPairDto;
import doris.dorisaccountservice.dto.request.LoginRequest;
import doris.dorisaccountservice.dto.request.RegisterRequest;
import doris.dorisaccountservice.exception.ExistedEmailException;
import doris.dorisaccountservice.model.User;

public interface IAccountService {
    public User register(RegisterRequest registerRequest) throws ExistedEmailException;
    public TokenPairDto login(LoginRequest loginRequest);
}

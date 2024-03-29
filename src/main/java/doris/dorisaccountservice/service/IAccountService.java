package doris.dorisaccountservice.service;

import doris.dorisaccountservice.dto.request.LoginRequest;
import doris.dorisaccountservice.dto.request.RegisterRequest;
import doris.dorisaccountservice.dto.response.LoginResponse;
import doris.dorisaccountservice.exception.ExistedEmailException;
import doris.dorisaccountservice.model.User;

public interface IAccountService {
    public User register(RegisterRequest registerRequest) throws ExistedEmailException;
    public LoginResponse login(LoginRequest loginRequest);
}

package doris.dorisaccountservice.dto.response;

import doris.dorisaccountservice.dto.TokenPairDto;
import doris.dorisaccountservice.enums.LoginResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private LoginResponseStatus type;
    private TokenPairDto tokenPair;
}

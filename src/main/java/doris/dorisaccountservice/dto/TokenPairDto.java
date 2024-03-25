package doris.dorisaccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenPairDto {
    private String accessToken;
    private String refreshToken;
}

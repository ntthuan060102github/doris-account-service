package doris.dorisaccountservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @Email(message = "Email address is not valid!")
    @Size(max = 100, message = "Email address must be less than 100 characters!")
    @NotBlank(message = "Email address cannot be empty!")
    private String email;
    
    @NotBlank(message = "Password cannot be empty!")
    private String password;
}

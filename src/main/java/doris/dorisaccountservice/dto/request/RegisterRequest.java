package doris.dorisaccountservice.dto.request;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @Email(message = "Email address is not valid!")
    @Size(max = 100, message = "Email address must be less than 100 characters!")
    @NotBlank(message = "Email address cannot be empty!")
    private String email;
    
    @NotBlank(message = "First name cannot be empty!")
    @Size(max = 100, message = "First name must be less than 100 characters!")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty!")
    @Size(max = 100, message = "Last name must be less than 100 characters!")
    private String lastName;

    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", 
        message = "Password must be at least 8 characters long and include at least one lowercase letter, one uppercase letter, and one number.")
    @NotBlank(message = "Password cannot be empty!")
    private String password;

    @NotNull(message = "Birthdays must be valuable!")
    private Date birthday;
    
    @Pattern(regexp = "^[0-9\\-\\+]{9,15}$", message = "Phone number format is not valid!")
    private String phoneNumber;

    @Max(value = 1, message = "Gender must be within the following values: male (1), female (0), undisclosed (-1)")
    @Min(value = -1, message = "Gender must be within the following values: male (1), female (0), undisclosed (-1)")
    private Integer gender;
}

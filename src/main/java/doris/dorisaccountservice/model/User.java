package doris.dorisaccountservice.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    @Email
    @Size(max = 100)
    private String email;

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(max = 100)
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "birthday", nullable = true)
    private Date birthday;

    @Column(name = "avatar_url", nullable = true)
    @Size(max = 500)
    private String avatarUrl;

    @Column(name = "phone_number", nullable = true)
    @Size(max = 15)
    private String phoneNumber;

    @Column(name = "gender")
    @Max(1)
    @Min(-1)
    private Integer gender;
}

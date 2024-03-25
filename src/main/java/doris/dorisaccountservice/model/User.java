package doris.dorisaccountservice.model;

import java.sql.Date;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import doris.dorisaccountservice.enums.AccountStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.NonNull;
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NonNull
    private AccountStatus status;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}

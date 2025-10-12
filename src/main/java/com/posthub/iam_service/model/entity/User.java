package com.posthub.iam_service.model.entity;

import com.posthub.iam_service.model.enums.RegistrationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 30)
    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Size(max = 80)
    @Column(length = 80, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(length = 50, unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus registrationStatus;

    @Column()
    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private Boolean deleted = false;

}

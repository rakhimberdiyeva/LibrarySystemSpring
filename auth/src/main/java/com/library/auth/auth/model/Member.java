package com.library.auth.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, length = 128, nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "fullname", length = 128, nullable = false)
    private String fullname;

    @Column(name = "email", unique = true, length = 128, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private MemberRole role = MemberRole.MEMBER;

    @Column(name = "is_active", nullable = false)
    private boolean iaActive = true;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Member(@NotBlank @Size(max = 128) String username, @Nullable String passwordHash, @NotBlank @Size(max = 128) String fullname, @NotBlank @Email @Size(max = 128) String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullname = fullname;
        this.email = email;
    }

    public Member(String username, @Nullable String passwordHash, String fullname, String email, MemberRole memberRole) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullname = fullname;
        this.email = email;
        this.role = memberRole;
    }
}

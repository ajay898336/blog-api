package com.talentica.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users",uniqueConstraints={@UniqueConstraint(columnNames="email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer id;
    @NotEmpty
    @Size(min=3, message = "First Name should have minimum 3 characters")
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @NotEmpty
    @Size(min=3, message = "Last Name should have minimum 3 characters")
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", insertable = false)
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy="to" , fetch = FetchType.LAZY)
    private List<Followers> followers;

    @OneToMany(mappedBy="from", fetch = FetchType.LAZY)
    private List<Followers> following;

    @OneToMany(mappedBy="user", targetEntity = Likes.class, fetch = FetchType.EAGER)
    private List<Likes> likes;
    private int followersCount;
    private int followingCount;
}

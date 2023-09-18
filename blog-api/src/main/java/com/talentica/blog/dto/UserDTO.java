package com.talentica.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Integer id;
    @NotEmpty
    @Size(min=3, message = "First Name should have minimum 3 characters")
    private String firstName;
    @NotEmpty
    @Size(min=3, message = "Last Name should have minimum 3 characters")
    private String lastName;
    @Email
    private String email;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private int followersCount;
    private int followingCount;
}

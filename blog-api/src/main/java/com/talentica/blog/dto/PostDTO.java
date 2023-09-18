package com.talentica.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Integer id;
    @NotEmpty
    @Size(min=10, message = "Title should have minimum 10 characters")
    private String title;
    @NotEmpty
    @Size(min=20, message = "Content should have minimum 20 characters")
    private String content;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}

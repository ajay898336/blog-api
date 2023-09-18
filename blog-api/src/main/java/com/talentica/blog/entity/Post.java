package com.talentica.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "posts",uniqueConstraints={@UniqueConstraint(columnNames="title")})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false, updatable = false)
    private Integer id;
    @NotEmpty
    @Size(min=10, message = "Title should have minimum 10 characters")
    @Column(name = "title",nullable = false)
    private String title;
    @NotEmpty
    @Size(min=20, message = "Content should have minimum 20 characters")
    @Column(name = "content",nullable = false)
    private String content;

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

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy="post", targetEntity = Likes.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Likes> likes;

}

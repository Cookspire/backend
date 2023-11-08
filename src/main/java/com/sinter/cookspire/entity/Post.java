package com.sinter.cookspire.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "post")
@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String content;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @Column(nullable = true, length = 250)
    private String imageName;

    @Column(nullable = true, length = 50)
    private String imageType;

    @Column(nullable = true)
    @Lob
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Users users;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Recipe recipe;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<PostInteraction> postInteractions;
}

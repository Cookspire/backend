package com.sinter.cookspire.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NonNull
  @Column(length = 50)
  private String username;

  @NonNull
  @Column(length = 200)
  private String email;

  @NonNull
  private String salt;

  @NonNull
  private String password;

  @NonNull
  @Column(length = 150)
  private String bio;

  @NonNull
  @Column(length = 70)
  private String country;

  private boolean isVerified;

  @CreationTimestamp
  private LocalDateTime createdOn;

  @UpdateTimestamp
  private LocalDateTime updatedOn;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private List<Post> posts;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private List<Bookmark> bookmarks;

  @OneToMany(mappedBy = "followerUsers", cascade = CascadeType.ALL)
  private List<Follower> followers;

  @OneToMany(mappedBy = "followeeUsers", cascade = CascadeType.ALL)
  private List<Follower> followee;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private List<Comment> comments;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private List<Replies> replies;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private List<RefreshToken> refreshTokens;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private List<PostInteraction> postInteractions;

}

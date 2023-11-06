package com.sinter.cookspire.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.sinter.cookspire.utils.Level;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "recipe")
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(length = 3000)
    private String instruction;

    @NonNull
    @Column(length = 150)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Level level;

    @Column(length = 300)
    private String description;

    @Column(length = 100)
    private String cuisine;

    @Column(length = 100)
    private String course;

    @Column(length = 300)
    private String diet;

    private Integer prep_time_mins;

    private Integer cook_time_mins;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    private boolean is_Verified;

    @Column(nullable = true, length = 250)
    private String imageName;

    @Column(nullable = true, length = 50)
    private String imageType;

    @Column(nullable = true)
    @Lob
    private byte[] imageData;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;

    @OneToMany(mappedBy = "recipes", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

}

package com.sinter.cookspire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinter.cookspire.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}

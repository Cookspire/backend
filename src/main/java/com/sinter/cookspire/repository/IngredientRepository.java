package com.sinter.cookspire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinter.cookspire.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}

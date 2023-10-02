package com.sinter.cookspire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Ingredient;
import com.sinter.cookspire.entity.Recipe;
@Transactional
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByRecipes(Recipe recipe);

}

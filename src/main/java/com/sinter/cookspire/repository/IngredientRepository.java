package com.sinter.cookspire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Ingredient;
import com.sinter.cookspire.entity.Recipe;

@Transactional
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByRecipes(Recipe recipe);

    @Query(nativeQuery = true, value = "select * from ingredient where item ILIKE %:search% LIMIT 10")
    List<Ingredient> filterIngredient(@Param("search") String search);

}

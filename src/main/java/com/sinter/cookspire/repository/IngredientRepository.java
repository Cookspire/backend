package com.sinter.cookspire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Ingredient;
@Transactional
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}

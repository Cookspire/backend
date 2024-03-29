package com.sinter.cookspire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.Recipe;

@Transactional
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByPost(Post post);

    @Query(nativeQuery = true, value = "Select DISTINCT(cuisine) from recipe")
    List<String> findAllCuisines();

    Page<Recipe> findByCuisineIgnoreCase(String cuisine, Pageable pagination);

    @Query(nativeQuery = true, value = "Select DISTINCT(course) from recipe")
    List<String> findAllCourses();

    Page<Recipe> findByCourseIgnoreCase(String course, Pageable pagination);

    @Query(nativeQuery = true, value = "select recipe.name from recipe where recipe.name ILIKE %:search% LIMIT 5")
    List<String> filterRecipeAndIngredient(@Param("search") String search);

    @Query(nativeQuery = true, value = "select * from recipe where name ILIKE %:search%")
    Page<Recipe> filterGlobalRecipe(@Param("search") String search, Pageable pagination);

    @Query(nativeQuery = true, value = "select * from recipe where (lower(diet) = :diet or :diet is NULL) AND (cook_time_mins between :from_time AND :to_time) AND (lower(cuisine)=:cuisine or :cuisine is NULL) AND (lower(course)=:course or :course is NULL) AND (name ILIKE %:query% or :query is NULL)")
    Page<Recipe> filterRecipe(@Param("diet") String diet,
            @Param("from_time") int from_time, @Param("to_time") int to_time, @Param("course") String course,
            @Param("cuisine") String cuisine, @Param("query") String query,
            Pageable pagination);

}

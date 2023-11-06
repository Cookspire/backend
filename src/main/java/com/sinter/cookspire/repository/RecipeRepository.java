package com.sinter.cookspire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.Recipe;

@Transactional
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByPost(Post post);

    @Query(nativeQuery = true, value = "Select DISTINCT(cuisine) from recipe")
    List<String> findAllCuisines();

    List<Recipe> findByCuisineIgnoreCase(String cuisine);

    @Query(nativeQuery = true, value = "Select DISTINCT(course) from recipe")
    List<String> findAllCourses();

    List<Recipe> findByCourseIgnoreCase(String course);

}

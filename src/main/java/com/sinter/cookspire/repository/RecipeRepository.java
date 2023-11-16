package com.sinter.cookspire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.sinter.cookspire.entity.Post;
import com.sinter.cookspire.entity.Recipe;
import com.sinter.cookspire.entity.Users;

@Transactional
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByPost(Post post);

    @Query(nativeQuery = true, value = "Select DISTINCT(cuisine) from recipe")
    List<String> findAllCuisines();

    List<Recipe> findByCuisineIgnoreCase(String cuisine, Pageable pagination);

    @Query(nativeQuery = true, value = "Select DISTINCT(course) from recipe")
    List<String> findAllCourses();

    List<Recipe> findByCourseIgnoreCase(String course, Pageable pagination);

    @Query(nativeQuery = true, value="select * from recipe where name ILKE '%:search%' LIMIT 10")
    List<Users> filterRecipe(@Param("search") String search);
}

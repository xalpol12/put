package com.xalpol12.recipes.repository;

import com.xalpol12.recipes.model.RecipeCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeCollectionRepository extends JpaRepository<RecipeCollection, Long> {
}
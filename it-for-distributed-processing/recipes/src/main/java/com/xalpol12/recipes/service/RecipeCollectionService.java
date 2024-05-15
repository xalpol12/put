package com.xalpol12.recipes.service;

import com.xalpol12.recipes.exception.custom.IncompleteUpdateFormException;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionMergeRequest;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.model.mapper.RecipeCollectionMapper;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeCollectionService {
    private final RecipeRepository recipeRepository;
    private final RecipeCollectionRepository repository;
    private final RecipeCollectionMapper mapper;

    public RecipeCollectionOutput getRecipeCollection(Long id) {
        RecipeCollection recipeCollection = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe collection with id: " + id + " could not be found."));
        return mapper.collectionToOutput(recipeCollection);
    }

    public Page<RecipeCollectionOutput> getAllRecipeCollections(Pageable pageable) {
       return repository.findAll(pageable).map(mapper::collectionToOutput);
    }

    public RecipeCollectionOutput addRecipeCollection(RecipeCollectionInput input) {
        RecipeCollection collection = mapper.inputToCollection(input);
        collection.setVersion(0);
        return mapper.collectionToOutput(repository.save(collection));
    }

    @Transactional
    public void deleteRecipeCollection(Long id) {
            RecipeCollection collection = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Recipe collection with id: " + id + "does not exist"));
            collection.getRecipes().clear();
            repository.delete(collection);
    }

    @Transactional
    public RecipeCollectionOutput updateRecipeCollection(Long id, RecipeCollectionInput input, Integer version) {
        RecipeCollection collection = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe collection with id: " + id + "could not be found."));

        if (!Objects.equals(version, collection.getVersion())) {
            throw new OptimisticLockException();
        }

        try {
            List<Recipe> recipes = getAllRecipes(input.getRecipeIds());

            collection.setCollectionName(input.getCollectionName());
            collection.setRecipes(recipes);

            collection.setVersion(collection.getVersion() + 1);

            repository.save(collection);
            return mapper.collectionToOutput(collection);

        } catch (NullPointerException e) {
            throw new IncompleteUpdateFormException("Update form does not include all the entity's fields");
        }
    }

    private List<Recipe> getAllRecipes(List<Long> ids) {
        List<Recipe> recipes = new ArrayList<>();
        for (Long recipeId : ids) {
            Recipe recipe = recipeRepository
                    .findById(recipeId)
                    .orElseThrow(() -> new EntityNotFoundException("Recipe with id: " + recipeId + " does not exist"));
            recipes.add(recipe);
        }
        return recipes;
    }

    @Transactional
    public RecipeCollectionOutput patchRecipeCollection(Long id, RecipeCollectionInput input) {
        RecipeCollection original = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe collection with id: " + id + "could not be found."));

        if (input.getCollectionName() != null) {
            original.setCollectionName(input.getCollectionName());
        }
        if (input.getRecipeIds() != null) {
            List<Recipe> recipes = getAllRecipes(input.getRecipeIds());
            original.setRecipes(recipes);
        }

        return mapper.collectionToOutput(repository.save(original));
    }

    @Transactional
    public RecipeCollectionOutput mergeRecipeCollections(RecipeCollectionMergeRequest mergeRequest) {
        RecipeCollection to = repository.findById(mergeRequest.getMergeTo())
                .orElseThrow(() -> new EntityNotFoundException("Collection with id: " + mergeRequest.getMergeTo() + " not found"));
        RecipeCollection from = repository.findById(mergeRequest.getMergeFrom())
                .orElseThrow(() -> new EntityNotFoundException("Collection with id: " + mergeRequest.getMergeFrom() + " not found"));

        Set<Recipe> set = new HashSet<>();
        set.addAll(to.getRecipes());
        set.addAll(from.getRecipes());

        List<Recipe> mergedRecipes = new ArrayList<>(set);
        to.setRecipes(mergedRecipes);

        repository.delete(from);

        return mapper.collectionToOutput(repository.save(to));
    }
}

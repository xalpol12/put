package com.xalpol12.recipes.service;

import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionMergeRequest;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.model.mapper.RecipeCollectionMapper;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return mapper.collectionToOutput(repository.save(collection));
    }

    public void deleteRecipeCollection(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public RecipeCollectionOutput updateRecipeCollection(Long id, RecipeCollectionInput input) {
        RecipeCollection original = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe collection with id: " + id + "could not be found."));

        List<Recipe> recipes = new ArrayList<>();
        for (Long recipeId : input.getRecipeIds()) {
            if (repository.existsById(recipeId)) {
                Recipe recipe = recipeRepository.getReferenceById(recipeId);
                recipes.add(recipe);
            } else {
                throw new EntityNotFoundException("Recipe with id: " + id + " does not exist");
            }
        }

        RecipeCollection updated = RecipeCollection.builder()
                .id(original.getId())
                .collectionName(input.getCollectionName())
                .recipes(recipes)
                .build();

        return mapper.collectionToOutput(repository.save(updated));
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

package com.xalpol12.recipes.service;

import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionMergeRequest;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.model.mapper.RecipeCollectionMapper;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeCollectionService {
    private final RecipeCollectionRepository repository;
    private final RecipeCollectionMapper mapper;

    public RecipeCollectionOutput getRecipeCollection(Long id) {
        RecipeCollection recipeCollection = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe collection with id: " + id + "could not be found."));
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
        //TODO: something something update
        RecipeCollection recipeCollection = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe collection with id: " + id + "could not be found."));
        return null;
    }

    public RecipeCollectionOutput mergeRecipeCollections(RecipeCollectionMergeRequest mergeRequest) {
        //TODO: implement
        return null;
    }
}

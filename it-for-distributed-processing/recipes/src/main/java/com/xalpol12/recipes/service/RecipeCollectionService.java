package com.xalpol12.recipes.service;

import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.model.mapper.RecipeCollectionMapper;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeCollectionService {
    private final RecipeCollectionRepository repository;
    private final RecipeCollectionMapper mapper;

    public RecipeCollectionOutput getRecipeCollection(Long id) {
        RecipeCollection recipeCollection = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe collection with id: " + id + "could not be found."));
        return mapper.mapToRecipeCollectionOutput(recipeCollection);
    }

    public List<RecipeCollectionOutput> getAllRecipeCollections() {
       return repository
               .findAll()
               .stream()
               .map(mapper::mapToRecipeCollectionOutput)
               .toList();
    }

    public URI addRecipeCollection(RecipeCollectionInput input) {
        RecipeCollection collection = mapper.mapToRecipeCollection(input);
        repository.save(collection);
        //TODO: Return URI
        return URI.create("not-implemented-yet");
    }

    public void deleteRecipeCollection(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllRecipeCollections() {
        repository.deleteAll();
    }

    @Transactional
    public void updateRecipeCollection(Long id, RecipeCollectionInput input) {
        //TODO: something something update
        RecipeCollection recipeCollection = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe collection with id: " + id + "could not be found."));
    }
}

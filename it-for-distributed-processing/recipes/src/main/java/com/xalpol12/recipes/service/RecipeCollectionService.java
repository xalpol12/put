package com.xalpol12.recipes.service;

import com.xalpol12.recipes.model.mapper.RecipeCollectionMapper;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeCollectionService {
    private final RecipeCollectionRepository repository;
    private final RecipeCollectionMapper mapper;
}

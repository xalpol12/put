package com.xalpol12.recipes.service;

import com.xalpol12.recipes.model.mapper.RecipeMapper;
import com.xalpol12.recipes.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository repository;
    private final RecipeMapper mapper;
}

package com.xalpol12.recipes.controller;

import com.xalpol12.recipes.controller.iface.IRecipeCollectionController;
import com.xalpol12.recipes.service.RecipeCollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeCollectionController implements IRecipeCollectionController {
    private final RecipeCollectionService service;
}

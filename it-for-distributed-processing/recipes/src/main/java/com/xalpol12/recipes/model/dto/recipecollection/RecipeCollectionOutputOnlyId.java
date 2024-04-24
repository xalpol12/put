package com.xalpol12.recipes.model.dto.recipecollection;

import lombok.Data;

@Data
public class RecipeCollectionOutputOnlyId {
    private Long collectionId;

    public RecipeCollectionOutputOnlyId(Long collectionId) {
        this.collectionId = collectionId;
    }
}

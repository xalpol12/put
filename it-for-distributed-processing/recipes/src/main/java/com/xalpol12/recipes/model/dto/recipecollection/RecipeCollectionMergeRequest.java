package com.xalpol12.recipes.model.dto.recipecollection;

import lombok.Data;

@Data
public class RecipeCollectionMergeRequest {
    private Long mergeTo;
    private Long mergeFrom;
}

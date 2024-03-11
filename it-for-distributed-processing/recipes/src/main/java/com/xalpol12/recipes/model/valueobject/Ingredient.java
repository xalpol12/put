package com.xalpol12.recipes.model.valueobject;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Ingredient {
    private String name;
}
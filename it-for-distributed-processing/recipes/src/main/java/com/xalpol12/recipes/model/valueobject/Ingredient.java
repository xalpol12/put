package com.xalpol12.recipes.model.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Ingredient {
    private String name;
}
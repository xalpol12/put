package com.xalpol12.recipes.model.valueobject;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class TextParagraph {
    private String content;
}

package com.xalpol12.recipes.model;

import com.xalpol12.recipes.model.valueobject.Ingredient;
import com.xalpol12.recipes.model.valueobject.TextParagraph;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity(name = "recipes")
@AllArgsConstructor
public class Recipe {
    @Id
    @Column(name = "recipe_id")
    private Long id;

    private Integer estimatedTime; // in minutes

    @ElementCollection
    private List<Ingredient> ingredients;

    @ElementCollection
    private List<TextParagraph> descriptions;

    @OneToMany(mappedBy = "recipe")
    @Cascade({CascadeType.REMOVE})
    @ToString.Exclude
    private List<Image> images;

    @ManyToMany(mappedBy = "recipes")
    @ToString.Exclude
    List<RecipeCollection> collections;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Recipe recipe = (Recipe) o;
        return getId() != null && Objects.equals(getId(), recipe.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

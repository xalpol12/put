package com.xalpol12.recipes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity(name = "recipe_collections")
@AllArgsConstructor
public class RecipeCollection {
    @Id
    @Column(name = "recipe_collection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Length(max = 80)
    private String collectionName;

    @ManyToMany
    @Cascade(CascadeType.REMOVE)
    @JoinTable(
            name = "recipe_collection_recipe",
            joinColumns = @JoinColumn(name = "recipe_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    @ToString.Exclude
    private List<Recipe> recipes;

    @Version
    private Integer version;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RecipeCollection that = (RecipeCollection) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

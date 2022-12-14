package com.abnamro.recipe.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Class for Recipe entity
 *
 * @author Suresh Chenga
 */
@Entity
@Table(name = "recipe")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GenericGenerator(
            name = "sequenceGenerator",
            strategy = "enhanced-sequence",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "optimizer",
                            value = "pooled-lo"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = "initial_value",
                            value = "1"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = "increment_size",
                            value = "1"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = "sequence_name",
                            value = "hibernate_sequence"
                    )
            }
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequenceGenerator"
    )
    private Long id;
    @Column(name = "RECIPE_NAME", nullable = false)
    private String recipeName;
    @Column(name = "IS_VEGETARIAN", nullable = false)
    private Boolean isVegetarian;
    @Column(name = "NO_OF_SERVINGS", nullable = false)
    private Integer noOfServings;
    @ElementCollection
    @CollectionTable(name = "RECIPE_INGREDIENTS", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "INGREDIENTS", nullable = false)
    private Set<String> ingredients = new HashSet<>();
    @Column(name = "INSTRUCTION", columnDefinition = "TEXT")
    private String instruction;
}

package com.side.tiggle.domain.category.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CategoryType type;

    @Column(name = "defaults", nullable = false)
    private boolean defaults;

    @Builder
    public Category(String name, CategoryType type, boolean defaults) {
        this.name = name;
        this.type = type;
        this.defaults = defaults;
    }
}

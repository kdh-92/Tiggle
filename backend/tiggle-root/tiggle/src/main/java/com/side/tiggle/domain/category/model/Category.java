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
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "default", nullable = false)
    private boolean defaults;

    @Builder
    public Category(String name, boolean defaults) {
        this.name = name;
        this.defaults = defaults;
    }
}

package com.side.tiggle.domain.asset.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "defaults", nullable = false)
    private boolean defaults;

    @Builder
    public Asset(String name, boolean defaults) {
        this.name = name;
        this.defaults = defaults;
    }
}
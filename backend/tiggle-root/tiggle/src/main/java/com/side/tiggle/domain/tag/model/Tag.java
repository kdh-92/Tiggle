package com.side.tiggle.domain.tag.model;

import com.side.tiggle.global.common.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "defaults", nullable = false)
    private boolean defaults;

    @Builder
    public Tag(String name, boolean defaults) {
        this.name = name;
        this.defaults = defaults;

    }
}

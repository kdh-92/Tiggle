package com.side.tiggle.domain.reaction;

import com.side.tiggle.global.common.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

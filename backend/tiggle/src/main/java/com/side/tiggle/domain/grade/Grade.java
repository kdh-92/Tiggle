package com.side.tiggle.domain.grade;

import com.side.tiggle.global.common.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public class Grade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

package com.side.tiggle.domain.member;

import com.side.tiggle.global.common.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

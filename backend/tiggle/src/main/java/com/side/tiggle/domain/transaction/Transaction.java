package com.side.tiggle.domain.transaction;

import com.side.tiggle.global.common.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

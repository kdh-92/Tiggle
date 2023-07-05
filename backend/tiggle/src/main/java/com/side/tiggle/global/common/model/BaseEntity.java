package com.side.tiggle.global.common.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * '@MappedSuperclass' : JPA Entity 클래스들이 BaseEntity를 상속할 경우 필드들(created_at, updated_at)도 칼럼으로 인식하도록 합니다.
 * '@EntityListeners(AuditingEntityListener.class)': BaseTimeEntiy 클래스에 Auditing 기능을 포함시킵니다.
 * '@CreatedDate': Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.
 * '@LastModifiedDate': 조회한 Entity의 값을 변경할 때 시간이 자동 저장됩니다.
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updated_at;

    @Column(name = "deleted_at")
    private LocalDateTime deleted_at;

}

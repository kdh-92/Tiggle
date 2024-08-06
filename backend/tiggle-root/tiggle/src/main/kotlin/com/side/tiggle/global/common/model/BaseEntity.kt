package com.side.tiggle.global.common.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

/**
 * '@MappedSuperclass' : JPA Entity 클래스들이 BaseEntity를 상속할 경우 필드들(created_at, updated_at)도 칼럼으로 인식하도록 합니다.
 * '@EntityListeners(AuditingEntityListener.class)': BaseTimeEntiy 클래스에 Auditing 기능을 포함시킵니다.
 * '@CreatedDate': Entity가 생성되어 저장될 때 시간이 자동 저장됩니다.
 * '@LastModifiedDate': 조회한 Entity의 값을 변경할 때 시간이 자동 저장됩니다.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity() {
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    open var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    @LastModifiedDate
    open var updatedAt: LocalDateTime? = null

    @Column(name = "deleted_at")
    open var deletedAt: LocalDateTime? = null

    @Column(name = "deleted", nullable = false)
    open var deleted: Boolean = false

    fun delete() {
        this.deletedAt = LocalDateTime.now()
        this.deleted = true
    }
}
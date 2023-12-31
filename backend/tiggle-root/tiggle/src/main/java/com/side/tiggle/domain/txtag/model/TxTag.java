package com.side.tiggle.domain.txtag.model;

import com.side.tiggle.global.common.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tx_tags")
public class TxTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tx_id", nullable = false)
    private Long txId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "tag_names", nullable = false)
    private String tagNames;

    @Builder
    public TxTag(Long txId, Long memberId, String tagNames) {
        this.txId = txId;
        this.memberId = memberId;
        this.tagNames = tagNames;
    }
}

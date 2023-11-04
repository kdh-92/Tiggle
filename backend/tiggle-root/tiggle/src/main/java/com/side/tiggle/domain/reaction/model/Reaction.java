package com.side.tiggle.domain.reaction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.transaction.model.Transaction;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "reactions")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JoinColumn(name = "tx_id", nullable = false)
    @ManyToOne
    private Transaction tx;

    @JsonIgnore
    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;

    @JsonIgnore
    @JoinColumn(name = "receiver_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ReactionType type;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;


    @Builder
    public Reaction(Transaction tx, Member sender, Member receiver, ReactionType type) {
        this.tx = tx;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
    }
}

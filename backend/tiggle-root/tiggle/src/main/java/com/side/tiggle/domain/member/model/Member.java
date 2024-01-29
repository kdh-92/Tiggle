package com.side.tiggle.domain.member.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.global.common.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "members")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    // tx
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> txList;

    // comment
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> sentCommentList;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> receivedCommentList;

    // reaction
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reaction> sentReactionList;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reaction> receivedReactionList;

//    // notification
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Comment> commentList;



    @Builder
    public Member(String email, String profileUrl, String nickname, LocalDate birth) {
        this.email = email;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.birth = birth;
    }

    @Builder
    public Member(String email, String profileUrl, String nickname, String provider, String providerId) {
        this.email = email;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
    }
}



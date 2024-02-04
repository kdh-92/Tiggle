package com.side.tiggle.domain.member.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.global.common.model.BaseEntity
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "members")
//@JsonIgnoreProperties(["hibernateLazyInitializer", "handler"])
class Member (
    var email: String,
    var profileUrl: String?,
    var nickname: String,
    var birth: LocalDate?,
    var provider: String? = null,
    var providerId: String? = null
): BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private var _txList: MutableList<Transaction> = mutableListOf()

    val tx: List<Transaction>
        get() = _txList.toList()

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private var _sentCommentList: MutableList<Comment> = mutableListOf()

    val sentComment: List<Comment>
        get() = _sentCommentList.toList()

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private var _receivedCommentList: MutableList<Comment> = mutableListOf()

    val receivedComment: List<Comment>
        get() = _receivedCommentList.toList()

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private var _sentReactionList: MutableList<Reaction> = mutableListOf()

    val sentReaction: List<Reaction>
        get() = _sentReactionList.toList()

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private var _receivedReactionList: MutableList<Reaction> = mutableListOf()

    val receivedReaction: List<Reaction>
        get() = _receivedReactionList.toList()


    fun joinTx(tx: Transaction) {
        _txList += tx
    }

    fun joinSentComment(comment: Comment) {
        _sentCommentList += comment
    }

    fun joinReceivedComment(comment: Comment) {
        _receivedCommentList += comment
    }

    fun joinSentReaction(reaction: Reaction) {
        _sentReactionList += reaction
    }

    fun joinReceivedComment(reaction: Reaction) {
        _receivedReactionList += reaction
    }
}
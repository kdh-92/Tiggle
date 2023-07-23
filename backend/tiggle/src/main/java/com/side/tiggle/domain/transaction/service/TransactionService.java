package com.side.tiggle.domain.transaction.service;

import com.side.tiggle.domain.transaction.dto.TransactionDto;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionDto.Request.ReqDto dto) {
        return transactionRepository.save(TransactionDto.Request.ReqDto.toEntity(dto));
    }

    public Transaction getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException(""));
    }

    public Page<Transaction> getCountOffsetTransaction(int pageSize, int index) {
        Page<Transaction> txPage = transactionRepository.findAll(
                PageRequest.of(index, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        if (txPage.isEmpty()) throw new IllegalArgumentException("거래가 존재하지 않습니다.");

        return txPage;
    }

    public Page<Transaction> getMemberCountOffsetTransaction(Long memberId, int count, int offset) {
        Page<Transaction> memberTxPage = transactionRepository.findByMemberId(
                memberId, PageRequest.of(offset, count, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        if (memberTxPage.isEmpty()) throw new IllegalArgumentException("거래가 존재하지 않습니다.");

        return memberTxPage;
    }

    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public Transaction updateTransaction(Long memberId, Long transactionId, TransactionDto.Request.ReqDto dto) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .stream().filter(item -> item.getMemberId().equals(memberId)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 거래가 존재하지 않습니다."));

        transaction.setParentId(dto.getParentId());
        transaction.setType(dto.getType());
        transaction.setImageUrl(dto.getImageUrl());
        transaction.setAmount(dto.getAmount());
        transaction.setDate(dto.getDate());;
        transaction.setContent(dto.getContent());
        transaction.setReason(dto.getReason());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long memberId, Long transactionId) {
        transactionRepository.delete(transactionRepository.findById(transactionId).stream().filter(item -> item.getMemberId().equals(memberId)).findAny().orElseThrow(() -> new IllegalArgumentException("해당 거래가 존재하지 않습니다.")));
    }
}


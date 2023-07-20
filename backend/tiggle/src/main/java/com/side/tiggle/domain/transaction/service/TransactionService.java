package com.side.tiggle.domain.transaction.service;

import com.side.tiggle.domain.transaction.dto.TransactionDto;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction createTransaction(TransactionDto.TransactionRequestDto dto) {
        return transactionRepository.save(TransactionDto.toEntity(dto));
    }

    public Transaction getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException(""));
    }

    public List<Transaction> getMemberCountOffsetTransaction(Long memberId, int count, int offset) {
        return transactionRepository.findByMemberId(
                memberId, PageRequest.of(offset, count, Sort.by(Sort.Direction.DESC, "createdAt"))
        ).stream().collect(Collectors.toList());
    }

    public List<Transaction> getCountOffsetTransaction(int count, int offset) {
        return transactionRepository.findAll(
                PageRequest.of(offset, count, Sort.by(Sort.Direction.DESC, "createdAt"))
        ).getContent().stream().collect(Collectors.toList());
    }

    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public Transaction updateTransaction(Long transactionId, TransactionDto.TransactionUpdateRequestDto dto) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        transaction.setParentId(dto.getParentId());
        transaction.setType(dto.getType());
        transaction.setImageUrl(dto.getImageUrl());
        transaction.setAmount(dto.getAmount());
        transaction.setDate(dto.getDate());;
        transaction.setContent(dto.getContent());
        transaction.setReason(dto.getReason());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(long memberId, Long transactionId) {
        transactionRepository.delete(transactionRepository.findById(transactionId).stream().filter(item -> item.getMemberId().equals(memberId)).findAny().orElseThrow(() -> new IllegalArgumentException("해당 거래가 존재하지 않습니다.")));
    }
}


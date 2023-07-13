package com.side.tiggle.domain.transaction.service;

import com.side.tiggle.domain.transaction.TransactionDto;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 임시 CRUD (추가 작업 필요)
 */

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .memberId(transactionDto.getMemberId())
                .parentId(transactionDto.getParentId())
                .type(transactionDto.getType())
                .imageUrl(transactionDto.getImageUrl())
                .amount(transactionDto.getAmount())
                .date(transactionDto.getDate())
                .content(transactionDto.getContent())
                .reason(transactionDto.getReason())
                .build();

        return transactionDto.fromEntity(transactionRepository.save(transaction));
    }

    public TransactionDto getTransaction(Long transactionId) {
        return TransactionDto.fromEntity(transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("")));
    }

    public List<TransactionDto> getAllTransaction() {
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (Transaction transaction : transactionRepository.findAll()) {
            transactionDtoList.add(TransactionDto.fromEntity(transaction));
        }

        return transactionDtoList;
    }

    public TransactionDto updateTransaction(Long transactionId, TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        transaction.setParentId(transactionDto.getParentId());
        transaction.setImageUrl(transactionDto.getImageUrl());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());;
        transaction.setContent(transactionDto.getContent());
        transaction.setReason(transactionDto.getReason());

        return transactionDto.fromEntity(transactionRepository.save(transaction));
    }

    // delete
}


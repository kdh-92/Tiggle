package com.side.tiggle.domain.transaction.service;

import com.side.tiggle.domain.transaction.dto.TransactionDto;
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final String FOLDER_PATH = System.getProperty("user.dir") + "/upload/image";

    public String uploadFileToFolder(MultipartFile uploadFile) throws IOException {

        String originalName = uploadFile.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("//")+1);
        String folderPath = makeFolder();
        String uuid = UUID.randomUUID().toString();
        String saveName = FOLDER_PATH + File.separator + folderPath + File.separator + uuid + "_" + fileName;

        Path savePath = Paths.get(saveName);

        uploadFile.transferTo(savePath);
        return saveName;
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(FOLDER_PATH, folderPath);

        if (uploadPathFolder.exists() == false) uploadPathFolder.mkdirs();
        return folderPath;
    }

    public Transaction createTransaction(TransactionDto dto) {
        return transactionRepository.save(dto.toEntity(dto));
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

    public Transaction updateTransaction(Long memberId, Long transactionId, TransactionUpdateReqDto dto) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .stream().filter(item -> item.getMemberId().equals(memberId)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 거래가 존재하지 않습니다."));

        transaction.setType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setDate(dto.getDate());
        transaction.setContent(dto.getContent());
        transaction.setReason(dto.getReason());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long memberId, Long transactionId) {
        transactionRepository.delete(transactionRepository.findById(transactionId).stream().filter(item -> item.getMemberId().equals(memberId)).findAny().orElseThrow(() -> new IllegalArgumentException("해당 거래가 존재하지 않습니다.")));
    }
}


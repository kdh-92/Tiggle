//package com.side.tiggle.domain.transaction.service;
//
//import com.side.tiggle.domain.asset.model.Asset;
//import com.side.tiggle.domain.asset.service.AssetService;
//import com.side.tiggle.domain.category.model.Category;
//import com.side.tiggle.domain.category.service.CategoryService;
//import com.side.tiggle.domain.member.service.MemberService;
//import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto;
//import com.side.tiggle.domain.transaction.repository.TransactionRepository;
//import com.side.tiggle.domain.txtag.model.TxTag;
//import com.side.tiggle.domain.txtag.service.TxTagService;
//import com.side.tiggle.domain.member.model.Member;
//import com.side.tiggle.domain.transaction.dto.TransactionDto;
//import com.side.tiggle.domain.transaction.model.Transaction;
//import com.side.tiggle.global.exception.NotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class TransactionService {
//
//    private final TxTagService txTagService;
//    private final MemberService memberService;
//    private final AssetService assetService;
//    private final CategoryService categoryService;
//    private final TransactionRepository transactionRepository;
//    private final String FOLDER_PATH = System.getProperty("user.dir") + "/upload/image";
//
//    public String uploadFileToFolder(MultipartFile uploadFile) throws IOException {
//
//        String originalName = uploadFile.getOriginalFilename();
//        String fileName = originalName.substring(originalName.lastIndexOf("//")+1);
//        String folderPath = makeFolder();
//        String uuid = UUID.randomUUID().toString();
//        String saveName = FOLDER_PATH + File.separator + folderPath + File.separator + uuid + "_" + fileName;
//
//        Path savePath = Paths.get(saveName);
//
//        uploadFile.transferTo(savePath);
//        return saveName;
//    }
//
//    private String makeFolder() {
//        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//        String folderPath = str.replace("/", File.separator);
//
//        File uploadPathFolder = new File(FOLDER_PATH, folderPath);
//
//        if (uploadPathFolder.exists() == false) uploadPathFolder.mkdirs();
//        return folderPath;
//    }
//
//    private void deleteFolder() {
//        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//        String folderPath = str.replace("/", File.separator);
//        File uploadPathFolder = new File(FOLDER_PATH, folderPath);
//
//        if (uploadPathFolder.list().length == 0) uploadPathFolder.delete();
//    }
//
//    @Transactional
//    public Transaction createTransaction(TransactionDto dto, MultipartFile file) throws IOException {
//        Path savePath = null;
//        try {
//            String uploadedFilePath = uploadFileToFolder(file);
//            savePath = Paths.get(uploadedFilePath);
//            dto.setImageUrl(uploadedFilePath);
//
//            Member member = memberService.getMember(dto.getMemberId());
//            Asset asset = assetService.getAsset(dto.getAssetId());
//            Category category = categoryService.getCategory(dto.getCategoryId());
//            Transaction tx = transactionRepository.save(dto.toEntity(member, asset, category));
//            TxTag txTag = new TxTag(tx.getId(), dto.getMemberId(), dto.getTagNames());
//
//            txTagService.createTxTag(txTag);
//            return tx;
//        } catch (Exception e) {
//            if (savePath != null) {
//                Files.deleteIfExists(savePath);
//            }
//            deleteFolder();
//            throw e;
//        }
//    }
//
//    public Transaction getTransaction(Long transactionId) {
//        return transactionRepository.findById(transactionId)
//                .orElseThrow(NotFoundException::new);
//    }
//
//    public Page<Transaction> getCountOffsetTransaction(int pageSize, int index) {
//        Page<Transaction> txPage = transactionRepository.findAll(
//                PageRequest.of(index, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"))
//        );
//
//        if (txPage.isEmpty()) throw new NotFoundException();
//
//        return txPage;
//    }
//
//    public Page<Transaction> getMemberCountOffsetTransaction(Long memberId, int count, int offset) {
//        Page<Transaction> memberTxPage = transactionRepository.findByMemberId(
//                memberId, PageRequest.of(offset, count, Sort.by(Sort.Direction.DESC, "createdAt"))
//        );
//
//        if (memberTxPage.isEmpty()) throw new NotFoundException();
//
//        return memberTxPage;
//    }
//
//    public List<Transaction> getAllUndeletedTransaction() {
//        return transactionRepository.findAll();
//    }
//
//    @Transactional
//    public Transaction updateTransaction(Long memberId, Long transactionId, TransactionUpdateReqDto dto) {
//        Transaction transaction = transactionRepository.findById(transactionId)
//                .stream().filter(item -> item.getMember().getId().equals(memberId)).findAny()
//                .orElseThrow(NotFoundException::new);
//
//        transaction.setType(dto.getType());
//        transaction.setAmount(dto.getAmount());
//        transaction.setDate(dto.getDate());
//        transaction.setContent(dto.getContent());
//        transaction.setReason(dto.getReason());
//        transaction.setTagNames(dto.getTagNames());
//
//        txTagService.updateTxTag(transactionId, transaction.getMember().getId(), dto.getTagNames());
//
//        return transactionRepository.save(transaction);
//    }
//
//    public void deleteTransaction(Long memberId, Long transactionId) {
//        transactionRepository.delete(transactionRepository.findById(transactionId)
//                .stream()
//                .filter(item -> item.getMember().getId().equals(memberId))
//                .findAny()
//                .orElseThrow(NotFoundException::new)
//        );
//    }
//}
//

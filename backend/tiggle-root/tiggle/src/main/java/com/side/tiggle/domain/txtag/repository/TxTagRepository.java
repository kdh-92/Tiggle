package com.side.tiggle.domain.txtag.repository;

import com.side.tiggle.domain.txtag.model.TxTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TxTagRepository extends JpaRepository<TxTag, Long> {

    Optional<TxTag> findByTxId(Long txId);
}

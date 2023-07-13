package com.side.tiggle.domain.txtag.api;

import com.side.tiggle.domain.txtag.TxTagDto;
import com.side.tiggle.domain.txtag.service.TxTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/txtag")
public class TxTagApiController {

    private final TxTagService txTagService;

    @PostMapping
    public ResponseEntity<TxTagDto> createTxTag(@RequestBody TxTagDto txTagDto) {
        return new ResponseEntity<>(txTagService.createTxTag(txTagDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TxTagDto> getTxTag(@PathVariable("id") Long commentId) {
        return new ResponseEntity<>(txTagService.getTxTag(commentId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TxTagDto>> getAllTxTag() {
        return new ResponseEntity<>(txTagService.getAllTxTag(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TxTagDto> updateComment(@PathVariable("id") Long txTagId,
                                                    @RequestBody TxTagDto txTagDto) {
        return new ResponseEntity<>(txTagService.updateTxTag(txTagId, txTagDto), HttpStatus.OK);
    }

    // delete

}

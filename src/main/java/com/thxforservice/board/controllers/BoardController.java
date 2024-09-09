package com.thxforservice.board.controllers;

import com.thxforservice.board.entities.Board;
import com.thxforservice.board.entities.BoardData;
import com.thxforservice.board.services.BoardDeleteService;
import com.thxforservice.board.services.BoardInfoService;
import com.thxforservice.board.services.BoardSaveService;
import com.thxforservice.board.services.BoardViewCountService;
import com.thxforservice.board.services.config.BoardConfigInfoService;
import com.thxforservice.board.validators.BoardValidator;
import com.thxforservice.global.CommonSearch;
import com.thxforservice.global.ListData;
import com.thxforservice.global.Utils;
import com.thxforservice.global.exceptions.BadRequestException;
import com.thxforservice.global.rests.JSONData;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Board", description = "게시글 API")
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardConfigInfoService configInfoService;
    private final BoardInfoService infoService;
    private final BoardSaveService saveService;
    private final BoardDeleteService deleteService;
    private final BoardViewCountService viewCountService;
    private final BoardValidator validator;
    private final Utils utils;

    // 게시판 설정
    @GetMapping("/config/{bid}")
    public JSONData getConfig(@PathVariable("bid") String bid) {

        Board board = configInfoService.get(bid);

        return new JSONData(board);
    }

    // 글쓰기
    @PostMapping("/write/{bid}")
    public ResponseEntity<JSONData> write(@PathVariable("bid") String bid, @RequestBody @Valid RequestBoard form, Errors errors) {
        form.setBid(bid);
        form.setMode("write");

        return save(form, errors);
    }

    // 글 수정
    @PatchMapping("/update/{seq}")
    public ResponseEntity<JSONData> update(@PathVariable("seq") Long seq, @RequestBody @Valid RequestBoard form, Errors errors) {
        form.setSeq(seq);
        form.setMode("update");

        return save(form, errors);
    }

    // 글 작성, 수정 처리
    private ResponseEntity<JSONData> save(RequestBoard form, Errors errors) {

        validator.validate(form, errors);

        if (errors.hasErrors()) { // 검증 실패
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        BoardData data = saveService.save(form);
        data.setBoard(null);
        data.setComments(null);

        JSONData jsonData = new JSONData(data);
        HttpStatus status = HttpStatus.CREATED;
        jsonData.setStatus(status);

        return ResponseEntity.status(status).body(jsonData);
    }

    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable("seq") Long seq) {
        BoardData item = infoService.get(seq);

        viewCountService.update(seq); // 조회수 카운트

        return new JSONData(item);
    }

    @GetMapping("/list/{bid}")
    public JSONData list(@PathVariable("bid") String bid, @ModelAttribute BoardDataSearch search) {
        ListData<BoardData> data = infoService.getList(bid, search);

        return new JSONData(data);
    }

    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {
        BoardData item = deleteService.delete(seq);

        return new JSONData(item);
    }

    @GetMapping("/wish")
    @PreAuthorize("isAuthenticated()")
    public JSONData wishList(CommonSearch search) {
        ListData<BoardData> data = infoService.getWishList(search);

        return new JSONData(data);
    }
}

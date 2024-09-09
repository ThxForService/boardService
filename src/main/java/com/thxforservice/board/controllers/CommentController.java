package com.thxforservice.board.controllers;

import com.thxforservice.board.entities.CommentData;
import com.thxforservice.board.services.comment.CommentDeleteService;
import com.thxforservice.board.services.comment.CommentInfoService;
import com.thxforservice.board.services.comment.CommentSaveService;
import com.thxforservice.board.validators.CommentValidator;
import com.thxforservice.global.Utils;
import com.thxforservice.global.exceptions.BadRequestException;
import com.thxforservice.global.rests.JSONData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentInfoService infoService;
    private final CommentSaveService saveService;
    private final CommentDeleteService deleteService;
    private final CommentValidator validator;
    private final Utils utils;

    @PostMapping
    public JSONData write(@RequestBody @Valid RequestComment form, Errors errors) {
        return save(form, errors);
    }

    @PatchMapping
    public JSONData update(@RequestBody @Valid RequestComment form, Errors errors) {
        return save(form, errors);
    }

    public JSONData save(RequestComment form, Errors errors) {
        validator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        saveService.save(form);

        List<CommentData> items = infoService.getList(form.getBoardDataSeq());

        return new JSONData(items);
    }

    @GetMapping("/info/{seq}")
    public JSONData getInfo(@PathVariable("seq") Long seq) {
        CommentData item = infoService.get(seq);

        return new JSONData(item);
    }

    @GetMapping("/list/{bSeq}")
    public JSONData getList(@PathVariable("bSeq") Long bSeq) {
        List<CommentData> items = infoService.getList(bSeq);

        return new JSONData(items);
    }

    @DeleteMapping("/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {
        Long bSeq = deleteService.delete(seq);

        List<CommentData> items = infoService.getList(bSeq);

        return new JSONData(items);
    }
}

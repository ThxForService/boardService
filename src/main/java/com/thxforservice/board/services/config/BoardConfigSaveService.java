package com.thxforservice.board.services.config;

import com.thxforservice.board.controllers.RequestBoardConfig;
import com.thxforservice.board.entities.Board;
import com.thxforservice.board.repositories.BoardRepository;
import com.thxforservice.global.Utils;
import com.thxforservice.member.constants.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BoardConfigSaveService {

    private final BoardRepository boardRepository;
    //private final FileUploadDoneService fileUploadDoneService;
    private final Utils utils;

    public void save(RequestBoardConfig form) {
        String bid = form.getBid();
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        Board board = boardRepository.findById(bid).orElseGet(Board::new);

        if (mode.equals("add")) { // 게시판 등록시 gid, bid 등록 -> 수정시에는 변경 X
            board.setBid(bid);
            board.setGid(form.getGid());
        }

        board.setBname(form.getBname());
        board.setActive(form.isActive());
        board.setRowsPerPage(form.getRowsPerPage());
        board.setPageCountPc(form.getPageCountPc());
        board.setPageCountMobile(form.getPageCountMobile());
        board.setUseReply(form.isUseReply());
        board.setUseComment(form.isUseComment());
        board.setUseEditor(form.isUseEditor());
        board.setUseUploadImage(form.isUseUploadImage());
        board.setUseUploadFile(form.isUseUploadFile());
        board.setLocationAfterWriting(form.getLocationAfterWriting());
        board.setShowListBelowView(form.isShowListBelowView());
        board.setSkin(form.getSkin());
        board.setCategory(form.getCategory());

        board.setListAccessType(Authority.valueOf(form.getListAccessType()));
        board.setViewAccessType(Authority.valueOf(form.getViewAccessType()));
        board.setWriteAccessType(Authority.valueOf(form.getWriteAccessType()));
        board.setReplyAccessType(Authority.valueOf(form.getReplyAccessType()));
        board.setCommentAccessType(Authority.valueOf(form.getCommentAccessType()));


        board.setListOrder(form.getListOrder());

        boardRepository.saveAndFlush(board);

        // 파일 업로드 완료 처리
        //fileUploadDoneService.process(board.getGid());
    }
}

package com.thxforservice.board.services.config;

import com.thxforservice.board.entities.Board;
import com.thxforservice.board.repositories.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardConfigDeleteService {

    private final BoardRepository boardRepository;
    private final BoardConfigInfoService configInfoService;
    //private final Utils utils;

    /**
     * 게시판 삭제
     * 
     * @param bid : 게시판 아이디
     */
    public void delete(String bid) {
        Board board = configInfoService.get(bid);


        boardRepository.delete(board);

        boardRepository.flush();

    }


}

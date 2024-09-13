package com.thxforservice.board.services;

import com.thxforservice.board.entities.BoardData;
import com.thxforservice.board.entities.BoardView;
import com.thxforservice.board.repositories.BoardDataRepository;
import com.thxforservice.board.repositories.BoardViewRepository;
import com.thxforservice.global.Utils;
import com.thxforservice.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import com.thxforservice.board.entities.QBoardView;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardViewCountService {
    private final BoardViewRepository viewRepository;
    private final BoardDataRepository dataRepository;
    private final MemberUtil memberUtil;
    private final Utils utils;

    public void update(Long seq) {
        BoardData data = dataRepository.findById(seq).orElse(null);
        if (data == null) {
            return;
        }

        int uid = memberUtil.isLogin() ? memberUtil.getMember().getMemberSeq().intValue() : utils.guestUid();

        BoardView boardView = new BoardView(seq, uid);
        viewRepository.saveAndFlush(boardView);

        // 전체 조회수
        QBoardView bv = QBoardView.boardView;
        long total = viewRepository.count(bv.seq.eq(seq));

        data.setViewCount((int)total);
        dataRepository.saveAndFlush(data);
    }
}

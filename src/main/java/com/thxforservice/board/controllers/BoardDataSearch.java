package com.thxforservice.board.controllers;

import com.thxforservice.global.CommonSearch;
import lombok.Data;

import java.util.List;

@Data
public class BoardDataSearch extends CommonSearch {

    private String bid; // 게시판 ID
    private List<String> bids; // 게시한 ID 여러개
    private Long num1;
    private String sort; // 정렬 조건
    private List<String> email;
}

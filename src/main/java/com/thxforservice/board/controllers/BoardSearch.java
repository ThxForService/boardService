package com.thxforservice.board.controllers;

import com.thxforservice.global.CommonSearch;
import lombok.Data;

import java.util.List;

@Data
public class BoardSearch extends CommonSearch {
    private String bid;
    private List<String> bids;

    private String bname;

    private boolean active;
}

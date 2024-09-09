package com.thxforservice.board.repositories;

import com.thxforservice.board.entities.BoardView;
import com.thxforservice.board.entities.BoardViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardViewRepository extends JpaRepository<BoardView, BoardViewId>, QuerydslPredicateExecutor<BoardView> {
}
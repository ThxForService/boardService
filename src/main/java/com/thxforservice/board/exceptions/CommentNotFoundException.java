package com.thxforservice.board.exceptions;

import com.thxforservice.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CommonException {
    public CommentNotFoundException() {
        super("NotFound.comment", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}

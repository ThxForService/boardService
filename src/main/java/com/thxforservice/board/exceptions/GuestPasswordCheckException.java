package com.thxforservice.board.exceptions;

import com.thxforservice.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class GuestPasswordCheckException extends CommonException {
    public GuestPasswordCheckException() {
        super("RequiredCheck.guestPw", HttpStatus.UNAUTHORIZED);
        setErrorCode(true);
    }
}

package com.thxforservice.board.validators;

import com.thxforservice.board.controllers.RequestBoardConfig;
import com.thxforservice.board.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class BoardConfigValidator implements Validator {

    private final BoardRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestBoardConfig.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        RequestBoardConfig form = (RequestBoardConfig) target;

        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        // 게시판 추가인 경우 게시판 ID 중복 여부 체크
        if (mode.equals("add") && repository.existsById(form.getBid())) {
            errors.rejectValue("bid", "Duplicated");
        }
    }
}

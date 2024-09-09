package com.thxforservice.global.services;

import com.thxforservice.global.Utils;
import com.thxforservice.global.entities.SessionEntity;
import com.thxforservice.global.repositories.SessionRepository;
import com.thxforservice.member.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository repository;
    private final MemberUtil memberUtil;
    private final Utils utils;

    public void save(String key, String value) {
        String token = getToken();
        String _key = String.format("%s_%s", token, key);
        SessionEntity entity = SessionEntity.builder()
                .key(_key)
                .value(value)
                .build();
        repository.save(entity);
    }

    public String get(String key) {
        String _key = String.format("%s_%s", getToken(), key);
        SessionEntity entity = repository.findById(_key).orElse(null);

        return entity == null ? null : entity.getValue();
    }

    public String getToken() {
        return memberUtil.isLogin() ? utils.getToken() : String.valueOf(utils.guestUid());
    }
}

package com.thxforservice.file.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.thxforservice.file.entities.FileInfo;
import com.thxforservice.global.rests.ApiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDeleteService {
    private final ApiRequest apiRequest;

    public FileInfo delete(Long seq) {
        ApiRequest result = apiRequest.request("/delete/" + seq, "fileservice", HttpMethod.DELETE);

        if (result.getStatus().is2xxSuccessful() && result.getData().isSuccess()) {
            return result.toObj(FileInfo.class);
        }

        return null;
    }


    public List<FileInfo> delete(String gid, String location) {
        String url = "/deletes/" + gid;
        if (StringUtils.hasText(location)) url += "&location=" + location;

        ApiRequest result = apiRequest.request(url, "fileservice", HttpMethod.DELETE);

        if (result.getStatus().is2xxSuccessful() && result.getData().isSuccess()) {
            return result.toList(new TypeReference<>() {});
        }

        return null;
    }

    public List<FileInfo> delete(String gid) {
        return delete(gid, null);
    }
}

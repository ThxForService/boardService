package com.thxforservice.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thxforservice.member.constants.Authority;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {

    private Long memberSeq;

    private String gid;

    private String email;

    private String password;

    private String userName;

    private String mobile;

    private Authority authority;
}
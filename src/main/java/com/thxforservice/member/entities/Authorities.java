package com.thxforservice.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thxforservice.member.constants.Authority;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Authorities {

    private Authority authority;
}
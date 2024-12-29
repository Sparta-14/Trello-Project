package com.spata14.trelloproject.user.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spata14.trelloproject.common.deserializer.TokenDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequestDto {

    @JsonDeserialize(using = TokenDeserializer.class)
    private String token;
}

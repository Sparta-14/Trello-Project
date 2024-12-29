package com.spata14.trelloproject.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TokenDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String url = p.getText(); // 입력값 받기
        String baseUrl = "https://hooks.slack.com/services/";

        if (url.startsWith(baseUrl)) {
            return url.substring(baseUrl.length()); // 포맷 변경
        }
        throw new IllegalArgumentException("Invalid Slack webhook URL");
    }
}
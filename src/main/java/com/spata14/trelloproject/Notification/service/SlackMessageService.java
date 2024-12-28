package com.spata14.trelloproject.Notification.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class SlackMessageService {

    private final WebClient webClient = WebClient.builder().build();

    public void sendMessage(Long userId, String message) {

        //  토큰 조작
        String SlackbaseUrl = "https://hooks.slack.com/services/";
        String token = "토큰 정보";
        String webhookUrl = createWebhookUrl(SlackbaseUrl, token);

//        String webhookUrl = "https://hooks.slack.com/services/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";


        webClient.post()
                .uri(webhookUrl)
                .header("Content-Type", "application/json")
                .bodyValue("{\"text\":\"" + message + "\"}")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Slack Response: " + response))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()))
                .subscribe();
    }

    private Map<String, String> createPayload(String message) {
        Map<String, String> payload = new HashMap<>();
        payload.put("text", message);
        return payload;
    }

    private String createWebhookUrl(String SlackbaseUrl, String token) {
        return new StringBuilder()
                .append(SlackbaseUrl)
                .append(token)
                .toString();
    }
}
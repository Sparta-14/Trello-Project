package com.spata14.trelloproject.Notification.service;

import com.spata14.trelloproject.Notification.exception.NotificationErrorCode;
import com.spata14.trelloproject.Notification.exception.NotificationException;
import com.spata14.trelloproject.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class SlackMessageService {

    private final WebClient webClient = WebClient.builder().build();

    @Async
    public void sendMessage(String token, String message) {


        //  토큰 조작
        String SlackbaseUrl = "https://hooks.slack.com/services/";
        String webhookUrl = createWebhookUrl(SlackbaseUrl, token);

        webClient.post()
                .uri(webhookUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue("{\"text\":\"" + message + "\"}")
                .retrieve()
                // HTTP 상태 코드가 오류인 경우 처리
                .onStatus(HttpStatusCode::isError, clientResponse ->
                        Mono.error(new NotificationException(NotificationErrorCode.TOKEN_INVALID))
                )
                .bodyToMono(String.class) // 응답 바디를 문자열로 처리
                .doOnNext(response -> System.out.println("Slack Response: " + response)) // 성공 로그
                .subscribe();
    }

    private String createWebhookUrl(String SlackbaseUrl, String token) {
        return new StringBuilder()
                .append(SlackbaseUrl)
                .append(token)
                .toString();
    }
}
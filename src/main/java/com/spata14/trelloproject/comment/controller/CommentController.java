package com.spata14.trelloproject.comment.controller;

import com.spata14.trelloproject.Notification.facade.NotificationServiceFacade;
import com.spata14.trelloproject.comment.dto.CommentRequestDto;
import com.spata14.trelloproject.comment.dto.CommentResponseDto;
import com.spata14.trelloproject.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("workspaces/{workspaceId]/cards/{cardId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final NotificationServiceFacade notificationServiceFacade;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long workspaceId,
            @PathVariable Long cardId,
            @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto responseDto = commentService.createComment(cardId, commentRequestDto);

        // 메세지 생성
        notificationServiceFacade.sendSlackMessageForProcessingOutcome(workspaceId, cardId, responseDto.getId());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long cardId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto responseDto = commentService.updateComment(cardId, commentId, commentRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long cardId,
            @PathVariable Long commentId) {
        commentService.deleteComment(cardId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

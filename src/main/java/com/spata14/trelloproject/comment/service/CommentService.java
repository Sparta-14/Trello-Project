package com.spata14.trelloproject.comment.service;

import com.spata14.trelloproject.card.entity.Card;
import com.spata14.trelloproject.card.repository.CardRepository;
import com.spata14.trelloproject.comment.dto.CommentRequestDto;
import com.spata14.trelloproject.comment.dto.CommentResponseDto;
import com.spata14.trelloproject.comment.entity.Comment;
import com.spata14.trelloproject.comment.exception.CommentErrorCode;
import com.spata14.trelloproject.comment.exception.CommentException;
import com.spata14.trelloproject.comment.repository.CommentRepository;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto createComment(Long cardId, CommentRequestDto commentRequestDto) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CommentException(CommentErrorCode.CARD_NOT_FOUND));
        User user = getCurrentUser();
        validateUserPermission(user, card);

        Comment comment = new Comment(commentRequestDto.getText(), card, user);
        Comment savedComment = commentRepository.save(comment);

        return toResponseDto(savedComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long cardId, Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
        User user = getCurrentUser();
        validateCommentOwnership(user, comment);

        comment.updateText(commentRequestDto.getText());
        Comment updatedComment = commentRepository.save(comment);

        return toResponseDto(updatedComment);
    }

    @Transactional
    public void deleteComment(Long cardId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
        User user = getCurrentUser();
        validateCommentOwnership(user, comment);

        commentRepository.delete(comment);
    }

    private User getCurrentUser() {
        return userRepository.findById(1L).orElseThrow(() -> new CommentException(CommentErrorCode.USER_NOT_FOUND));
    }

    private void validateUserPermission(User user, Card card) {

    }

    private void validateCommentOwnership(User user, Comment comment) {
        if (!comment.getUser().equals(user)) {
            throw new CommentException(CommentErrorCode.NO_PERMISSION);
        }
    }

    private CommentResponseDto toResponseDto(Comment comment) {
        CommentResponseDto responseDto = new CommentResponseDto();
        responseDto.setId(comment.getId());
        responseDto.setUserId(comment.getUser().getId());
        responseDto.setCardId(comment.getCard().getId());
        responseDto.setText(comment.getText());
        return responseDto;
    }
}

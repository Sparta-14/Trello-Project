package com.spata14.trelloproject.board.controller;

import com.spata14.trelloproject.board.dto.BoardRequestDto;
import com.spata14.trelloproject.board.dto.BoardResponseDto;
import com.spata14.trelloproject.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/workspaces/{workspaceId}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 보드 생성
    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@PathVariable Long workspaceId,
                                                        @Valid @RequestBody BoardRequestDto dto,
                                                        @SessionAttribute(value = "USER_AUTH") String email) {

        BoardResponseDto response = boardService.create(dto, workspaceId, email);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 단건 보드 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long workspaceId,
                                                     @PathVariable Long boardId) {

        BoardResponseDto response = boardService.findById(boardId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 전체 보드 조회
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards(@PathVariable Long workspaceId) {
        List<BoardResponseDto> responseList = boardService.findAll(workspaceId);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // 보드 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long workspaceId,
                                                        @PathVariable Long boardId,
                                                        @Valid @RequestBody BoardRequestDto dto,
                                                        @SessionAttribute(value = "USER_AUTH") String email) {

        BoardResponseDto response = boardService.update(boardId, dto, email);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 보드 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long workspaceId,
                                            @PathVariable Long boardId) {
        boardService.delete(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



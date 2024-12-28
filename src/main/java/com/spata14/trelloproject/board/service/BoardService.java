package com.spata14.trelloproject.board.service;


import com.spata14.trelloproject.board.dto.BoardRequestDto;
import com.spata14.trelloproject.board.dto.BoardResponseDto;
import com.spata14.trelloproject.board.entity.Board;
import com.spata14.trelloproject.board.exception.BoardErrorCode;
import com.spata14.trelloproject.board.exception.BoardException;
import com.spata14.trelloproject.board.repository.BoardRepository;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.UserRole;
import com.spata14.trelloproject.user.repository.UserRepository;
import com.spata14.trelloproject.workspace.Workspace;
import com.spata14.trelloproject.workspace.WorkspaceUser;
import com.spata14.trelloproject.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponseDto create(BoardRequestDto dto, Long workspaceId, String email) {
        // 워크스페이스 조회
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new BoardException(BoardErrorCode.RESPONSE_INCORRECT));

        // 사용자의 email을 통해 User 객체를 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BoardException(BoardErrorCode.RESPONSE_INCORRECT));

        // 워크스페이스에서 해당 사용자의 권한 확인
        WorkspaceUser workspaceUser = workspace.getWorkspaceUsers().stream()
                .filter(wu -> wu.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new BoardException(BoardErrorCode.PERMISSION_DENIED));

        // 권한 검사: READ 권한은 보드 생성 불가
        if (workspaceUser.getUserRole() == UserRole.READ) {
            throw new BoardException(BoardErrorCode.PERMISSION_DENIED);
        }

        // 보드 제목 유효성 검사
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new BoardException(BoardErrorCode.TITLE_EMPTY);
        }

        // 보드 생성
        Board board = new Board(dto.getTitle(), dto.getColor(), dto.getImageUrl(), workspace);
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    // 보드 조회 (단건)
    @Transactional(readOnly = true)
    public BoardResponseDto findById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(BoardErrorCode.RESPONSE_INCORRECT));
        return new BoardResponseDto(board);
    }

    // 모든 보드 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto> findAll(Long workspaceId) {
        List<Board> boards = boardRepository.findByWorkspaceId(workspaceId);
        return boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }

    // 보드 수정
    @Transactional
    public BoardResponseDto update(Long boardId, BoardRequestDto dto, String email) {
        // 보드 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(BoardErrorCode.RESPONSE_INCORRECT));

        // 사용자의 email을 통해 User 객체를 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BoardException(BoardErrorCode.RESPONSE_INCORRECT));

        // 워크스페이스에서 해당 사용자의 권한 확인
        Workspace workspace = board.getWorkspace();
        WorkspaceUser workspaceUser = workspace.getWorkspaceUsers().stream()
                .filter(wu -> wu.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new BoardException(BoardErrorCode.PERMISSION_DENIED));

        // 권한 검사: READ 권한은 보드 수정 불가
        if (workspaceUser.getUserRole() == UserRole.READ) {
            throw new BoardException(BoardErrorCode.PERMISSION_DENIED);
        }

        // 보드 제목 유효성 검사
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new BoardException(BoardErrorCode.TITLE_EMPTY);
        }

        // 보드 수정
        board.update(dto.getTitle(), dto.getColor(), dto.getImageUrl());

        return new BoardResponseDto(board);
    }

    // 보드 삭제
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(BoardErrorCode.RESPONSE_INCORRECT));

        boardRepository.delete(board);
    }
}





package com.spata14.trelloproject.board.service;


import com.spata14.trelloproject.board.dto.BoardRequestDto;
import com.spata14.trelloproject.board.dto.BoardResponseDto;
import com.spata14.trelloproject.board.entity.Board;
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

    // 보드 생성
    @Transactional
    public BoardResponseDto create(BoardRequestDto dto, Long workspaceId, String email) {
        // 워크스페이스 조회
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("워크스페이스를 찾을 수 없습니다"));

        // 사용자의 email을 통해 User 객체를 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        // 워크스페이스에서 해당 사용자의 권한 확인
        WorkspaceUser workspaceUser = workspace.getWorkspaceUsers().stream()
                .filter(wu -> wu.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 유저는 워크스페이스의 멤버가 아닙니다"));

        // 권한 검사: READ 권한은 보드 생성 불가
        if (workspaceUser.getUserRole() == UserRole.READ) {
            throw new RuntimeException("읽기 권한을 가진 유저는 보드 생성이 불가능합니다");
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
                .orElseThrow(() -> new RuntimeException("Board not found"));
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
                .orElseThrow(() -> new RuntimeException("Board not found"));

        // 사용자의 email을 통해 User 객체를 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        // 워크스페이스에서 해당 사용자의 권한 확인
        Workspace workspace = board.getWorkspace();
        WorkspaceUser workspaceUser = workspace.getWorkspaceUsers().stream()
                .filter(wu -> wu.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 유저는 워크스페이스의 멤버가 아닙니다"));

        // 권한 검사: READ 권한은 보드 수정 불가
        if (workspaceUser.getUserRole() == UserRole.READ) {
            throw new RuntimeException("읽기 권한을 가진 유저는 보드를 수정할 수 없습니다.");
        }

        // 보드 수정
        board.update(dto.getTitle(), dto.getColor(), dto.getImageUrl());

        return new BoardResponseDto(board);
    }

    // 보드 삭제
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        boardRepository.delete(board);
    }
}





package com.spata14.trelloproject.list.service;

import com.spata14.trelloproject.board.entity.Board;
import com.spata14.trelloproject.board.repository.BoardRepository;
import com.spata14.trelloproject.list.entity.Lists;
import com.spata14.trelloproject.list.repository.ListsRepository;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.UserRole;
import com.spata14.trelloproject.user.repository.UserRepository;
import com.spata14.trelloproject.workspace.WorkspaceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListsService {

    private final ListsRepository listsRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 리스트 생성
    @Transactional
    public Lists createList(String email, Long boardId, String title, Long order) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("보드를 찾을 수 없습니다"));

        WorkspaceUser workspaceUser = board.getWorkspace().getWorkspaceUsers().stream()
                .filter(wu -> wu.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 유저는 워크스페이스의 멤버가 아닙니다"));

        if (workspaceUser.getUserRole() == UserRole.READ) {
            throw new RuntimeException("읽기 권한을 가진 유저는 리스트 생성이 불가능합니다");
        }

        Lists list = new Lists(title, order, board);
        listsRepository.save(list);

        return list;
    }

    // 리스트 수정
    @Transactional
    public Lists updateList(String email, Long listId, String title, Long order) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        Lists list = listsRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("리스트를 찾을 수 없습니다"));

        WorkspaceUser workspaceUser = list.getBoard().getWorkspace().getWorkspaceUsers().stream()
                .filter(wu -> wu.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 유저는 워크스페이스의 멤버가 아닙니다"));

        if (workspaceUser.getUserRole() == UserRole.READ) {
            throw new RuntimeException("읽기 권한을 가진 유저는 리스트 수정이 불가능합니다");
        }

        list.update(title, order);
        listsRepository.save(list);

        return list;
    }

    // 리스트 삭제
    @Transactional
    public void deleteList(String email, Long listId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        Lists list = listsRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("리스트를 찾을 수 없습니다"));

        WorkspaceUser workspaceUser = list.getBoard().getWorkspace().getWorkspaceUsers().stream()
                .filter(wu -> wu.getUser().equals(user))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 유저는 워크스페이스의 멤버가 아닙니다"));

        if (workspaceUser.getUserRole() == UserRole.READ) {
            throw new RuntimeException("읽기 권한을 가진 유저는 리스트 삭제가 불가능합니다");
        }

        listsRepository.delete(list);
    }
}
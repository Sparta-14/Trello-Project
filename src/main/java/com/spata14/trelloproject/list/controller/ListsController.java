package com.spata14.trelloproject.list.controller;


import com.spata14.trelloproject.list.dto.ListsRequestDto;
import com.spata14.trelloproject.list.dto.ListsResponseDto;
import com.spata14.trelloproject.list.entity.Lists;
import com.spata14.trelloproject.list.service.ListsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspaces/{workspaceId}/boards/{boardId}/lists")
@RequiredArgsConstructor
public class ListsController {

    private final ListsService listsService;

    // 리스트 생성
    @PostMapping
    public ResponseEntity<ListsResponseDto> createList(@PathVariable Long workspaceId,
                                                       @PathVariable Long boardId,
                                                       @RequestBody ListsRequestDto dto,
                                                       @SessionAttribute(value = "USER_AUTH") String email) {
        Lists list = listsService.createList(email, boardId, dto.getTitle(), dto.getOrder());
        return new ResponseEntity<>(new ListsResponseDto(list), HttpStatus.CREATED);
    }

    // 리스트 수정
    @PutMapping("/{listId}")
    public ResponseEntity<ListsResponseDto> updateList(@PathVariable Long workspaceId,
                                                       @PathVariable Long boardId,
                                                       @PathVariable Long listId,
                                                       @RequestBody ListsRequestDto dto,
                                                       @SessionAttribute(value = "USER_AUTH") String email) {
        Lists list = listsService.updateList(email, listId, dto.getTitle(), dto.getOrder());
        return new ResponseEntity<>(new ListsResponseDto(list), HttpStatus.OK);
    }

    // 리스트 삭제
    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(@PathVariable Long workspaceId,
                                           @PathVariable Long boardId,
                                           @PathVariable Long listId,
                                           @SessionAttribute(value = "USER_AUTH") String email) {
        listsService.deleteList(email, listId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

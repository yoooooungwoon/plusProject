package com.example.plusproject.todo;

import com.example.plusproject.CommonResponseDto;
import com.example.plusproject.user.UserDetailsImpl;
import com.example.plusproject.user.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> postTodo(@RequestBody TodoRequestDto todoRequestDto,
        @AuthenticationPrincipal
        UserDetailsImpl userDetails) {
        TodoResponseDto responseDto = todoService.createTodo(todoRequestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(responseDto);
    }

    @GetMapping("{todoId}")
    public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId) {
        try {
            TodoResponseDto responseDto = todoService.getTodoDto(todoId);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),
                HttpStatus.BAD_REQUEST.value()));
        }

    }

    @GetMapping
    public ResponseEntity<List<TodoListResponseDto>> getTodoList() {
        List<TodoListResponseDto> response = new ArrayList<>();

        Map<UserDto, List<TodoResponseDto>> responseDTOMap = todoService.getUserTodoMap();

        responseDTOMap.forEach((key, value) -> response.add(new TodoListResponseDto(key, value)));

        return ResponseEntity.ok().body(response);
    }
    @PutMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> putTodo(@PathVariable Long todoId,@RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            TodoResponseDto responseDto = todoService.updateTodo(todoId,todoRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (RejectedExecutionException | IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }

    }

    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<CommonResponseDto> patchTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            TodoResponseDto responseDto = todoService.completeTodo(todoId, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (RejectedExecutionException | IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }

    }


}

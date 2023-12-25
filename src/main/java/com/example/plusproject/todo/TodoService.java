package com.example.plusproject.todo;

import com.example.plusproject.user.User;
import com.example.plusproject.user.UserDto;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto dto, User user){
        Todo todo = new Todo(dto);
        todo.setUser(user);
        todoRepository.save(todo);

        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodoDto(Long todoId) {
        Todo todo = getTodo(todoId);
        return new TodoResponseDto(todo);
    }

    public Map<UserDto, List<TodoResponseDto>> getUserTodoMap() {
        Map<UserDto, List<TodoResponseDto>> userTodoMap = new HashMap<>();

        List<Todo> todoList = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")); // 작성일 기준 내림차순

        todoList.forEach(todo -> {
            var userDto = new UserDto(todo.getUser());
            var todoDto = new TodoResponseDto(todo);
            if (userTodoMap.containsKey(userDto)) {
                // 유저 할일목록에 항목을 추가
                userTodoMap.get(userDto).add(todoDto);
            } else {
                // 유저 할일목록을 새로 추가
                userTodoMap.put(userDto, new ArrayList<>(List.of(todoDto)));
            }
        });

        return userTodoMap;
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoRequestDto todoRequestDto, User user) {
        Todo todo = getUserTodo(todoId, user);

        todo.setTitle(todoRequestDto.getTitle());
        todo.setContext(todoRequestDto.getContext());
        return new TodoResponseDto(todo);

    }

    @Transactional
    public TodoResponseDto completeTodo(Long todoId, User user) {
        Todo todo = getUserTodo(todoId, user);

        todo.complete();
        return new TodoResponseDto(todo);

    }


    public Todo getTodo(Long todoId) {
        return todoRepository.findById(todoId)
            .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 할일ID 입니다."));
    }
    public Todo getUserTodo(Long todoId, User user) {
        Todo todo = getTodo(todoId);

        if(user.getId().equals(todo.getUser().getId())){
            throw new RejectedExecutionException("작성자만 수정가능합니다.");
        }
        return todo;
    }
}

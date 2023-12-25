package com.example.plusproject.todo;

import com.example.plusproject.CommonResponseDto;
import com.example.plusproject.user.UserDto;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoResponseDto extends CommonResponseDto {
    private Long id;
    private String title;
    private String context;
    private LocalDateTime createDate;
    private UserDto user;
    private Boolean isCompleted;

    public TodoResponseDto(Todo todo) {
        this.id=todo.getId();
        this.title=todo.getTitle();
        this.context=todo.getContext();
        this.isCompleted=todo.getIsCompleted();
        this.createDate = todo.getCreateDate();
        this.user= new UserDto(todo.getUser());
    }
}

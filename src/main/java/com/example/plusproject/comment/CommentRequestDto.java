package com.example.plusproject.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long todoId;
    private String text;
}

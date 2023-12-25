package com.example.plusproject.comment;

import com.example.plusproject.CommonResponseDto;
import com.example.plusproject.user.UserDto;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponseDto extends CommonResponseDto {
    private Long id;
    private String text;
    private UserDto user;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = new UserDto(comment.getUser());
        this.createDate = comment.getCreateDate();
    }
}

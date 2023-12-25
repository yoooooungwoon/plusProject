package com.example.plusproject.todo;

import com.example.plusproject.comment.Comment;
import com.example.plusproject.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String context;
    @Column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "todo")
    private List<Comment> comments;
    @Column
    private Boolean isCompleted;
    public Todo(TodoRequestDto dto){
        this.title=dto.getTitle();
        this.context = dto.getContext();
        this.createDate=LocalDateTime.now();
        this.isCompleted = false;
    }
    public void setUser(User user){
        this.user= user;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public void setContext(String context){
        this.context = context;
    }

    public void complete(){
        this.isCompleted = true;
    }
}

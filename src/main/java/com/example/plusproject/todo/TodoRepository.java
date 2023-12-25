package com.example.plusproject.todo;

import com.example.plusproject.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {

}

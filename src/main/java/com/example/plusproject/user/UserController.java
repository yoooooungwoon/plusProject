package com.example.plusproject.user;

import com.example.plusproject.CommonResponseDto;
import com.example.plusproject.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto){
        try {
            userService.signup(userRequestDto);
        } catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(new CommonResponseDto("중복된 유저이름입니다.",HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(201).body(new CommonResponseDto("회원가입성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/nameCheck")
    public ResponseEntity<CommonResponseDto> nameCheck(@RequestBody UserRequestDto userRequestDto){
        try {
            userService.nameCheck(userRequestDto);
        } catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().body(new CommonResponseDto("중복된 유저이름입니다.",HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(201).body(new CommonResponseDto("사용가능한 유저 이름입니다", HttpStatus.OK.value()));
    }
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response){
        try {
            userService.login(userRequestDto);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(userRequestDto.getUsername()));
        return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
    }

}

package com.example.plusproject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public void signup(UserRequestDto userRequestDto) {
        String username= userRequestDto.getUsername();
        String password= passwordEncoder.encode(userRequestDto.getPassword());


        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저입니다");
        }
        User user = new User(username, password);
        userRepository.save(user);

    }
    public void nameCheck(UserRequestDto userRequestDto){
        String username= userRequestDto.getUsername();

        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저입니다");
        }
    }


    public void login(UserRequestDto userRequestDto) {
        String username= userRequestDto.getUsername();
        String password= userRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
            .orElseThrow(()-> new IllegalArgumentException("아이디 또는 패스워드를 확인해주세요"));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("아이디 또는 패스워드를 확인해주세요");
        }
    }
}

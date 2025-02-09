package com.cn.hsbc.controller;

import com.cn.hsbc.dao.UserRepository;
import com.cn.hsbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // 根据用户名查找用户
        java.util.Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            // 验证密码
            if (existingUser.getPassword().equals(user.getPassword())) {
                return new ResponseEntity<>("登录成功", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("密码错误", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("用户不存在", HttpStatus.UNAUTHORIZED);
        }
    }
}
package com.getyourtickets.implement;

import com.getyourtickets.dto.usersignup.UserSignupRequest;
import com.getyourtickets.exception.ErrorEnum;
import com.getyourtickets.exception.GytException;
import com.getyourtickets.mapper.UserMapper;
import com.getyourtickets.model.User;
import com.getyourtickets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByUsername(String username) {
        return Optional.ofNullable(userMapper.getUserByUsername(username))
                .orElseThrow(() -> new GytException(ErrorEnum.USER_NOT_FOUND));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertUser(UserSignupRequest request) {
        User existingUser = userMapper.getUserByUsername(request.getUsername());
        if (existingUser != null) {
            throw new GytException(ErrorEnum.USER_ALREADY_EXISTS);
        }

        Map<String, Object> insertMap = new HashMap<>();
        insertMap.put("username", request.getUsername());
        insertMap.put("password", hashPassword(request.getPassword()));
        insertMap.put("email", request.getEmail());
        insertMap.put("fullName", request.getFullName());

        userMapper.insertUser(insertMap);

    }

    private String hashPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(password);
    }
}

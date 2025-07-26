package com.getyourtickets.implement;

import com.getyourtickets.dto.userlogin.UserLoginRequest;
import com.getyourtickets.mapper.UserMapper;
import com.getyourtickets.model.User;
import com.getyourtickets.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserMapper userMapper;
    @Override
    public boolean authenticate(UserLoginRequest request) {
        User user = userMapper.getUserByUsername(request.getUsername());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return true;
        }

        return false;
    }


}

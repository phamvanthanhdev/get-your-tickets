package com.getyourtickets.service;

import com.getyourtickets.dto.usersignup.UserSignupRequest;
import com.getyourtickets.dto.usersignup.UserSignupResponse;
import com.getyourtickets.exception.GytException;
import com.getyourtickets.mapper.UserMapper;
import com.getyourtickets.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockitoBean
    private UserMapper userMapper;

    private UserSignupRequest request;
    private UserSignupResponse response;

    @BeforeEach
    void initData() {
        request = UserSignupRequest.builder()
                .username("testuser")
                .password("testpassword")
                .email("testemail@gmail.com")
                .fullName("Test User")
                .build();

        response = UserSignupResponse.builder()
                .username("testuser")
                .message("User registered successfully")
                .build();
    }

    @Test
    void insertUser_success_Test() throws Exception {
        // GIVEN
        Mockito.when(userMapper.getUserByUsername(Mockito.anyString()))
                .thenReturn(null);
        Mockito.when(userMapper.insertUser(Mockito.anyMap())).thenReturn(1);

        // WHEN
        UserSignupResponse result = userService.insertUser(request);

        // THEN
        Assertions.assertThat(result.getUsername()).isEqualTo("testuser");
        Assertions.assertThat(result.getMessage()).isEqualTo("User registered successfully");
    }

    @Test
    void insertUser_existed_failure_Test() throws Exception {
        // GIVEN
        Mockito.when(userMapper.getUserByUsername(Mockito.anyString()))
                .thenReturn(new User());

        // WHEN
        GytException exception = assertThrows(GytException.class,
                () -> userService.insertUser(request));

        // THEN
        Assertions.assertThat(exception.getErrorEnum().getCode()).isEqualTo(409);
    }
}

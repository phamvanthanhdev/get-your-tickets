package com.getyourtickets.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourtickets.dto.usersignup.UserSignupRequest;
import com.getyourtickets.dto.usersignup.UserSignupResponse;
import com.getyourtickets.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;

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
    void signup_success_Test() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        // GIVEN
        Mockito.when(userService.insertUser(Mockito.any()))
                .thenReturn(response);
        // WHEN, THEN
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(201))
                .andExpect(MockMvcResultMatchers.jsonPath("result.username").value("testuser"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.message").value("User registered successfully"));

    }

    @Test
    void signup_failure_InvalidEmail_Test() throws Exception {
        request.setEmail("invalid-email");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);

        // GIVEN

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Invalid email format"));
    }
}

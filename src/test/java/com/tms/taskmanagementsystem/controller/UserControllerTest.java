package com.tms.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.taskmanagementsystem.domain.User;
import com.tms.taskmanagementsystem.security.filter.JwtAuthenticationFilter;
import com.tms.taskmanagementsystem.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    private static final String SURNAME = "Любенков";

    private static final String NAME = "Денис";

    private static final Integer USER_ID = 2;

    @MockBean
    UserService userService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    static List<User> userList = new ArrayList<>();

    static User user = new User();

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    public static void beforeAll() {
        user.setId(USER_ID);
        user.setFirstName(NAME);
        user.setLastName(SURNAME);
        user.setEmail("denisliubenkov@yandex.by");
        userList.add(user);
    }

    @Test
    public void createUserTest() throws Exception {
        UserService mockCS = Mockito.mock(UserService.class);
        Mockito.doNothing().when(mockCS).createUser(any());
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateUserTest() throws Exception {
        UserService mockCS = Mockito.mock(UserService.class);
        Mockito.doNothing().when(mockCS).updateUser(any());
        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUserTest() throws Exception {
        UserService mockCS = Mockito.mock(UserService.class);
        Mockito.doNothing().when(mockCS).deleteUserById(anyInt());

        mockMvc.perform(delete("/user/2"))
                .andExpect(status().isNoContent());
    }
}
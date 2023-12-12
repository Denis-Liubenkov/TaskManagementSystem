package com.tms.taskmanagementsystem.service;

import com.tms.taskmanagementsystem.domain.User;
import com.tms.taskmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Integer ID_USER = 4;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeAll
    public static void beforeAll() {
        Authentication authenticationMock = mock(Authentication.class);
        SecurityContext securityContextMock = mock(SecurityContext.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
    }

    @Test
    public void createUser() {
        userService.createUser(new User());
        Mockito.verify(userRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateUser() {
        userService.updateUser(new User());
        Mockito.verify(userRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    public void deleteUser() {
        userService.deleteUserById(ID_USER);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(anyInt());
    }
}

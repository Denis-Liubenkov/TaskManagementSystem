package com.tms.taskmanagementsystem.service;

import com.tms.taskmanagementsystem.domain.Task;
import com.tms.taskmanagementsystem.domain.User;
import com.tms.taskmanagementsystem.repository.TaskRepository;
import com.tms.taskmanagementsystem.repository.UserRepository;
import com.tms.taskmanagementsystem.security.domain.SecurityCredentials;
import com.tms.taskmanagementsystem.security.repository.SecurityCredentialsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    private static final Integer ID_TASK = 10;

    private static final String EMAIL = "den@yandex.by";

    private static final Integer ID_USER = 4;

    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private SecurityCredentialsRepository securityCredentialsRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeAll
    public static void beforeAll() {
        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        SecurityContextHolder.setContext(securityContextMock);
    }

    @Test
    public void getTaskTest() {
        SecurityContextHolder.setContext(new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return null;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return false;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                    }

                    @Override
                    public String getName() {
                        return EMAIL;
                    }
                };
            }

            @Override
            public void setAuthentication(Authentication authentication) {
            }
        });
        SecurityCredentials securityCredentials = new SecurityCredentials();
        securityCredentials.setUserId(ID_USER);
        Task task = new Task();
        task.setCreator(new User());
        task.setId(ID_TASK);
        Mockito.when(taskRepository.findById(ID_TASK)).thenReturn(Optional.of(task));
        taskService.getTask(ID_TASK);
        Mockito.verify(taskRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void createTask() {
        SecurityContextHolder.setContext(new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return null;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return false;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                    }

                    @Override
                    public String getName() {
                        return EMAIL;
                    }
                };
            }

            @Override
            public void setAuthentication(Authentication authentication) {
            }
        });
        SecurityCredentials securityCredentials = new SecurityCredentials();
        securityCredentials.setUserId(ID_USER);
        Mockito.when(securityCredentialsRepository.findByEmail(EMAIL)).thenReturn(Optional.of(securityCredentials));
        User user = new User();
        user.setId(ID_USER);
        Mockito.when(userRepository.findById(ID_USER)).thenReturn(Optional.of(user));
        Task task = new Task();
        task.setCreator(new User());
        task.setId(ID_TASK);
        taskService.createTask(new Task());
        Mockito.verify(taskRepository, Mockito.times(1)).save(any());
    }

    @Test
    public void updateTask() {
        SecurityContextHolder.setContext(new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return null;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return false;
                    }

                    @Override
                    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                    }

                    @Override
                    public String getName() {
                        return EMAIL;
                    }
                };
            }

            @Override
            public void setAuthentication(Authentication authentication) {
            }
        });
        SecurityCredentials securityCredentials = new SecurityCredentials();
        securityCredentials.setUserId(ID_USER);
        Mockito.when(securityCredentialsRepository.findByEmail(EMAIL)).thenReturn(Optional.of(securityCredentials));
        User user = new User();
        user.setId(ID_USER);
        Mockito.when(userRepository.findById(ID_USER)).thenReturn(Optional.of(user));
        Task task = new Task();
        task.setCreator(new User());
        task.setId(ID_TASK);
        taskService.updateTask(new Task());
        Mockito.verify(taskRepository, Mockito.times(1)).saveAndFlush(any());
    }

    @Test
    public void deleteTask() {
        taskService.deleteTaskById(ID_TASK);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(anyInt());
    }
}

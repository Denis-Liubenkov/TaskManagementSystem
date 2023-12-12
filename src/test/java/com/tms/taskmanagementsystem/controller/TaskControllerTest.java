package com.tms.taskmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.taskmanagementsystem.domain.Priority;
import com.tms.taskmanagementsystem.domain.Status;
import com.tms.taskmanagementsystem.domain.Task;
import com.tms.taskmanagementsystem.security.filter.JwtAuthenticationFilter;
import com.tms.taskmanagementsystem.service.TaskService;
import org.hamcrest.Matchers;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TaskService taskService;

    static List<Task> taskList = new ArrayList<>();

    static Task task = new Task();

    private static final Integer ID_TASK = 2;

    @BeforeAll
    public static void beforeAll() {
        task.setId(ID_TASK);
        task.setStatus(Status.COMPLETED);
        task.setPriority(Priority.HIGH);
        taskList.add(task);
    }

    @Test
    public void getTaskTest() throws Exception {
        Mockito.when(taskService.getTask(anyInt())).thenReturn(Optional.ofNullable(task));
        mockMvc.perform(get("/task/2"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTasksTestReturnListOfTasks() throws Exception {
        Mockito.when(taskService.getTasks()).thenReturn(taskList);
        mockMvc.perform(get("/task/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void createTaskTest() throws Exception {
        TaskService mockOS = Mockito.mock(TaskService.class);
        Mockito.doNothing().when(mockOS).createTask(any());
        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTaskTest() throws Exception {
        TaskService mockOS = Mockito.mock(TaskService.class);
        Mockito.doNothing().when(mockOS).updateTask(any());
        mockMvc.perform(put("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTaskTest() throws Exception {
        TaskService mockOS = Mockito.mock(TaskService.class);
        Mockito.doNothing().when(mockOS).deleteTaskById(anyInt());
        mockMvc.perform(delete("/task/2"))
                .andExpect(status().isNoContent());
    }
}

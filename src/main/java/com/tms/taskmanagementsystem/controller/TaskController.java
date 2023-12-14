package com.tms.taskmanagementsystem.controller;

import com.tms.taskmanagementsystem.domain.*;
import com.tms.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.tms.taskmanagementsystem.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/task")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks are found"),
            @ApiResponse(responseCode = "404", description = "Tasks are not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllTasks(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Map<String, Object> tasks = taskService.getTasks(title, page, size);
        log.info("List of tasks is found!");
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(summary = "Get tasks by priority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks are found"),
            @ApiResponse(responseCode = "404", description = "Tasks are not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/filter/priority")
    public ResponseEntity<List<Task>> taskAsFilteredList(@RequestParam Priority priority) {
        List<Task> filteredTaskAsList = taskService.filteredTaskAsList(priority);
        log.info("List of tasks by priority: " + priority + " is found!");
        return new ResponseEntity<>(filteredTaskAsList, HttpStatus.OK);
    }

    @Operation(summary = "Get tasks by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks are found"),
            @ApiResponse(responseCode = "404", description = "Tasks are not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/filter/status")
    public ResponseEntity<List<Task>> taskAsFilteredList(@RequestParam Status status) {
        List<Task> filteredTaskAsList = taskService.filteredTaskAsList(status);
        log.info("Task with id: " + status + " is found!");
        return new ResponseEntity<>(filteredTaskAsList, HttpStatus.OK);
    }

    @Operation(summary = "Get task", description = "Get one task , need to pass the input parameter task`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task is found"),
            @ApiResponse(responseCode = "404", description = "Task is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Integer id) {
        Task task = taskService.getTask(id).orElseThrow(TaskNotFoundException::new);
        log.info("Task with id: " + id + " is found!");
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Operation(summary = "Creating task", description = "Create task,  need to pass object Task in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task is created"),
            @ApiResponse(responseCode = "409", description = "Task is not created"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping
    public ResponseEntity<HttpStatus> createTask(@RequestBody Task task) {
        taskService.createTask(task);
        log.info("Task with id: " + task.getId() + " is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Assignee task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task is assigned"),
            @ApiResponse(responseCode = "409", description = "Task is not assigned"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping("/{taskId}/assign")
    public ResponseEntity<HttpStatus> assignTask(@PathVariable Integer taskId, @RequestBody AssigneeDTO assigneeDTO) {
        taskService.assignedTask(assigneeDTO);
        log.info("Task with id: " + taskId + " is assigned!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Updating task", description = "Update task, need to pass object Task in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task is updated"),
            @ApiResponse(responseCode = "409", description = "Task is not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PutMapping
    public ResponseEntity<HttpStatus> updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
        log.info("Task with id: " + task.getId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Updating taskStatus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TaskStatus is updated"),
            @ApiResponse(responseCode = "409", description = "TaskStatus is not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PutMapping("/{taskId}/status")
    public ResponseEntity<HttpStatus> updateTaskStatus(@PathVariable Integer taskId, @RequestBody StatusDTO statusDTO) {
        taskService.changeStatusTask(statusDTO);
        log.info("Status of task with id: " + taskId + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Deleting task", description = "Delete task,  need to pass the input parameter task`s id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task is deleted"),
            @ApiResponse(responseCode = "409", description = "Task is not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Integer id) {
        taskService.deleteTaskById(id);
        log.info("Task with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

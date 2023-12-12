package com.tms.taskmanagementsystem.controller;

import com.tms.taskmanagementsystem.domain.Task;
import com.tms.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.tms.taskmanagementsystem.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get list of tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tasks is found"),
            @ApiResponse(responseCode = "404", description = "List of tasks is not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/list")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task>tasks = taskService.getTasks();
        if (!tasks.isEmpty()) {
            log.info("List of tasks is found!");
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            log.info("List of tasks is not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @Operation(summary = "Get tasks with filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks are found with filter"),
            @ApiResponse(responseCode = "404", description = "Tasks are not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @GetMapping("/search/filter")
    public ResponseEntity getAllTasksWithFilter(@RequestParam("query") String query, Pageable pageable) {
        return ResponseEntity.ok(taskService.filterTasks(query, pageable));
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

    @Operation(summary = "Updating task", description = "Update task, need to pass object Task in format JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task is updates"),
            @ApiResponse(responseCode = "409", description = "Task is not updated"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PutMapping
    public ResponseEntity<HttpStatus> updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
        log.info("Task with id: " + task.getId() + " is updated!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<HttpStatus> updateTaskStatus(@PathVariable Integer taskId, @RequestBody Task task) {
        taskService.getTask(taskId);
        taskService.updateTaskStatus(task);
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

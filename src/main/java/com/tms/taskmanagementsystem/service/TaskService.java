package com.tms.taskmanagementsystem.service;

import com.tms.taskmanagementsystem.domain.*;
import com.tms.taskmanagementsystem.exceptions.NotAccessException;
import com.tms.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.tms.taskmanagementsystem.security.domain.SecurityCredentials;
import com.tms.taskmanagementsystem.security.repository.SecurityCredentialsRepository;
import com.tms.taskmanagementsystem.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.tms.taskmanagementsystem.repository.TaskRepository;
import com.tms.taskmanagementsystem.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final SecurityCredentialsRepository securityCredentialsRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, SecurityCredentialsRepository securityCredentialsRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.securityCredentialsRepository = securityCredentialsRepository;
    }

    public Map<String, Object> getTasks(String title, int page, int size) {
        List<Task> tasks;
        Pageable paging = PageRequest.of(page, size);
        Page<Task> pageTask;
        if (title == null)
            pageTask = taskRepository.findAll(paging);
        else
            pageTask = taskRepository.findByTitle(title, paging);
        tasks = pageTask.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("tasks", tasks);
        response.put("currentPage", pageTask.getNumber());
        response.put("totalItems", pageTask.getTotalElements());
        response.put("totalPages", pageTask.getTotalPages());
        return response;
    }

    public Optional<Task> getTask(Integer id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException();
        }
        return task;
    }

    public void createTask(Task task) {
        task.setTitle(task.getTitle());
        task.setDescription(task.getDescription());
        task.setStatus(task.getStatus());
        task.setPriority(task.getPriority());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> userLogin = securityCredentialsRepository.findByEmail(login);
        if (userLogin.isEmpty()) {
            throw new UserNotFoundException();
        }
        Optional<User> userId = userRepository.findById(userLogin.get().getUserId());
        userId.ifPresent(task::setCreator);
        taskRepository.save(task);
    }

    public void assignedTask(AssigneeDTO assigneeDTO) {
        Task task = getTask(assigneeDTO);
        Integer userId = getUserIdFromSession();
        if (!userId.equals(task.getCreator())) {
            throw new NotAccessException();
        }
        Optional<User> assigneeUserOptional = userRepository.findById(assigneeDTO.getAssigneeId());
        if (assigneeUserOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        User assignee = assigneeUserOptional.get();
        task.setAssignee(assignee);
        assignee.addTaskList(task);
        taskRepository.save(task);
    }

    public void changeStatusTask(StatusDTO statusDTO) {
        Optional<Task> taskOptional = taskRepository.findById(statusDTO.getTaskId());
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException();
        }
        Task task = taskOptional.get();
        Integer userId = getUserIdFromSession();
        Optional<User> assigneeUserOptional = userRepository.findById(statusDTO.getAssigneeId());
        if (assigneeUserOptional.isEmpty()) {
            throw new UserNotFoundException();
        }
        User assignee = assigneeUserOptional.get();
        if (userId.equals(assignee.getId()))
            task.setStatus(statusDTO.getStatus());
        taskRepository.save(task);
    }

    private Task getTask(AssigneeDTO assigneeDTO) {
        Optional<Task> taskOptional = taskRepository.findById(assigneeDTO.getTaskId());
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException();
        }
        return taskOptional.get();
    }

    private Integer getUserIdFromSession() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> user = securityCredentialsRepository.findByEmail(login);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return user.get().getUserId();
    }

    public void updateTaskStatus(StatusDTO statusDTO) {
        Optional<Task> task = taskRepository.findById(statusDTO.getTaskId());
        if (task.isEmpty()) {
            throw new TaskNotFoundException();
        }
        Task taskSaved = task.get();
        taskSaved.setStatus(statusDTO.getStatus());
        taskRepository.saveAndFlush(taskSaved);
    }

    public List<Task> filteredTaskAsList(Priority priority) {
        return taskRepository.findByPriority(priority);
    }

    public List<Task> filteredTaskAsList(Status status) {
        return taskRepository.findByStatus(status);
    }

    public void updateTask(Task taskOptional) {
        taskOptional.setId(taskOptional.getId());
        taskOptional.setTitle(taskOptional.getTitle());
        taskOptional.setDescription(taskOptional.getDescription());
        taskOptional.setStatus(taskOptional.getStatus());
        taskOptional.setPriority(taskOptional.getPriority());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> userLogin = securityCredentialsRepository.findByEmail(login);
        if (userLogin.isEmpty()) {
            throw new UserNotFoundException();
        }
        Optional<User> userId = userRepository.findById(userLogin.get().getUserId());
        userId.ifPresent(taskOptional::setCreator);
        taskRepository.saveAndFlush(taskOptional);
    }

    public void deleteTaskById(Integer id) {
        taskRepository.deleteById(id);
    }
}

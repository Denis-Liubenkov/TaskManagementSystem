package com.tms.taskmanagementsystem.service;

import com.tms.taskmanagementsystem.domain.Task;
import com.tms.taskmanagementsystem.domain.User;
import com.tms.taskmanagementsystem.exceptions.ListOfTasksNotFoundException;
import com.tms.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.tms.taskmanagementsystem.security.domain.SecurityCredentials;
import com.tms.taskmanagementsystem.security.repository.SecurityCredentialsRepository;
import com.tms.taskmanagementsystem.exceptions.UserNotFoundException;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.tms.taskmanagementsystem.repository.TaskRepository;
import com.tms.taskmanagementsystem.repository.UserRepository;

import java.util.List;
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

    public List<Task> getTasks() {
        List<Task> taskList = taskRepository.findAll();
        if (taskList.isEmpty()) {
            throw new ListOfTasksNotFoundException();
        }
        return taskList;
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
        userId.ifPresent(task::setUser);
        taskRepository.save(task);
    }

   public PaginatedTaskResponse filterTasks(String priority, Pageable pageable){
       Page<Task>tasks=taskRepository.findAllByStatusAndPriority(priority,pageable);
       return PaginatedTaskResponse.builder()
   }

    public void updateTaskStatus(Task task){
      task.setStatus(task.getStatus());
    }

    public void updateTask(Task task) {
        task.setId(task.getId());
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
        userId.ifPresent(task::setUser);
        taskRepository.saveAndFlush(task);
    }

    public void deleteTaskById(Integer id) {
        taskRepository.deleteById(id);
    }
}

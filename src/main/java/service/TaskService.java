package service;

import domain.Task;
import domain.User;
import exceptions.TaskNotFoundException;
import exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

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
            throw new TaskNotFoundException();
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
        task.setExecutor(task.getExecutor());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> userLogin = securityCredentialsRepository.findByUserLogin(login);
        if (userLogin.isEmpty()) {
            throw new UserNotFoundException();
        }
        Optional<User> userId = userRepository.findById(userLogin.get().getUserId());
        userId.ifPresent(task::setUser);
        taskRepository.save(task);
    }

    public void updateTask(Task task) {
        task.setId(task.getId());
        task.setTitle(task.getTitle());
        task.setDescription(task.getDescription());
        task.setStatus(task.getStatus());
        task.setPriority(task.getPriority());
        task.setExecutor(task.getExecutor());
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<SecurityCredentials> userLogin = securityCredentialsRepository.findByUserLogin(login);
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

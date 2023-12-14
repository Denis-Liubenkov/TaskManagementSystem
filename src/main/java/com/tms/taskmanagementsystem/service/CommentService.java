package com.tms.taskmanagementsystem.service;

import com.tms.taskmanagementsystem.domain.Comments;
import com.tms.taskmanagementsystem.domain.CommentsDTO;
import com.tms.taskmanagementsystem.domain.Task;
import com.tms.taskmanagementsystem.exceptions.TaskNotFoundException;
import com.tms.taskmanagementsystem.repository.CommentsRepository;
import com.tms.taskmanagementsystem.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentsRepository commentsRepository;

    private final TaskRepository taskRepository;

    public CommentService(CommentsRepository commentsRepository, TaskRepository taskRepository) {
        this.commentsRepository = commentsRepository;
        this.taskRepository = taskRepository;
    }

    public void addComment(CommentsDTO commentsDTO) {
        Optional<Task> taskOptional = taskRepository.findById(commentsDTO.getTaskId());
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException();
        }
        Comments comments = new Comments();
        Task task = taskOptional.get();
        comments.setTask(task);
        task.addComments(comments);
        commentsRepository.save(comments);
    }

    public void deleteCommentById(Integer id) {
        commentsRepository.deleteById(id);
    }
}

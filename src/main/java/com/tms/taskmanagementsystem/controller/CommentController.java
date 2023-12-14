package com.tms.taskmanagementsystem.controller;

import com.tms.taskmanagementsystem.domain.CommentsDTO;
import com.tms.taskmanagementsystem.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Adding comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment is added"),
            @ApiResponse(responseCode = "409", description = "Comment is not added"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @PostMapping
    public ResponseEntity<HttpStatus> addComment(@RequestBody CommentsDTO commentsDTO) {
        commentService.addComment(commentsDTO);
        log.info("Comment with taskId: " + commentsDTO.getTaskId() + " is added!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Deleting comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment is deleted"),
            @ApiResponse(responseCode = "409", description = "Comment is not deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Integer id) {
        commentService.deleteCommentById(id);
        log.info("Comment with id: " + id + " is deleted!");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


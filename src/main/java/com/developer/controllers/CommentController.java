package com.developer.controllers;


import com.developer.entities.Comment;
import com.developer.requests.CommentCreateRequest;
import com.developer.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getAllCommentsWithParam(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return commentService.getAllCommentsWithParam(userId, postId);
    }

    @GetMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest newCommentRequest){
        return commentService.createOneComment(newCommentRequest);
    }
}

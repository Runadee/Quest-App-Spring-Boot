package com.developer.services;

import com.developer.entities.Comment;
import com.developer.repositories.CommentRepository;
import com.developer.requests.CommentCreateRequest;

import java.util.List;
import java.util.Optional;

public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }


    public List<Comment> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()) {
            return commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
        } else if (userId.isPresent()) {
            return commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        } else
            return commentRepository.findAll();
    }

    public Comment createOneComment(CommentCreateRequest newCommentRequest) {
    }
}

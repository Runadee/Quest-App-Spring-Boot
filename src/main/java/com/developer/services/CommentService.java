package com.developer.services;

import com.developer.entities.Comment;
import com.developer.entities.Post;
import com.developer.entities.User;
import com.developer.repositories.CommentRepository;
import com.developer.requests.CommentCreateRequest;
import com.developer.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
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



    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest createRequest) {
        User user = userService.getOneUserById(createRequest.getUserId());
        Post post = postService.getOnePostById(createRequest.getPostId());
        if (user != null && post != null) {
            Comment commentToSave = new Comment();
            commentToSave.setId(createRequest.getId());
            commentToSave.setUser(user);
            commentToSave.setPost(post);
            commentToSave.setText(createRequest.getText());
            return commentRepository.save(commentToSave);

        } else
            return null;

    }



    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest updateRequest) {
         Optional<Comment> comment = commentRepository.findById(commentId);
         if (comment.isPresent()){
             Comment commentToUpdate = comment.get();
             commentToUpdate.setText(updateRequest.getText());
             return commentRepository.save(commentToUpdate);
         }
        return null;
    }

    public void deleteOneCommentById(Long commentId) {
         commentRepository.deleteById(commentId);

    }
}

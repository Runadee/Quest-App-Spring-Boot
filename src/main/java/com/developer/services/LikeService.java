package com.developer.services;

import com.developer.entities.Like;
import com.developer.entities.Post;
import com.developer.entities.User;
import com.developer.repositories.LikeRepository;
import com.developer.requests.LikeCreateRequest;
import com.developer.responses.LikeResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {

        List<Like> likeList;
        if (userId.isPresent() && postId.isPresent()){
            likeList = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            likeList = likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            likeList = likeRepository.findByPostId(postId.get());
        } else
            likeList = likeRepository.findAll();
          return likeList.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like createOneLike(LikeCreateRequest likeRequest) {
        User user = userService.getOneUserById(likeRequest.getUserId());
        Post post = postService.getOnePostById(likeRequest.getPostId());
        if (user != null && post != null) {
            Like likeToSave = new Like();
            likeToSave.setId(likeRequest.getId());
            likeToSave.setUser(user);
            likeToSave.setPost(post);
            return likeRepository.save(likeToSave);
        } else
            return null;
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}

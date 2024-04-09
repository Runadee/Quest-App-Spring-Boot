package com.developer.services;


import com.developer.entities.Post;
import com.developer.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public List<Post> getAllPosts(Optional<Long> userId) {
        if (userId.isPresent()){
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
    }

    public Post getOnePostById(Long postId){
        return postRepository.findById(postId).orElse(null );
    }

    public Post createOnePost(Post newPost) {
        return postRepository.save(newPost);
    }
}

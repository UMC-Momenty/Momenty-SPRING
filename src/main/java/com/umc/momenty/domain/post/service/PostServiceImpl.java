package com.umc.momenty.domain.post.service;

import com.umc.momenty.domain.post.converter.PostConverter;
import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.entity.Post;
import com.umc.momenty.domain.post.exception.PostException;
import com.umc.momenty.domain.post.repository.PostRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public Long createPost(Long userId, PostReqDTO.CreatePostDTO createPost) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PostException(UserErrorCode.USER_NOT_FOUND));
        Post post = PostConverter.toPost(user, createPost);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }
}

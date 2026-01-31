package com.umc.momenty.domain.post.service;

import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    Long createPost(Long userId, PostReqDTO.CreatePostDTO createPost);
}

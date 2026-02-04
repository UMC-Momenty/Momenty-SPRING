package com.umc.momenty.domain.post.service;

import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.enums.PostCategory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public interface PostService {

    Long createPost(Long userId, PostReqDTO.CreatePostDTO createPost);

    PostResDto.PostPageDTO getAllPosts (Long userId, PostCategory category, String keyword, Pageable pageable);
}
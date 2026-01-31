package com.umc.momenty.domain.post.controller;

import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.exception.code.PostSuccessCode;
import com.umc.momenty.domain.post.service.PostService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/{userId}")
    public ApiResponse<Long> createPost(@PathVariable Long userId, @RequestBody PostReqDTO.CreatePostDTO createPost) {
        Long postId = postService.createPost(userId, createPost);
        return ApiResponse.onSuccess(PostSuccessCode.POST_CREATED, postId);
    }

//    @GetMapping
//    public ApiResponse<>


}

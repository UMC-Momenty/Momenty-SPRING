package com.umc.momenty.domain.post.controller;

import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.enums.PostCategory;
import com.umc.momenty.domain.post.exception.code.PostSuccessCode;
import com.umc.momenty.domain.post.service.PostService;
import com.umc.momenty.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs{

    private final PostService postService;

    @PostMapping("/{userId}")
    public ApiResponse<Long> createPost(@PathVariable Long userId, @RequestBody PostReqDTO.CreatePostDTO createPost) {
        Long postId = postService.createPost(userId, createPost);
        return ApiResponse.onSuccess(PostSuccessCode.POST_CREATED, postId);
    }
    @GetMapping("/{userId}")
    public ApiResponse<PostResDto.PostPageDTO> getPosts(
            @PathVariable Long userId,
            @RequestParam(required = false) PostCategory category,
            @RequestParam(required = false) String keyword,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return ApiResponse.onSuccess(PostSuccessCode.POST_LIST_FOUND, postService.getAllPosts(userId, category, keyword, pageable));
    }


}

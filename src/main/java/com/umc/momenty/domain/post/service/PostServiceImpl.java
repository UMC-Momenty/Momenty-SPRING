package com.umc.momenty.domain.post.service;

import com.umc.momenty.domain.post.converter.PostConverter;
import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.entity.Post;
import com.umc.momenty.domain.post.enums.PostCategory;
import com.umc.momenty.domain.post.exception.PostException;
import com.umc.momenty.domain.post.repository.LikeRepository;
import com.umc.momenty.domain.post.repository.PostRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Override
    public Long createPost(Long userId, PostReqDTO.CreatePostDTO createPost) {
        Post post = PostConverter.toPost(getUserById(userId), createPost);
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Override
    public PostResDto.PostPageDTO getAllPosts(Long userId, PostCategory category, String keyword, Pageable pageable) {
        getUserById(userId);
        Page <Post> posts = postRepository.findAllByCategoryAndKeyword(category, keyword, pageable);
        List<Long> postIds = posts.getContent().stream()
                .map(Post::getId)
                .toList();
        List<Long> likedPostIds = likeRepository.findLikedPostIdsByUserIdAndPostIds(userId, postIds);
        return PostConverter.toPostPageDTO(posts, likedPostIds);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new PostException(UserErrorCode.USER_NOT_FOUND));
    }
}

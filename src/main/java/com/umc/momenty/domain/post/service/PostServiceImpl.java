package com.umc.momenty.domain.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.umc.momenty.domain.post.converter.CommentConverter;
import com.umc.momenty.domain.post.converter.PostConverter;
import com.umc.momenty.domain.post.dto.req.CommentReqDTO;
import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.entity.Comment;
import com.umc.momenty.domain.post.entity.Post;
import com.umc.momenty.domain.post.entity.PostImage;
import com.umc.momenty.domain.post.enums.PostCategory;
import com.umc.momenty.domain.post.exception.PostException;
import com.umc.momenty.domain.post.exception.code.PostErrorCode;
import com.umc.momenty.domain.post.repository.CommentRepository;
import com.umc.momenty.domain.post.repository.LikeRepository;
import com.umc.momenty.domain.post.repository.PostRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    @Transactional
    public Long createPost(Long userId, PostReqDTO.CreatePostDTO createPost, List<MultipartFile> images) {
        User user = getUserById(userId);
        Post post = PostConverter.toPost(user, createPost);

        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                String imageUrl = uploadImageToS3(file);
                PostImage postImage = PostImage.builder()
                        .imageUrl(imageUrl)
                        .post(post)
                        .build();
                post.addPostImage(postImage);
            }
        }
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public PostResDto.PostPageDTO getAllPosts(Long userId, PostCategory category, String keyword, Pageable pageable) {
        getUserById(userId);
        // 🔥 수정: soft delete된 글 제외 (Repository에서 처리됨)
        Page<Post> posts = postRepository.findAllByCategoryAndKeyword(category, keyword, pageable);
        List<Long> postIds = posts.getContent().stream().map(Post::getId).toList();
        List<Long> likedPostIds = likeRepository.findLikedPostIdsByUserIdAndPostIds(userId, postIds);
        return PostConverter.toPostPageDTO(posts, likedPostIds);
    }

    @Override
    @Transactional
    public Long createComment(Long userId, Long postId, CommentReqDTO.CreateCommentDTO createComment) {
        User user = getUserById(userId);
        // 🔥 수정: soft delete된 글만 체크
        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        Comment comment = CommentConverter.toComment(user, post, createComment);
        Comment savedComment = commentRepository.save(comment);

        // 🔥 핵심 수정: 댓글 생성 시 Post의 commentNum 증가!
        post.increaseCommentNum();
        postRepository.save(post);  // 변경사항 플러시

        return savedComment.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public PostResDto.PostDetailDTO getPostDetail(Long userId, Long postId) {
        getUserById(userId);
        // 수정: soft delete된 글 제외
        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        // 수정: soft delete된 댓글 제외
        List<Comment> comments = commentRepository.findByPostIdAndDeletedAtIsNull(postId);

        boolean isLiked = likeRepository.findLikedPostIdsByUserIdAndPostIds(userId, List.of(postId)).contains(postId);
        return PostConverter.toPostDetailDTO(post, comments, isLiked);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new PostException(UserErrorCode.USER_NOT_FOUND));
    }

    // 수정: 기존 메서드도 soft delete 조건으로 변경
    private Post getPostById(Long postId) {
        return postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }

    private String uploadImageToS3(MultipartFile file) {
        String fileName = "posts/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드에 실패했습니다.", e);
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}

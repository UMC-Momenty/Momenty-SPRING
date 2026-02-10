package com.umc.momenty.domain.post.converter;

import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.entity.Comment;
import com.umc.momenty.domain.post.entity.Post;
import com.umc.momenty.domain.post.entity.PostImage;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.global.converter.PageConverter;
import com.umc.momenty.global.dto.res.PageResDTO;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post toPost(User user, PostReqDTO.CreatePostDTO createPostDTO) {
        return Post.builder()
                .category(createPostDTO.category())
                .title(createPostDTO.title())
                .content(createPostDTO.content())
                .user(user)
                .isAnonymous(createPostDTO.isAnonymous())
                .build();
    }

    public static PostResDto.PostPageDTO toPostPageDTO(Page<Post> posts, List<Long> likedPostIds) {
        List<PostResDto.PostListDTO> postListDTOS = posts.getContent().stream()
                .map(post -> {
                    boolean isLiked = likedPostIds.contains(post.getId());
                    String authorName = post.isAnonymous() ? "익명" : post.getUser().getUsername();

                    String contentPreview = post.getContent().length() > 50
                            ? post.getContent().substring(0, 50) + "..."
                            : post.getContent();

                    return PostResDto.PostListDTO.builder()
                            .postId(post.getId())
                            .category(post.getCategory())
                            .title(post.getTitle())
                            .contentPreview(contentPreview)
                            .authorName(authorName)
                            .createdAt(post.getCreatedAt())
                            .likeNum(post.getLikeNum())
                            .commentNum(post.getCommentNum())
                            .isLiked(isLiked)
                            .build();
                })
                .toList();
        PageResDTO.PageInfoDTO pageInfo = PageConverter.toPageInfoDTO(posts);

        return PostResDto.PostPageDTO.builder()
                .posts(postListDTOS)
                .pageInfo(pageInfo)
                .build();
    }

    public static PostResDto.PostDetailDTO toPostDetailDTO(Post post, List<Comment> comments, boolean isLiked) {
        String postAuthorName = post.isAnonymous() ? "익명" : post.getUser().getUsername();

        List<String> imageUrls = post.getPostImages().stream()
                .map(PostImage::getImageUrl)
                .collect(Collectors.toList());

        Map<Long, String> anonymousMap = new HashMap<>();
        AtomicInteger anonymousCounter = new AtomicInteger(1);

        List<PostResDto.CommentDTO> commentDTOs = comments.stream()
                .map(comment -> {
                    String commentAuthorName;

                    if (comment.isAnonymous()) {
                        Long userId = comment.getUser().getId();

                        if (userId.equals(post.getUser().getId())) {
                            commentAuthorName = "익명(글쓴이)";
                        } else {
                            commentAuthorName = anonymousMap.computeIfAbsent(
                                    userId,
                                    k -> "익명 " + anonymousCounter.getAndIncrement()
                            );
                        }
                    } else {
                        commentAuthorName = comment.getUser().getUsername();
                    }

                    return PostResDto.CommentDTO.builder()
                            .commentId(comment.getId())
                            .parentCommentId(comment.getParentId())
                            .authorName(commentAuthorName)
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());

        return PostResDto.PostDetailDTO.builder()
                .postId(post.getId())
                .category(post.getCategory())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(postAuthorName)
                .isAnonymous(post.isAnonymous())
                .createdAt(post.getCreatedAt())
                .imageUrls(imageUrls)
                .likeNum(post.getLikeNum())
                .commentNum(post.getCommentNum())
                .isLiked(isLiked)
                .comments(commentDTOs)
                .build();
    }
}

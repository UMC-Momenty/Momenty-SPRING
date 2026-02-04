package com.umc.momenty.domain.post.converter;

import com.umc.momenty.domain.post.dto.req.PostReqDTO;
import com.umc.momenty.domain.post.dto.res.PostResDto;
import com.umc.momenty.domain.post.entity.Post;
import com.umc.momenty.domain.post.entity.PostImage;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.global.converter.PageConverter;
import com.umc.momenty.global.dto.res.PageResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post toPost(User user, PostReqDTO.CreatePostDTO createPostDTO) {
        Post post = Post.builder()
                .category(createPostDTO.category())
                .title(createPostDTO.title())
                .content(createPostDTO.content())
                .user(user)
                .build();

        if (createPostDTO.imageUrls() != null) {
            createPostDTO.imageUrls().forEach(imageUrl -> {
                PostImage postImage = PostImage.builder()
                        .imageUrl(imageUrl)
                        .build();

                post.addPostImage(postImage);
            });
        }
        return post;
    }

    public static PostResDto.PostPageDTO toPostPageDTO(Page<Post> posts, List<Long> likedPostIds) {
        List<PostResDto.PostListDTO> postListDTOS = posts.getContent().stream()
                .map(post -> {
                    boolean isLiked = likedPostIds.contains(post.getId());

                    String authorName = post.getUser().getUsername();

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



}

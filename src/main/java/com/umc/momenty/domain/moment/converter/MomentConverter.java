package com.umc.momenty.domain.moment.converter;

import com.umc.momenty.domain.moment.dto.req.MomentReqDTO;
import com.umc.momenty.domain.moment.dto.res.MomentResDTO;
import com.umc.momenty.domain.moment.entity.Moment;
import com.umc.momenty.domain.moment.entity.MomentImage;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.global.converter.PageConverter;
import org.springframework.data.domain.Page;

import java.util.List;

public class MomentConverter {

    public static MomentResDTO.MomentDTO toMomentDTO(Moment moment) {
        return MomentResDTO.MomentDTO.builder()
                .momentId(moment.getId())
                .emotion(moment.getEmotion())
                .content(moment.getContent())
                .images(
                        moment.getImages().stream()
                                .map(MomentConverter::toMomentImageDTO)
                                .toList()
                )
                .createdAt(moment.getCreatedAt())
                .build();
    }

    public static MomentResDTO.MomentImageDTO toMomentImageDTO(MomentImage image) {
        return MomentResDTO.MomentImageDTO.builder()
                .momentImageId(image.getId())
                .imageUrl(image.getImageUrl())
                .build();
    }

    public static Moment toMoment(User user, Pet pet, MomentReqDTO.MomentDTO dto, List<String> imageUrls){
        Moment moment = Moment.builder()
                .emotion(dto.emotion())
                .content(dto.content())
                .user(user)
                .pet(pet)
                .build();

        if (imageUrls != null && !imageUrls.isEmpty()) {
            imageUrls.forEach(url ->
                    moment.addImage(new MomentImage(url))
            );
        }
        return moment;
    }

    public static MomentResDTO.MomentListDTO toMomentListDTO(Moment moment) {

        return MomentResDTO.MomentListDTO.builder()
                .momentId(moment.getId())
                .emotion(moment.getEmotion())
                .content(moment.getContent())
                .createdAt(moment.getCreatedAt())
                .build();
    }

    public static MomentResDTO.MomentPageDTO toMomentPageDTO(Page<Moment> page) {
        return MomentResDTO.MomentPageDTO.builder()
                .moments(
                        page.getContent().stream()
                                .map(MomentConverter::toMomentListDTO)
                                .toList()
                )
                .pageInfo(PageConverter.toPageInfoDTO(page))
                .build();
    }

	public static MomentResDTO.MomentCountDTO toMomentCountDTO(long count) {
        return MomentResDTO.MomentCountDTO.builder()
            .count(count)
            .build();
	}
}

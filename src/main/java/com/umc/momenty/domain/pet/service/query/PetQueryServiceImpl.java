package com.umc.momenty.domain.pet.service.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umc.momenty.domain.pet.converter.PetConverter;
import com.umc.momenty.domain.pet.dto.res.PetResDTO;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.repository.PetRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PetQueryServiceImpl implements PetQueryService {

	private final PetRepository petRepository;
	private final UserRepository userRepository;

	@Override
	public List<PetResDTO.MyPetDTO> getMyPetList(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		List<Pet> petList = petRepository.findAllByUser(user);

		return petList.stream()
			.map(PetConverter::toMyPetDTO)
			.toList();
	}
}

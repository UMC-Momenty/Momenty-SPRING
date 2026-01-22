package com.umc.momenty.domain.daily.service.command;

import org.springframework.stereotype.Service;

import com.umc.momenty.domain.daily.converter.DailyConverter;
import com.umc.momenty.domain.daily.dto.req.DailyReqDTO;
import com.umc.momenty.domain.daily.entity.DailyAnswer;
import com.umc.momenty.domain.daily.entity.DailyQuestion;
import com.umc.momenty.domain.daily.exception.DailyException;
import com.umc.momenty.domain.daily.exception.code.DailyErrorCode;
import com.umc.momenty.domain.daily.repository.DailyAnswerRepository;
import com.umc.momenty.domain.daily.repository.DailyQuestionRepository;
import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.exception.PetException;
import com.umc.momenty.domain.pet.exception.code.PetErrorCode;
import com.umc.momenty.domain.pet.repository.PetRepository;
import com.umc.momenty.domain.pet.validator.PetValidator;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import com.umc.momenty.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyCommandServiceImpl implements DailyCommandService {

	private final DailyAnswerRepository dailyAnswerRepository;
	private final DailyQuestionRepository dailyQuestionRepository;
	private final UserRepository userRepository;
	private final PetRepository petRepository;
	private final PetValidator petValidator;

	@Override
	public void createDailyAnswer(Long userId, DailyReqDTO.AnswerDTO answerDTO) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

		Pet pet = petRepository.findById(answerDTO.petId())
			.orElseThrow(() -> new PetException(PetErrorCode.PET_NOT_FOUND));

		petValidator.validatePetOwner(pet, user);

		DailyQuestion dailyQuestion = dailyQuestionRepository.findById(answerDTO.questId())
			.orElseThrow(() -> new DailyException(DailyErrorCode.QUESTION_NOT_FOUND));

		DailyAnswer dailyAnswer = DailyConverter.toDailyAnswer(user, pet, dailyQuestion, answerDTO.answer());
		dailyAnswerRepository.save(dailyAnswer);
	}
}

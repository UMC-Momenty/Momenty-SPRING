package com.umc.momenty.domain.schedule.service;

import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.repository.PetRepository;
import com.umc.momenty.domain.pet.exception.PetException;
import com.umc.momenty.domain.pet.exception.code.PetErrorCode;
import com.umc.momenty.domain.schedule.converter.ScheduleConverter;
import com.umc.momenty.domain.schedule.dto.ScheduleResponseDTO;
import com.umc.momenty.domain.schedule.entity.Schedule;
import com.umc.momenty.domain.schedule.repository.ScheduleRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.repository.UserRepository;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDTO.MyPetsResponseDTO getMyPets(Long userId) {
        // [수정] RuntimeException -> UserException 사용
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        List<Pet> pets = petRepository.findAllByUser(user);
        return ScheduleConverter.toMyPetsResponseDTO(pets);
    }

    public ScheduleResponseDTO.CalendarResponseDTO getCalendarAll(Long userId, int year, int month) {
        // [수정] RuntimeException -> UserException 사용
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Schedule> schedules = scheduleRepository.findAllByUserAndMonth(user, startOfMonth, endOfMonth);
        return ScheduleConverter.toCalendarResponseDTO(null, year, month, schedules);
    }

    public ScheduleResponseDTO.CalendarResponseDTO getCalendarByPet(Long userId, Long petId, int year, int month) {
        // [수정] RuntimeException -> UserException 사용
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // [추가] 펫 존재 여부 확인 후 PetException 발생
        if (!petRepository.existsById(petId)) {
            throw new PetException(PetErrorCode.PET_NOT_FOUND);
        }

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Schedule> schedules = scheduleRepository.findAllByPetAndMonth(petId, user, startOfMonth, endOfMonth);
        return ScheduleConverter.toCalendarResponseDTO(petId, year, month, schedules);
    }
}

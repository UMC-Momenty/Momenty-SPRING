package com.umc.momenty.domain.schedule.service;

import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.repository.PetRepository;
import com.umc.momenty.domain.schedule.converter.ScheduleConverter;
import com.umc.momenty.domain.schedule.dto.ScheduleResponseDTO;
import com.umc.momenty.domain.schedule.entity.Schedule;
import com.umc.momenty.domain.schedule.repository.ScheduleRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.repository.UserRepository;
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
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // PetRepository에 findAllByUser 메소드가 없으면 여기서 빨간 줄이 뜰 겁니다!
        List<Pet> pets = petRepository.findAllByUser(user);
        return ScheduleConverter.toMyPetsResponseDTO(pets);
    }

    public ScheduleResponseDTO.CalendarResponseDTO getCalendarAll(Long userId, int year, int month) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Schedule> schedules = scheduleRepository.findAllByUserAndMonth(user, startOfMonth, endOfMonth);
        return ScheduleConverter.toCalendarResponseDTO(null, year, month, schedules);
    }

    public ScheduleResponseDTO.CalendarResponseDTO getCalendarByPet(Long userId, Long petId, int year, int month) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Schedule> schedules = scheduleRepository.findAllByPetAndMonth(petId, user, startOfMonth, endOfMonth);
        return ScheduleConverter.toCalendarResponseDTO(petId, year, month, schedules);
    }
}

package com.umc.momenty.domain.schedule.service;

import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.repository.PetRepository;
import com.umc.momenty.domain.pet.exception.PetException;
import com.umc.momenty.domain.pet.exception.code.PetErrorCode;
import com.umc.momenty.domain.schedule.converter.ScheduleConverter;
import com.umc.momenty.domain.schedule.dto.req.ScheduleReqDTO;
import com.umc.momenty.domain.schedule.dto.res.ScheduleResDTO;
import com.umc.momenty.domain.schedule.entity.Schedule;
import com.umc.momenty.domain.schedule.entity.alarm.Alarm;
import com.umc.momenty.domain.schedule.enums.AlarmType;
import com.umc.momenty.domain.schedule.repository.AlarmRepository;
import com.umc.momenty.domain.schedule.repository.ScheduleRepository;
import com.umc.momenty.domain.user.entity.User;
import com.umc.momenty.domain.user.repository.UserRepository;
import com.umc.momenty.domain.user.exception.UserException;
import com.umc.momenty.domain.user.exception.code.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;

    public ScheduleResDTO.MyPetsResponseDTO getMyPets(Long userId) {
        // [수정] RuntimeException -> UserException 사용
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        List<Pet> pets = petRepository.findAllByUser(user);
        return ScheduleConverter.toMyPetsResponseDTO(pets);
    }

    public ScheduleResDTO.CalendarResponseDTO getCalendarAll(Long userId, int year, int month) {
        // [수정] RuntimeException -> UserException 사용
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Schedule> schedules = scheduleRepository.findAllByUserAndMonth(user, startOfMonth, endOfMonth);
        return ScheduleConverter.toCalendarResponseDTO(null, year, month, schedules);
    }

    public ScheduleResDTO.CalendarResponseDTO getCalendarByPet(Long userId, Long petId, int year, int month) {
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
    // 4. 일별 일정 조회
    public ScheduleResDTO.DailyScheduleResponseDTO getDailyScheduleByPet(Long petId, LocalDate date) {
        if (!petRepository.existsById(petId)) {
            throw new PetException(PetErrorCode.PET_NOT_FOUND);
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Schedule> schedules = scheduleRepository.findAllByPetAndDate(petId, startOfDay, endOfDay);

        // Schedule -> DTO 변환 (Alarm 메모 조회 포함)
        List<ScheduleResDTO.DailyScheduleDTO> dtos = schedules.stream()
                .map(schedule -> {
                    // 해당 스케줄의 알람 조회 (간소화를 위해 첫 번째 알람의 메모 사용)
                    Alarm alarm = alarmRepository.findBySchedule(schedule).orElse(null);
                    String memo = (alarm != null) ? alarm.getMemo() : null;
                    return ScheduleConverter.toDailyScheduleDTO(schedule, memo);
                })
                .collect(Collectors.toList());

        return ScheduleConverter.toDailyScheduleResponseDTO(petId, date, dtos);
    }

    // 5. 일정 생성
    @Transactional
    public ScheduleResDTO.ScheduleIdResponseDTO registerSchedule(Long petId, ScheduleReqDTO.ScheduleCreateDTO request) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetException(PetErrorCode.PET_NOT_FOUND));

        // 날짜/시간 병합 로직
        LocalDate datePart;
        if ("REPEAT".equals(request.getType()) || request.getDate() == null) {
            datePart = LocalDate.now(); // 반복일 경우 시작일은 오늘로 임시 설정 (요구사항에 따라 변경 가능)
        } else {
            datePart = LocalDate.parse(request.getDate());
        }
        LocalTime timePart = LocalTime.parse(request.getTime()); // "14:00" -> LocalTime
        LocalDateTime startDateTime = LocalDateTime.of(datePart, timePart);

        // 1. Schedule 저장
        Schedule schedule = Schedule.builder()
                .content(request.getTitle())
                .startDateTime(startDateTime)
                .category(request.getCategory())
                .user(pet.getUser())
                .pet(pet)
                .build();
        scheduleRepository.save(schedule);

        // 2. Alarm 저장 (메모 저장을 위해 필수)
        Alarm alarm = Alarm.builder()
                .schedule(schedule)
                .title(request.getTitle())
                .memo(request.getMemo())
                .alarmType("REPEAT".equals(request.getType()) ? AlarmType.REPEAT : AlarmType.ONE_TIME)
                .isEnabled(true)
                .build();
        alarmRepository.save(alarm);

        return ScheduleResDTO.ScheduleIdResponseDTO.builder()
                .scheduleId(schedule.getId())
                .build();
    }
}

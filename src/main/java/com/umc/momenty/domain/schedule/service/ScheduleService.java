package com.umc.momenty.domain.schedule.service;

import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.pet.repository.PetRepository;
import com.umc.momenty.domain.pet.exception.PetException;
import com.umc.momenty.domain.pet.exception.code.PetErrorCode;
import com.umc.momenty.domain.schedule.converter.ScheduleConverter;
import com.umc.momenty.domain.schedule.dto.req.ScheduleReqDTO;
import com.umc.momenty.domain.schedule.dto.res.ScheduleResDTO;
import com.umc.momenty.domain.schedule.entity.Schedule;
import com.umc.momenty.domain.schedule.repository.ScheduleRepository;
import com.umc.momenty.domain.schedule.exception.ScheduleException;
import com.umc.momenty.domain.schedule.exception.code.ScheduleErrorCode;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public ScheduleResDTO.MyPetsResponseDTO getMyPets(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        List<Pet> pets = petRepository.findAllByUser(user);
        return ScheduleConverter.toMyPetsResponseDTO(pets);
    }

    public ScheduleResDTO.CalendarResponseDTO getCalendarAll(Long userId, int year, int month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Schedule> schedules = scheduleRepository.findAllByUserAndMonth(user, startOfMonth, endOfMonth);
        return ScheduleConverter.toCalendarResponseDTO(null, year, month, schedules);
    }

    public ScheduleResDTO.CalendarResponseDTO getCalendarByPet(Long userId, Long petId, int year, int month) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetException(PetErrorCode.PET_NOT_FOUND));

        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = YearMonth.of(year, month).atEndOfMonth().atTime(23, 59, 59);

        List<Schedule> schedules = scheduleRepository.findAllByPetAndMonth(petId, user, startOfMonth, endOfMonth);
        return ScheduleConverter.toCalendarResponseDTO(petId, year, month, schedules);
    }

    public ScheduleResDTO.DailyScheduleResponseDTO getDailyScheduleByPet(Long petId, LocalDate date) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetException(PetErrorCode.PET_NOT_FOUND));

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Schedule> schedules = scheduleRepository.findAllByPetAndDate(petId, startOfDay, endOfDay);

        if (schedules.isEmpty()) {
            return ScheduleConverter.toDailyScheduleResponseDTO(petId, date, Collections.emptyList());
        }

        List<ScheduleResDTO.DailyScheduleDTO> dtos = schedules.stream()
                .map(schedule -> ScheduleConverter.toDailyScheduleDTO(schedule, schedule.getMemo()))
                .collect(Collectors.toList());

        return ScheduleConverter.toDailyScheduleResponseDTO(petId, date, dtos);
    }

    @Transactional
    public ScheduleResDTO.ScheduleIdResponseDTO registerSchedule(Long petId, ScheduleReqDTO.ScheduleCreateDTO request) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetException(PetErrorCode.PET_NOT_FOUND));

        LocalDate datePart = LocalDate.now();
        if ("ONE_TIME".equals(request.getType()) && request.getDate() != null) {
            datePart = LocalDate.parse(request.getDate());
        }

        LocalTime timePart = LocalTime.parse(request.getTime());
        LocalDateTime startDateTime = LocalDateTime.of(datePart, timePart);

        // [핵심] List -> String 변환 (예: ["MON", "WED"] -> "MON,WED")
        String repeatDaysStr = null;
        if ("REPEAT".equals(request.getType()) && request.getRepeatDays() != null) {
            repeatDaysStr = String.join(",", request.getRepeatDays());
        }

        Schedule schedule = Schedule.builder()
                .title(request.getTitle())
                .category(request.getCategory())
                .memo(request.getMemo())
                .startDateTime(startDateTime)
                .alarmTime(timePart)
                .repeatDays(repeatDaysStr)
                .isAlarmEnabled(request.getIsAlarmEnabled() != null ? request.getIsAlarmEnabled() : true)
                .durationMinutes(request.getDurationMinutes())
                .user(pet.getUser())
                .pet(pet)
                .build();

        scheduleRepository.save(schedule);

        return ScheduleResDTO.ScheduleIdResponseDTO.builder()
                .scheduleId(schedule.getId())
                .build();
    }

    // [NEW] 6. 알림 목록 조회
    public ScheduleResDTO.AlarmListDTO getAlarmList(Long petId) {
        if (!petRepository.existsById(petId)) {
            throw new PetException(PetErrorCode.PET_NOT_FOUND);
        }

        List<Schedule> schedules = scheduleRepository.findAllAlarmsByPet(petId);

        List<ScheduleResDTO.AlarmDTO> alarmDTOs = schedules.stream()
                .map(s -> {
                    List<String> daysList = null;
                    if (s.getRepeatDays() != null && !s.getRepeatDays().isEmpty()) {
                        daysList = List.of(s.getRepeatDays().split(","));
                    }

                    return ScheduleResDTO.AlarmDTO.builder()
                            .scheduleId(s.getId())
                            .title(s.getTitle())
                            .category(s.getCategory())
                            .repeatDays(daysList)
                            .date(s.getStartDateTime().toLocalDate())
                            .alarmTime(s.getAlarmTime())
                            .isOneTime(s.getRepeatDays() == null)
                            .isAlarmEnabled(s.isAlarmEnabled())
                            .durationMinutes(s.getDurationMinutes())
                            .build();
                })
                .collect(Collectors.toList());

        return ScheduleResDTO.AlarmListDTO.builder()
                .petId(petId)
                .alarms(alarmDTOs)
                .build();
    }

    // [NEW] 7. 알림 상태 토글
    @Transactional
    public ScheduleResDTO.AlarmStatusResponseDTO toggleAlarmStatus(Long scheduleId, boolean isEnabled) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleException(ScheduleErrorCode.SCHEDULE_NOT_FOUND));

        schedule.updateAlarmStatus(isEnabled);

        return ScheduleResDTO.AlarmStatusResponseDTO.builder()
                .scheduleId(schedule.getId())
                .isAlarmEnabled(schedule.isAlarmEnabled())
                .build();
    }
}

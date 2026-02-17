package com.umc.momenty.domain.schedule.converter;

import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.schedule.dto.res.ScheduleResDTO;
import com.umc.momenty.domain.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScheduleConverter {

    public static ScheduleResDTO.PetProfileDTO toPetProfileDTO(Pet pet) {
        return ScheduleResDTO.PetProfileDTO.builder()
                .petId(pet.getId())
                .profile(pet.getProfileImageUrl())
                .build();
    }

    public static ScheduleResDTO.MyPetsResponseDTO toMyPetsResponseDTO(List<Pet> pets) {
        List<ScheduleResDTO.PetProfileDTO> petDTOs = pets.stream()
                .map(ScheduleConverter::toPetProfileDTO)
                .collect(Collectors.toList());

        return ScheduleResDTO.MyPetsResponseDTO.builder()
                .pets(petDTOs)
                .build();
    }

    public static ScheduleResDTO.CalendarResponseDTO toCalendarResponseDTO(Long petId, int year, int month, List<Schedule> schedules) {
        Map<LocalDate, Long> countMap = schedules.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStartDateTime().toLocalDate(),
                        Collectors.counting()
                ));

        List<ScheduleResDTO.DayCountDTO> days = countMap.entrySet().stream()
                .map(entry -> ScheduleResDTO.DayCountDTO.builder()
                        .date(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .sorted((d1, d2) -> d1.getDate().compareTo(d2.getDate()))
                .collect(Collectors.toList());

        return ScheduleResDTO.CalendarResponseDTO.builder()
                .petId(petId)
                .year(year)
                .month(month)
                .days(days)
                .build();
    }

    public static ScheduleResDTO.DailyScheduleResponseDTO toDailyScheduleResponseDTO(Long petId, LocalDate date, List<ScheduleResDTO.DailyScheduleDTO> scheduleDTOs) {
        return ScheduleResDTO.DailyScheduleResponseDTO.builder()
                .petId(petId)
                .date(date)
                .schedules(scheduleDTOs)
                .build();
    }

    // 단일 스케줄 -> DTO 변환
    public static ScheduleResDTO.DailyScheduleDTO toDailyScheduleDTO(Schedule schedule, String memo) {
        return ScheduleResDTO.DailyScheduleDTO.builder()
                .scheduleId(schedule.getId())
                .title(schedule.getTitle()) // ✅ content -> title로 수정
                .startAt(schedule.getStartDateTime())
                .category(schedule.getCategory())
                .memo(memo)
                .build();
    }
}

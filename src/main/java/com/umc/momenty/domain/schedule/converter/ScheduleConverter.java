package com.umc.momenty.domain.schedule.converter;

import com.umc.momenty.domain.pet.entity.Pet;
import com.umc.momenty.domain.schedule.dto.ScheduleResponseDTO;
import com.umc.momenty.domain.schedule.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScheduleConverter {

    public static ScheduleResponseDTO.PetProfileDTO toPetProfileDTO(Pet pet) {
        return ScheduleResponseDTO.PetProfileDTO.builder()
                .petId(pet.getId())
                .profile(pet.getProfileImageUrl())
                .build();
    }

    public static ScheduleResponseDTO.MyPetsResponseDTO toMyPetsResponseDTO(List<Pet> pets) {
        List<ScheduleResponseDTO.PetProfileDTO> petDTOs = pets.stream()
                .map(ScheduleConverter::toPetProfileDTO)
                .collect(Collectors.toList());

        return ScheduleResponseDTO.MyPetsResponseDTO.builder()
                .pets(petDTOs)
                .build();
    }

    public static ScheduleResponseDTO.CalendarResponseDTO toCalendarResponseDTO(Long petId, int year, int month, List<Schedule> schedules) {
        Map<LocalDate, Long> countMap = schedules.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStartDateTime().toLocalDate(),
                        Collectors.counting()
                ));

        List<ScheduleResponseDTO.DayCountDTO> days = countMap.entrySet().stream()
                .map(entry -> ScheduleResponseDTO.DayCountDTO.builder()
                        .date(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .sorted((d1, d2) -> d1.getDate().compareTo(d2.getDate()))
                .collect(Collectors.toList());

        return ScheduleResponseDTO.CalendarResponseDTO.builder()
                .petId(petId)
                .year(year)
                .month(month)
                .days(days)
                .build();
    }
}

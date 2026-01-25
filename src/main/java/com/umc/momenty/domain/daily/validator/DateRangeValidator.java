package com.umc.momenty.domain.daily.validator;

import java.time.LocalDate;

import com.umc.momenty.domain.daily.annotation.ValidDateRange;
import com.umc.momenty.domain.daily.dto.req.DailyReqDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator
	implements ConstraintValidator<ValidDateRange, DailyReqDTO.DateRangeDTO> {

	@Override
	public boolean isValid(DailyReqDTO.DateRangeDTO value, ConstraintValidatorContext context) {
		if (value == null) return true;

		LocalDate from = value.from();
		LocalDate to = value.to();

		if (from == null || to == null) return true;

		if (from.isAfter(to)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					"from은 to보다 이전 날짜여야 합니다."
				)
				.addPropertyNode("from")
				.addConstraintViolation();
			return false;
		}

		return true;
	}
}


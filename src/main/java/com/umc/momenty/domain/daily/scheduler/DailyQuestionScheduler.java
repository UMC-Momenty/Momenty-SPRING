package com.umc.momenty.domain.daily.scheduler;

import org.hibernate.TransactionException;
import org.springframework.core.retry.RetryException;
import org.springframework.core.retry.RetryListener;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;
import org.springframework.core.retry.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.umc.momenty.global.apiPayload.exception.GeneralException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class DailyQuestionScheduler {

	private final DailyQuestionTask dailyQuestionTask;

	@Scheduled(cron = "0 0 0 * * *")
	public void createDailyQuestion() {
		log.info("Creating Daily Question...");

		RetryPolicy retryPolicy = RetryPolicy.builder()
			.maxRetries(3)
			.includes(TransactionException.class)
			.includes(GeneralException.class)
			.build();

		RetryTemplate retryTemplate = new RetryTemplate(retryPolicy);
		retryTemplate.setRetryListener(new TryCreatingDailyQuestionListener());

		try {
			retryTemplate.execute(new TryCreatingDailyQuestion());
		} catch (RetryException e) {
			log.warn("Failed to create Daily Question. Please add Daily Question manually.");
		}
	}

	public static class TryCreatingDailyQuestionListener implements RetryListener {
		@Override
		public void beforeRetry(RetryPolicy retryPolicy, Retryable<?> retryable) {
			log.warn("Retry Creating Daily Question...");
		}

		@Override
		public void onRetryFailure(RetryPolicy retryPolicy, Retryable<?> retryable, Throwable throwable) {
			log.warn("Failed to create Daily Question. [Message : {}]", throwable.getMessage());
		}
	}

	private class TryCreatingDailyQuestion implements Retryable<Void> {
		@Override
		public Void execute() {
			return dailyQuestionTask.createDailyQuestion();
		}
	}
}

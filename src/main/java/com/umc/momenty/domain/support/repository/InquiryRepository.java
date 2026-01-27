package com.umc.momenty.domain.support.repository;

import com.umc.momenty.domain.support.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}

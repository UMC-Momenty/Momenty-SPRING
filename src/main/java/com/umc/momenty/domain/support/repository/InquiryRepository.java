package com.umc.momenty.domain.support.repository;

import com.umc.momenty.domain.support.entity.Inquiry;
import com.umc.momenty.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    Page<Inquiry> findAllByUser(User user, Pageable pageable);
}

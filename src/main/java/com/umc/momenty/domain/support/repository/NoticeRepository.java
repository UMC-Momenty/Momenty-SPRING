package com.umc.momenty.domain.support.repository;

import com.umc.momenty.domain.support.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findByIdAndActiveTrue(Long noticeId);

    Page<Notice> findAllByActiveTrue(Pageable pageable);
}

package com.umc.momenty.domain.support.repository;

import com.umc.momenty.domain.support.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}

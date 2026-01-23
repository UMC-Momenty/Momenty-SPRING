package com.umc.momenty.domain.support.repository;

import com.umc.momenty.domain.support.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("SELECT n FROM Notice n WHERE n.isActive = true")
    Page<Notice> findActiveNotices(Pageable pageable);
}

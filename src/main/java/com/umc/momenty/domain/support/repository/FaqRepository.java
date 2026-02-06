package com.umc.momenty.domain.support.repository;

import com.umc.momenty.domain.support.entity.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<FAQ, Long> {

    List<FAQ> findAllByActiveTrue();
}

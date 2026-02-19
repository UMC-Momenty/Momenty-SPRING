package com.umc.momenty.domain.support.repository;

import com.umc.momenty.domain.support.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppRepository extends JpaRepository<App, Long> {

    Optional<App> findByIdAndActiveTrue(Long appId);
    List<App> findAllByActiveTrue();
}

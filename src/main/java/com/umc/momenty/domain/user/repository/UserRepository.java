package com.umc.momenty.domain.user.repository;

import com.umc.momenty.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

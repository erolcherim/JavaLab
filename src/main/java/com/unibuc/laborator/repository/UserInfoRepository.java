package com.unibuc.laborator.repository;

import com.unibuc.laborator.model.User;
import com.unibuc.laborator.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
}
